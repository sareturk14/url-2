package tr.edu.duzce.mf.bm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import tr.edu.duzce.mf.bm.entity.UrlLink;
import tr.edu.duzce.mf.bm.service.UrlService;

import java.util.List;

@Controller
public class UrlController {

    @Autowired
    private UrlService urlService;

    // ANA SAYFA: Senin yazdığın, listeyi ekrana getiren mükemmel kısım (Bunu koruduk!)
    @GetMapping("/")
    public String index(Model model) {
        List<UrlLink> urls = urlService.getAllUrls();
        model.addAttribute("urls", urls);
        return "index";
    }

    // KISALTMA: Arkadaşının güvenlik kontrolleri + Senin listeyi yenileme mantığın (Harmanladık!)
    @PostMapping("/shorten")
    public String shortenUrl(@RequestParam("originalUrl") String originalUrl, org.springframework.web.servlet.mvc.support.RedirectAttributes redirectAttributes) {
        // Arkadaşının eklediği boş link kontrolü
        if (originalUrl == null || originalUrl.trim().isEmpty()) {
            return "redirect:/";
        }

        // Arkadaşının eklediği http:// kontrolü
        if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
            originalUrl = "http://" + originalUrl;
        }

        try {
            UrlLink createdLink = urlService.createShortUrl(originalUrl);
            // Frontend için mesajları RedirectAttributes ile gönderelim
            redirectAttributes.addFlashAttribute("shortUrl", "http://localhost:8080/bm470/" + createdLink.getShortCode());
            redirectAttributes.addFlashAttribute("originalUrl", createdLink.getOriginalUrl());
            redirectAttributes.addFlashAttribute("shortCode", createdLink.getShortCode());
            redirectAttributes.addFlashAttribute("aiSummary", createdLink.getSummary());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Hata oluştu: " + e.getMessage());
        }

        return "redirect:/"; // İşlem bitince listeyi güncellemek ve sonuçları göstermek için ana sayfaya dön
    }

    // TIKLAMA: Spring 6 hatasını çözdüğümüz senin kodun (Bunu kesinlikle korumalıydık!)
    @GetMapping("/{shortCode}")
    public RedirectView redirectUrl(@PathVariable("shortCode") String shortCode) {
        String originalUrl = urlService.getOriginalUrl(shortCode);

        if (originalUrl != null) {
            if (!originalUrl.startsWith("http://") && !originalUrl.startsWith("https://")) {
                originalUrl = "http://" + originalUrl;
            }
            return new RedirectView(originalUrl);
        }

        return new RedirectView("/");
    }
}