#!/usr/bin/env python3
import json
import urllib.request


def main():
    payload = {
        "url": "https://example.com/blog/ai-destekli-url-kisaltma",
        "text": "Bu içerik AI destekli URL kısaltma ve analiz yaklaşımını anlatıyor."
    }

    req = urllib.request.Request(
        "http://127.0.0.1:8088/analyze",
        data=json.dumps(payload).encode("utf-8"),
        headers={"Content-Type": "application/json"},
        method="POST",
    )

    with urllib.request.urlopen(req, timeout=5) as response:
        print(response.read().decode("utf-8"))


if __name__ == "__main__":
    main()
