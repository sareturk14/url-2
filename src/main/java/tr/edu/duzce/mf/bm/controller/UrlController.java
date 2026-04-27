package tr.edu.duzce.mf.bm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.edu.duzce.mf.bm.entity.UrlLink;
import tr.edu.duzce.mf.bm.service.UrlService;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class UrlController {
    
    @Autowired
    private UrlService urlService;
    
    /**
     * Anasayfayı gösterir
     */
    @GetMapping("/")
    public String home() {
        return "index";
    }
    
    /**
     * Kullanıcının girdiği URL'yi kısaltır
     */
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("originalUrl") String originalUrl, 
                           Model model,
                           HttpServletRequest request) {
        
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            model.addAttribute("error", "URL boş olamaz!");
            return "index";
        }
        
        // URL formatını düzeltme (http:// eklenmemişse ekle)
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }
        
        try {
            UrlLink urlLink = urlService.createShortUrl(originalUrl);
            
            // Kısaltılmış URL'nin tam adresini oluştur
            String baseUrl = getBaseUrl(request);
            String shortUrl = baseUrl + "/" + urlLink.getShortCode();
            
            model.addAttribute("shortUrl", shortUrl);
            model.addAttribute("originalUrl", originalUrl);
            model.addAttribute("shortCode", urlLink.getShortCode());
            
        } catch (Exception e) {
            model.addAttribute("error", "URL kısaltılırken hata oluştu: " + e.getMessage());
        }
        
        return "index";
    }
    
    /**
     * Kısa linke tıklandığında orijinal URL'ye yönlendirir
     */
    @GetMapping("/{shortCode}")
    public String redirectToOriginalUrl(@PathVariable("shortCode") String shortCode,
                                      RedirectAttributes redirectAttributes) {
        
        UrlLink urlLink = urlService.getUrlAndIncrementClick(shortCode);
        
        if (urlLink == null) {
            redirectAttributes.addFlashAttribute("error", "Bu kısa kod bulunamadı!");
            return "redirect:/";
        }
        
        return "redirect:" + urlLink.getOriginalUrl();
    }
    
    /**
     * İstekten temel URL'yi alır
     */
    private String getBaseUrl(HttpServletRequest request) {
        String scheme = request.getScheme();
        String serverName = request.getServerName();
        int serverPort = request.getServerPort();
        String contextPath = request.getContextPath();
        
        StringBuilder baseUrl = new StringBuilder();
        baseUrl.append(scheme).append("://").append(serverName);
        
        if ((scheme.equals("http") && serverPort != 80) || 
            (scheme.equals("https") && serverPort != 443)) {
            baseUrl.append(":").append(serverPort);
        }
        
        baseUrl.append(contextPath);
        return baseUrl.toString();
    }
}
