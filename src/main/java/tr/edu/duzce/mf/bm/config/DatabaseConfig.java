package tr.edu.duzce.mf.bm.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.hibernate.HibernateTransactionManager;
import org.springframework.orm.jpa.hibernate.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;
import java.util.Properties;

@Configuration // Spring'e "Bu bir ayar sınıfıdır, XML arama buraya bak" diyoruz.
@EnableTransactionManagement // Veritabanı işlemlerinde hata olursa işlemleri geri almak (rollback) için.
public class DatabaseConfig {

    // 1. Veritabanı Bağlantı Havuzu (c3p0) Gereksinimi
    @Bean
    public DataSource dataSource() {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        try {
            // Docker'daki MySQL bağlantı bilgilerimiz
            dataSource.setDriverClass("com.mysql.cj.jdbc.Driver");
            // Docker'da çalışırken host.docker.internal, lokalde çalışırken localhost
            String dbHost = System.getenv().getOrDefault("DB_HOST", "localhost");
            dataSource.setJdbcUrl("jdbc:mysql://" + dbHost + ":3306/url_shortener_db?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true");
            dataSource.setUser("admin");
            dataSource.setPassword("admin123");

            // c3p0 Havuz Ayarları (Performans için)
            dataSource.setMinPoolSize(5);
            dataSource.setMaxPoolSize(20);
            dataSource.setMaxIdleTime(3000);
        } catch (PropertyVetoException e) {
            throw new RuntimeException("Veritabanı sürücüsü yüklenirken hata oluştu!", e);
        }
        return dataSource;
    }

    // 2. Hibernate Gereksinimi
    @Bean
    public LocalSessionFactoryBean sessionFactory() {
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
        sessionFactory.setDataSource(dataSource()); // Yukarıdaki c3p0'ı buraya bağladık

        // Entity sınıflarımızı nerede bulacağını söylüyoruz
        sessionFactory.setPackagesToScan("tr.edu.duzce.mf.bm.entity");

        Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
        hibernateProperties.setProperty("hibernate.show_sql", "true"); // Arka planda dönen SQL'i konsola basar

        // EN ÖNEMLİ KISIM: Tablo yoksa, Entity sınıfımıza bakıp Docker'da tabloyu kendi oluşturacak!
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");

        sessionFactory.setHibernateProperties(hibernateProperties);
        return sessionFactory;
    }

    // 3. İşlem (Transaction) Yöneticisi
    @Bean
    public HibernateTransactionManager transactionManager() {
        HibernateTransactionManager transactionManager = new HibernateTransactionManager();
        transactionManager.setSessionFactory(sessionFactory().getObject());
        return transactionManager;
    }
}