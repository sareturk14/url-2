package tr.edu.duzce.mf.bm.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class AppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer {

    @Override
    protected Class<?>[] getRootConfigClasses() {
<<<<<<< HEAD
        // Veritabanı ve temel ayar sınıfımızı veriyoruz
        return new Class[]{DatabaseConfig.class};
=======
        return new Class<?>[] { HibernateConfig.class };
>>>>>>> 2a3b034 (algoritmanın temelini attım)
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
<<<<<<< HEAD
        // Web (MVC) ayar sınıfımızı veriyoruz
        return new Class[]{WebConfig.class};
=======
        return new Class<?>[] { WebConfig.class };
>>>>>>> 2a3b034 (algoritmanın temelini attım)
    }

    @Override
    protected String[] getServletMappings() {
<<<<<<< HEAD
        // Gelen BÜTÜN istekleri ("/") Spring yönlendirsin diyoruz
        return new String[]{"/"};
=======
        return new String[] { "/" };
>>>>>>> 2a3b034 (algoritmanın temelini attım)
    }
}