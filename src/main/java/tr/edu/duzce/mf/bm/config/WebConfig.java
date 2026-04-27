package tr.edu.duzce.mf.bm.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import java.time.Duration;
import java.util.Locale;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "tr.edu.duzce.mf.bm") // Spring'e tüm paketleri taramasını söyler
public class WebConfig implements WebMvcConfigurer {

    // 1. Çoklu Dil Desteği - Mesaj Dosyalarının Yeri
    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource source = new ResourceBundleMessageSource();
        source.setBasename("messages"); // resources altındaki messages.properties'i okur
        source.setDefaultEncoding("UTF-8");
        return source;
    }

    // 2. Zorunlu Türkçe Varsayılanı ve Dil Değişimini Hafızada Tutma
    @Bean
    public CookieLocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("tr")); // Varsayılan dil Türkçe
        resolver.setCookieName("language-cookie");
        resolver.setCookieMaxAge(Duration.ofHours(1));
        return resolver;
    }

    // 3. JSP Sayfalarının Yolunu Belirleme
    @Bean
    public InternalResourceViewResolver viewResolver() {
        InternalResourceViewResolver resolver = new InternalResourceViewResolver();
        resolver.setPrefix("/WEB-INF/views/"); // Sayfalarımızı bu klasöre koyacağız
        resolver.setSuffix(".jsp");
        return resolver;
    }

    // 4. Interceptor Kayıtları (Araya girip işlem yapacak sınıflar)
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // Dil değiştirme parametresi (Örn: localhost:8080/bm470?lang=en)
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor);

        // Proje gereksinimi: Bütün istekleri loglayacak kendi Interceptor'ımız
        registry.addInterceptor(new RequestLoggingInterceptor());
    }
}{
}
