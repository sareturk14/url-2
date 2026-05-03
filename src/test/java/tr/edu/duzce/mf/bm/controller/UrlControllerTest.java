package tr.edu.duzce.mf.bm.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import tr.edu.duzce.mf.bm.entity.UrlLink;
import tr.edu.duzce.mf.bm.service.UrlService;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class UrlControllerTest {

    @Mock
    private UrlService urlService;

    @Mock
    private Model model;

    @Mock
    private RedirectAttributes redirectAttributes;

    @InjectMocks
    private UrlController urlController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testIndex() {
        // Sahte veri döndür
        when(urlService.getAllUrls()).thenReturn(Collections.emptyList());

        // Ana sayfayı getir
        String viewName = urlController.index(model);

        // Beklenen sonuçlar
        assertEquals("index", viewName, "Index sayfası çağrıldığında 'index' görünümü dönmelidir.");
        verify(urlService, times(1)).getAllUrls();
    }

    @Test
    void testShortenUrl() {
        // Yeni url ekle
        String originalUrl = "http://example.com";
        UrlLink mockLink = new UrlLink();
        mockLink.setOriginalUrl(originalUrl);
        mockLink.setShortCode("abc");

        when(urlService.createShortUrl(originalUrl)).thenReturn(mockLink);

        String viewName = urlController.shortenUrl(originalUrl, redirectAttributes);

        assertEquals("redirect:/", viewName, "Kısaltma işleminden sonra ana sayfaya dönmelidir.");
        verify(urlService, times(1)).createShortUrl(originalUrl);
    }
}
