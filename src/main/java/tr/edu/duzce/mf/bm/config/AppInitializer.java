package tr.edu.duzce.mf.bm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
        // Veritabanı ve temel ayar sınıfımızı veriyoruz
        return new Class<?>[] { DatabaseConfig.class };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        // Web (MVC) ayar sınıfımızı veriyoruz
        return new Class[]{WebConfig.class};
    }

    @Override
    protected String[] getServletMappings() {
        // Gelen BÜTÜN istekleri ("/") Spring yönlendirsin diyoruz
        return new String[]{"/"};
    }
}