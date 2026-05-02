package tr.edu.duzce.mf.bm.service;

import tr.edu.duzce.mf.bm.entity.UrlLink;
<<<<<<< HEAD

public interface UrlService {
    
    /**
     * Uzun URL'yi alıp kısa kod oluşturarak kaydeder
     * @param originalUrl kısaltılacak uzun URL
     * @return oluşturulan UrlLink nesnesi
     */
    UrlLink createShortUrl(String originalUrl);
    
    /**
     * Kısa kod ile URL arar ve tıklama sayısını artırır
     * @param shortCode aranacak kısa kod
     * @return bulunan UrlLink nesnesi, bulunamazsa null
     */
    UrlLink getUrlAndIncrementClick(String shortCode);
    
    /**
     * Kısa kodun benzersiz olmasını sağlar
     * @param shortCode kontrol edilecek kısa kod
     * @return benzersizse true, değilse false
     */
    boolean isShortCodeAvailable(String shortCode);
}
=======
import java.util.List;

public interface UrlService {
    UrlLink createShortUrl(String originalUrl);  // Yeni link oluşturur ve kısaltır
    String getOriginalUrl(String shortCode);     // Kısa koda tıklandığında asıl linki bulur ve tıklanmayı artırır
    List<UrlLink> getAllUrls();                  // Panoda listelemek için tüm linkleri getirir
}
>>>>>>> 2a3b034 (algoritmanın temelini attım)
