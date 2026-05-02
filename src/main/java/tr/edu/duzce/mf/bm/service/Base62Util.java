package tr.edu.duzce.mf.bm.service;

import org.springframework.stereotype.Component;

@Component
public class Base62Util {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final int BASE = ALPHABET.length();

    // Veritabanı ID'sini alıp kısa koda çevirir (Örn: 12345 -> "dnh")
    public String encode(long num) {
        StringBuilder sb = new StringBuilder();
        if (num == 0) {
            return String.valueOf(ALPHABET.charAt(0));
        }
        while (num > 0) {
            sb.append(ALPHABET.charAt((int) (num % BASE)));
            num /= BASE;
        }
        return sb.reverse().toString();
    }

    // Kısa kodu alıp tekrar ID'ye çevirir (İleride lazım olabilir)
    public long decode(String str) {
        long num = 0;
        for (int i = 0; i < str.length(); i++) {
            num = num * BASE + ALPHABET.indexOf(str.charAt(i));
        }
        return num;
    }
}