package tr.edu.duzce.mf.bm.dao;

import tr.edu.duzce.mf.bm.entity.UrlLink;
import java.util.List;

public interface UrlLinkDao {
    void save(UrlLink urlLink);                  // Yeni link kaydet
    UrlLink findByShortCode(String shortCode);   // Kısa koda göre linki bul (Yönlendirme için)
    void update(UrlLink urlLink);                // Tıklanma sayısını veya AI özetini güncelle
    List<UrlLink> findAll();                     // Bütün linkleri getir (Pano için)
}