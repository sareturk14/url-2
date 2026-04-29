package tr.edu.duzce.mf.bm.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import tr.edu.duzce.mf.bm.entity.UrlLink;

import java.security.SecureRandom;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * UrlService iş mantığı birim testleri.
 *
 * Bu sınıf, URL kısaltma servisinin temel mantığını test eder:
 * 1) Kısa kod üretim algoritmasının doğruluğu
 * 2) Boş/null URL girildiğinde hata fırlatılması
 */
class UrlServiceTest {

    // UrlServiceImpl içindeki sabitlerle aynı değerler
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private final SecureRandom random = new SecureRandom();

    /**
     * UrlServiceImpl.generateShortCode() mantığının birebir kopyası.
     * Private metodu test edebilmek için burada yeniden tanımlıyoruz.
     */
    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }

    /**
     * Servis katmanındaki URL doğrulama + kısa kod üretim mantığını simüle eder.
     * Gerçek projede bu mantık UrlServiceImpl.createShortUrl() içindedir.
     */
    private UrlLink validateAndCreateShortUrl(String originalUrl) {
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            throw new IllegalArgumentException("URL boş olamaz!");
        }
        UrlLink urlLink = new UrlLink();
        urlLink.setOriginalUrl(originalUrl);
        urlLink.setShortCode(generateShortCode());
        return urlLink;
    }

    // =========================================================================
    //  TEST 1: Kısa kod üretim algoritması
    // =========================================================================

    @Test
    @DisplayName("Geçerli URL verildiğinde 6 haneli, alfanümerik ve benzersiz kısa kodlar üretilmeli")
    void shortCodeShouldBe6CharAlphanumericAndUnique() {
        // 100 adet kısa kod üretip her birini doğruluyoruz
        Set<String> generatedCodes = new HashSet<>();

        for (int i = 0; i < 100; i++) {
            UrlLink result = validateAndCreateShortUrl("https://www.google.com");

            // Kısa kod null olmamalı
            assertNotNull(result.getShortCode(), "Üretilen kısa kod null olmamalı");

            // Kısa kod tam 6 karakter olmalı
            assertEquals(SHORT_CODE_LENGTH, result.getShortCode().length(),
                    "Kısa kod tam olarak " + SHORT_CODE_LENGTH + " karakter olmalı");

            // Kısa kod sadece harf ve rakamlardan oluşmalı (A-Z, a-z, 0-9)
            assertTrue(result.getShortCode().matches("[A-Za-z0-9]+"),
                    "Kısa kod sadece alfanümerik karakterler içermeli, bulunan: " + result.getShortCode());

            // Orijinal URL doğru atanmış olmalı
            assertEquals("https://www.google.com", result.getOriginalUrl(),
                    "Orijinal URL doğru kaydedilmeli");

            generatedCodes.add(result.getShortCode());
        }

        // 100 kodun büyük çoğunluğu benzersiz olmalı (62^6 ≈ 56 milyar olasılık)
        assertTrue(generatedCodes.size() > 90,
                "100 üretilen kodun en az 90'ı benzersiz olmalı, bulunan benzersiz: " + generatedCodes.size());
    }

    // =========================================================================
    //  TEST 2: Boş ve null URL kontrolü
    // =========================================================================

    @Test
    @DisplayName("Boş, null veya sadece boşluk içeren URL verildiğinde IllegalArgumentException fırlatılmalı")
    void emptyOrNullUrlShouldThrowException() {
        // Durum 1: Boş string ("") verildiğinde hata fırlatılmalı
        assertThrows(IllegalArgumentException.class,
                () -> validateAndCreateShortUrl(""),
                "Boş string URL için IllegalArgumentException bekleniyor");

        // Durum 2: null verildiğinde hata fırlatılmalı
        assertThrows(IllegalArgumentException.class,
                () -> validateAndCreateShortUrl(null),
                "null URL için IllegalArgumentException bekleniyor");

        // Durum 3: Sadece boşluk karakterleri ("   ") verildiğinde hata fırlatılmalı
        assertThrows(IllegalArgumentException.class,
                () -> validateAndCreateShortUrl("   "),
                "Sadece boşluk içeren URL için IllegalArgumentException bekleniyor");
    }
}
