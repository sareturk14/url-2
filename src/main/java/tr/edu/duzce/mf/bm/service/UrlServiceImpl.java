package tr.edu.duzce.mf.bm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
<<<<<<< HEAD
import tr.edu.duzce.mf.bm.dao.UrlDao;
import tr.edu.duzce.mf.bm.entity.UrlLink;

import java.security.SecureRandom;
import java.util.Random;

@Service
@Transactional
public class UrlServiceImpl implements UrlService {
    
    @Autowired
    private UrlDao urlDao;
    
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final int SHORT_CODE_LENGTH = 6;
    private final Random random = new SecureRandom();
    
    @Override
    @Transactional
    public UrlLink createShortUrl(String originalUrl) {
        String shortCode;
        do {
            shortCode = generateShortCode();
        } while (urlDao.existsByShortCode(shortCode));
        
        UrlLink urlLink = new UrlLink();
        urlLink.setOriginalUrl(originalUrl);
        urlLink.setShortCode(shortCode);
        
        urlDao.save(urlLink);
        return urlLink;
    }
    
    @Override
    @Transactional
    public UrlLink getUrlAndIncrementClick(String shortCode) {
        UrlLink urlLink = urlDao.findByShortCode(shortCode);
        if (urlLink != null) {
            urlLink.setClickCount(urlLink.getClickCount() + 1);
            // Hibernate otomatik olarak değişikliği kaydedecek
        }
        return urlLink;
    }
    
    @Override
    public boolean isShortCodeAvailable(String shortCode) {
        return !urlDao.existsByShortCode(shortCode);
    }
    
    /**
     * 6 haneli rastgele kısa kod üretir
     * @return üretilen kısa kod
     */
    private String generateShortCode() {
        StringBuilder sb = new StringBuilder(SHORT_CODE_LENGTH);
        for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
            sb.append(CHARACTERS.charAt(random.nextInt(CHARACTERS.length())));
        }
        return sb.toString();
    }
}
=======
import tr.edu.duzce.mf.bm.dao.UrlLinkDao;
import tr.edu.duzce.mf.bm.entity.UrlLink;

import java.util.List;

@Service
@Transactional // Veritabanı işlemleri için Transaction (işlem) yönetimini otomatik yapar
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlLinkDao urlLinkDao;

    @Autowired
    private Base62Util base62Util;

    @Override
    public UrlLink createShortUrl(String originalUrl) {
        // 1. Yeni link nesnesini oluştur ve veritabanına kaydet (Benzersiz bir ID almak için)
        UrlLink urlLink = new UrlLink();
        urlLink.setOriginalUrl(originalUrl);
        urlLink.setShortCode("temp"); // ID oluşana kadar geçici bir kod atıyoruz

        urlLinkDao.save(urlLink);

        // 2. Veritabanının verdiği ID'yi alıp Base62 algoritmamızla kısa koda çeviriyoruz
        String shortCode = base62Util.encode(urlLink.getId());
        urlLink.setShortCode(shortCode);

        // 3. Gerçek kısa kod ile veritabanını güncelliyoruz
        urlLinkDao.update(urlLink);

        return urlLink;
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        UrlLink urlLink = urlLinkDao.findByShortCode(shortCode);
        if (urlLink != null) {
            // Birisi linke tıkladığı için tıklanma sayısını 1 artır ve veritabanını güncelle
            urlLink.setClickCount(urlLink.getClickCount() + 1);
            urlLinkDao.update(urlLink);
            return urlLink.getOriginalUrl();
        }
        return null; // Eğer geçersiz bir kısa link girilmişse null döner
    }

    @Override
    public List<UrlLink> getAllUrls() {
        return urlLinkDao.findAll();
    }
}
>>>>>>> 2a3b034 (algoritmanın temelini attım)
