package tr.edu.duzce.mf.bm.service;

import tr.edu.duzce.mf.bm.entity.UrlLink;
import java.util.List;

public interface UrlService {
    
    /**
     * Uzun URL'yi alıp kısa kod oluşturarak kaydeder
     * @param originalUrl kısaltılacak uzun URL
     * @return oluşturulan UrlLink nesnesi
     */
    UrlLink createShortUrl(String originalUrl);
    
    /**
     * Kısa kod ile URL arar ve tıklama sayısını artırır (getOriginalUrl olarak adlandırıldı)
     * @param shortCode aranacak kısa kod
     * @return bulunan orijinal url string'i
     */
    String getOriginalUrl(String shortCode);
    
    /**
     * Tüm URL'leri getirir
     * @return UrlLink listesi
     */
    List<UrlLink> getAllUrls();
}
