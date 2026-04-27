package tr.edu.duzce.mf.bm.dao;

import tr.edu.duzce.mf.bm.entity.UrlLink;

public interface UrlDao {
    
    /**
     * Yeni bir URL kaydeder
     * @param urlLink kaydedilecek URL nesnesi
     */
    void save(UrlLink urlLink);
    
    /**
     * Kısa kod ile URL arar
     * @param shortCode aranacak kısa kod
     * @return bulunan URL nesnesi, bulunamazsa null
     */
    UrlLink findByShortCode(String shortCode);
    
    /**
     * Kısa kodun veritabanında olup olmadığını kontrol eder
     * @param shortCode kontrol edilecek kısa kod
     * @return varsa true, yoksa false
     */
    boolean existsByShortCode(String shortCode);
}
