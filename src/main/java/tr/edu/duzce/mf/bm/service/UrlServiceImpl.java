package tr.edu.duzce.mf.bm.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tr.edu.duzce.mf.bm.dao.UrlLinkDao;
import tr.edu.duzce.mf.bm.entity.UrlLink;
import java.util.List;

@Service
@Transactional
public class UrlServiceImpl implements UrlService {

    @Autowired
    private UrlLinkDao urlLinkDao;

    @Autowired
    private Base62Util base62Util;

    @Autowired
    private AiService aiService;

    @Override
    public UrlLink createShortUrl(String originalUrl) {
        UrlLink urlLink = new UrlLink();
        urlLink.setOriginalUrl(originalUrl);
        urlLink.setShortCode("temp");
        
        String summary = aiService.analyzeAndGetSummary(originalUrl);
        urlLink.setSummary(summary);

        urlLinkDao.save(urlLink);

        String shortCode = base62Util.encode(urlLink.getId());
        urlLink.setShortCode(shortCode);

        urlLinkDao.update(urlLink);

        return urlLink;
    }

    @Override
    public String getOriginalUrl(String shortCode) {
        UrlLink urlLink = urlLinkDao.findByShortCode(shortCode);
        if (urlLink != null) {
            urlLink.setClickCount(urlLink.getClickCount() + 1);
            urlLinkDao.update(urlLink);
            return urlLink.getOriginalUrl();
        }
        return null;
    }

    @Override
    public List<UrlLink> getAllUrls() {
        return urlLinkDao.findAll();
    }
}
