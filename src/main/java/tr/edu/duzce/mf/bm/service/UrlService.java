package tr.edu.duzce.mf.bm.service;

import tr.edu.duzce.mf.bm.entity.UrlLink;

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
