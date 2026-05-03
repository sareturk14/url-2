#!/usr/bin/env python3
import json
import os
import re
import uuid
from collections import Counter
from datetime import datetime, timezone
from http.server import BaseHTTPRequestHandler, ThreadingHTTPServer
from urllib.parse import urlparse


STOP_WORDS_TR = {
    "ve", "ile", "veya", "ama", "fakat", "bir", "bu", "şu", "için", "gibi",
    "çok", "daha", "en", "de", "da", "ki", "mi", "mı", "mu", "mü", "olan",
    "olarak", "içinde", "üzerinde", "altında", "hem", "ya", "ya da"
}

STOP_WORDS_EN = {
    "the", "and", "or", "but", "a", "an", "to", "of", "in", "on", "at", "for",
    "with", "by", "is", "are", "was", "were", "be", "as", "that", "this", "it"
}

POSITIVE_WORDS = {"good", "great", "excellent", "fast", "secure", "başarılı", "iyi", "hızlı"}
NEGATIVE_WORDS = {"bad", "slow", "error", "problem", "issue", "kötü", "hata", "sorun"}


def detect_language(text: str) -> str:
    lower = text.lower()
    if re.search(r"[çğıöşü]", lower):
        return "tr"

    tr_hits = sum(1 for w in STOP_WORDS_TR if f" {w} " in f" {lower} ")
    en_hits = sum(1 for w in STOP_WORDS_EN if f" {w} " in f" {lower} ")
    return "tr" if tr_hits > en_hits else "en"


def tokenize(text: str):
    return re.findall(r"[A-Za-zÇĞİÖŞÜçğıöşü0-9]+", text.lower())


def summarize_text(text: str, max_words: int = 22) -> str:
    sentences = re.split(r"(?<=[.!?])\s+", text.strip())
    if sentences and sentences[0]:
        first = sentences[0].strip()
        words = first.split()
        if len(words) <= max_words:
            return first
        return " ".join(words[:max_words]) + "..."
    words = text.split()
    return " ".join(words[:max_words]) + ("..." if len(words) > max_words else "")


def sentiment_of(tokens):
    pos = sum(1 for t in tokens if t in POSITIVE_WORDS)
    neg = sum(1 for t in tokens if t in NEGATIVE_WORDS)
    if pos > neg:
        return "positive"
    if neg > pos:
        return "negative"
    return "neutral"


def analyze_payload(payload):
    text = (payload.get("text") or "").strip()
    url = (payload.get("url") or "").strip()

    if not text and not url:
        raise ValueError("Payload must include at least one of: text or url")

    parsed_url = None
    if url:
        parsed_url = urlparse(url)
        url_bits = [parsed_url.netloc.replace("www.", ""), parsed_url.path.replace("/", " ").strip()]
        text = (text + " " + " ".join(bit for bit in url_bits if bit)).strip()

    tokens = tokenize(text)
    language = detect_language(text)
    stop_words = STOP_WORDS_TR if language == "tr" else STOP_WORDS_EN

    meaningful_tokens = [t for t in tokens if t not in stop_words and len(t) > 2]
    keyword_counts = Counter(meaningful_tokens)
    keywords = [word for word, _ in keyword_counts.most_common(6)]

    analysis = {
        "summary": summarize_text(text),
        "keywords": keywords,
        "language": language,
        "sentiment": sentiment_of(tokens),
        "wordCount": len(tokens),
        "charCount": len(text),
    }

    if parsed_url and parsed_url.netloc:
        analysis["urlContext"] = {
            "domain": parsed_url.netloc,
            "path": parsed_url.path or "/",
            "scheme": parsed_url.scheme or "http"
        }

    return analysis


class SemanticServiceHandler(BaseHTTPRequestHandler):
    server_version = "SemanticTestService/1.0"

    def _send_json(self, payload, status=200):
        data = json.dumps(payload, ensure_ascii=False).encode("utf-8")
        self.send_response(status)
        self.send_header("Content-Type", "application/json; charset=utf-8")
        self.send_header("Content-Length", str(len(data)))
        self.end_headers()
        self.wfile.write(data)

    def do_GET(self):
        if self.path == "/health":
            self._send_json({
                "ok": True,
                "service": "semantic-analysis-test-service",
                "timestamp": datetime.now(timezone.utc).isoformat()
            })
            return

        self._send_json({
            "ok": False,
            "error": "Not Found",
            "supportedEndpoints": ["GET /health", "POST /analyze"]
        }, status=404)

    def do_POST(self):
        if self.path != "/analyze":
            self._send_json({"ok": False, "error": "Not Found"}, status=404)
            return

        try:
            content_length = int(self.headers.get("Content-Length", "0"))
            raw_body = self.rfile.read(content_length).decode("utf-8")
            payload = json.loads(raw_body) if raw_body else {}
        except json.JSONDecodeError:
            self._send_json({"ok": False, "error": "Invalid JSON body"}, status=400)
            return

        request_id = str(uuid.uuid4())

        try:
            analysis = analyze_payload(payload)
        except ValueError as ex:
            self._send_json({
                "ok": False,
                "requestId": request_id,
                "error": str(ex)
            }, status=400)
            return

        self._send_json({
            "ok": True,
            "requestId": request_id,
            "timestamp": datetime.now(timezone.utc).isoformat(),
            "analysis": analysis
        })


def main():
    host = os.getenv("AI_TEST_HOST", "127.0.0.1")
    port = int(os.getenv("AI_TEST_PORT", "8088"))
    server = ThreadingHTTPServer((host, port), SemanticServiceHandler)
    print(f"Semantic test service running on http://{host}:{port}")
    server.serve_forever()


if __name__ == "__main__":
    main()
