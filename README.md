# 🔗 Kurumsal URL Kısaltma Sistemi (Bitly Benzeri)

> **Düzce Üniversitesi - Bilgisayar Mühendisliği**  
> **BM470 İleri Java Programlama Dersi - Dönem Projesi**

Spring MVC tabanlı, tamamen XML'siz (annotation-based) modern mimaride geliştirilmiş kurumsal URL kısaltma uygulaması. Uzun URL'leri 6 haneli benzersiz kısa kodlara dönüştürür ve tıklama istatistiklerini takip eder.

---

## 📑 İçindekiler

1. [Proje Özellikleri](#-proje-özellikleri)
2. [Teknoloji Stack'i](#-teknoloji-stacki)
3. [Mimari Yapı](#-mimari-yapı)
4. [Proje Dizin Yapısı](#-proje-dizin-yapısı)
5. [Katmanların Detaylı Açıklaması](#-katmanların-detaylı-açıklaması)
6. [Kullanılan Annotation'lar](#-kullanılan-annotationlar)
7. [Veritabanı Şeması](#-veritabanı-şeması)
8. [Endpoint'ler](#-endpointler)
9. [Çoklu Dil (i18n) Desteği](#-çoklu-dil-i18n-desteği)
10. [Kurulum ve Çalıştırma](#-kurulum-ve-çalıştırma)
11. [Test Senaryoları](#-test-senaryoları)
12. [Karşılaşılan Sorunlar ve Çözümler](#-karşılaşılan-sorunlar-ve-çözümler)

---

## ✨ Proje Özellikleri

- ✅ **URL Kısaltma**: Uzun URL'leri 6 haneli (62^6 = ~56.8 milyar) benzersiz kısa kodlara dönüştürme
- ✅ **Otomatik Yönlendirme**: Kısa kod ile orijinal URL'ye otomatik redirect
- ✅ **Tıklama Sayacı**: Her kısa link için tıklama sayısı takibi
- ✅ **Çoklu Dil Desteği**: Türkçe ve İngilizce arayüz (i18n)
- ✅ **Modern UI**: Responsive tasarım, gradient renkler, hover efektleri
- ✅ **Connection Pool**: c3p0 ile veritabanı bağlantı havuzu (5-20 connection)
- ✅ **Transaction Yönetimi**: `@Transactional` ile otomatik rollback
- ✅ **Request Logging**: Her HTTP isteği için interceptor ile log kaydı
- ✅ **XML'siz Konfigürasyon**: %100 Java annotation tabanlı
- ✅ **Docker Desteği**: MySQL ve Tomcat container'larda çalışır

---

## 🛠 Teknoloji Stack'i

### **Backend Framework**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **Spring Framework** | 7.0.5 | IoC Container, MVC, DI |
| **Spring Web MVC** | 7.0.5 | Web katmanı, DispatcherServlet |
| **Spring ORM** | 7.0.5 | Hibernate entegrasyonu |
| **Spring TX** | 7.0.5 | Transaction yönetimi |
| **Spring Context Support** | 7.0.5 | ResourceBundle, MessageSource |

### **Persistence (Veri Erişim)**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **Hibernate ORM** | 7.2.5.Final | JPA implementasyonu, entity yönetimi |
| **Hibernate c3p0** | 7.2.5.Final | Connection pool entegrasyonu |
| **c3p0** | 0.12.0 | JDBC connection pooling |
| **MySQL Connector/J** | 9.6.0 | MySQL JDBC sürücüsü |
| **Jakarta Persistence API** | 3.2.0 | JPA standart spesifikasyonu |
| **Jakarta Transaction API** | 2.0.1 | JTA standardı |

### **Veritabanı**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **MySQL** | 8.0 | İlişkisel veritabanı (Docker'da) |

### **Web / Servlet Katmanı**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **Jakarta Servlet API** | 6.1.0 | Servlet spesifikasyonu |
| **Jakarta JSP/JSTL API** | 3.0.0 | JSP standart kütüphane |
| **Apache Tomcat** | 10.1 (jdk17) | Servlet container |
| **GlassFish JSTL** | 3.0.1 | JSTL implementasyonu |

### **Logging**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **SLF4J API** | 2.0.17 | Logging facade |
| **SLF4J Reload4j** | 2.0.17 | SLF4J → Log4j köprüsü |
| **Apache Log4j 2** | 2.25.3 | Log4j2 core |

### **Yardımcı Kütüphaneler**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **Lombok** | 1.18.42 | Boilerplate kod azaltma (@Data, @Getter) |
| **Apache Commons Lang3** | 3.20.0 | String/Object yardımcı metodlar |
| **DataFaker** | 2.5.4 | Test verisi üretimi |
| **JSON-lib** | 2.4 | JSON serialization |

### **Build & Geliştirme**
| Teknoloji | Versiyon | Kullanım Amacı |
|-----------|----------|----------------|
| **Maven** | 3.x | Bağımlılık yönetimi, build |
| **Java** | 17+ | Programlama dili (release 17) |
| **Docker** | 28.5.1 | Container orchestration |

---

## 🏗 Mimari Yapı

Proje **çok katmanlı (N-Tier) mimari** prensibine göre tasarlanmıştır:

```
┌──────────────────────────────────────────────────────────────┐
│                     KULLANICI (Browser)                      │
└─────────────────────────────┬────────────────────────────────┘
                              │ HTTP Request
                              ▼
┌──────────────────────────────────────────────────────────────┐
│  PRESENTATION LAYER (View)                                   │
│  • index.jsp (JSP + JSTL + spring:message)                   │
│  • CSS/HTML (Responsive)                                     │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│  WEB LAYER (Controller)                                      │
│  • UrlController (@Controller)                               │
│  • RequestLoggingInterceptor (HandlerInterceptor)            │
│  • LocaleChangeInterceptor (i18n)                            │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│  BUSINESS LAYER (Service)                                    │
│  • UrlService (Interface)                                    │
│  • UrlServiceImpl (@Service, @Transactional)                 │
│  • Kısa kod üretim algoritması (SecureRandom)                │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│  DATA ACCESS LAYER (DAO)                                     │
│  • UrlDao (Interface)                                        │
│  • UrlDaoImpl (@Repository)                                  │
│  • Hibernate SessionFactory + HQL                            │
└─────────────────────────────┬────────────────────────────────┘
                              │
                              ▼
┌──────────────────────────────────────────────────────────────┐
│  PERSISTENCE LAYER                                           │
│  • UrlLink Entity (@Entity)                                  │
│  • c3p0 Connection Pool                                      │
│  • Hibernate ORM                                             │
└─────────────────────────────┬────────────────────────────────┘
                              │ JDBC
                              ▼
┌──────────────────────────────────────────────────────────────┐
│  DATABASE (MySQL 8 - Docker Container)                       │
│  • url_shortener_db.url_links tablosu                        │
└──────────────────────────────────────────────────────────────┘
```

---

## 📁 Proje Dizin Yapısı

```
URL_kisaltma/
├── pom.xml                                    # Maven bağımlılık dosyası
├── docker-compose.yml                         # MySQL + Tomcat orkestrasyon
├── README.md                                  # Bu dosya
│
├── src/main/
│   ├── java/tr/edu/duzce/mf/bm/
│   │   │
│   │   ├── config/                            # Konfigürasyon Katmanı
│   │   │   ├── AppInitializer.java            # Web app başlatıcı (web.xml yerine)
│   │   │   ├── DatabaseConfig.java            # DataSource, SessionFactory, TxManager
│   │   │   ├── WebConfig.java                 # MVC, ViewResolver, i18n, Interceptor
│   │   │   └── RequestLoggingInterceptor.java # HTTP istek logger
│   │   │
│   │   ├── entity/                            # Domain (Model) Katmanı
│   │   │   └── UrlLink.java                   # JPA Entity (@Entity)
│   │   │
│   │   ├── dao/                               # Veri Erişim Katmanı
│   │   │   ├── UrlDao.java                    # DAO Interface
│   │   │   └── UrlDaoImpl.java                # Hibernate impl (@Repository)
│   │   │
│   │   ├── service/                           # İş Mantığı Katmanı
│   │   │   ├── UrlService.java                # Service Interface
│   │   │   └── UrlServiceImpl.java            # Impl (@Service, @Transactional)
│   │   │
│   │   └── controller/                        # Web (HTTP) Katmanı
│   │       └── UrlController.java             # REST endpoints (@Controller)
│   │
│   ├── resources/
│   │   ├── messages_tr.properties             # Türkçe çeviriler
│   │   └── messages_en.properties             # İngilizce çeviriler
│   │
│   └── webapp/
│       └── WEB-INF/
│           └── views/
│               └── index.jsp                  # Ana sayfa (JSP + JSTL)
│
└── target/
    └── bm470.war                              # Build çıktısı (deploy edilecek)
```

---

## 🎯 Katmanların Detaylı Açıklaması

### **1. Konfigürasyon Katmanı (`config/`)**

#### **`AppInitializer.java`**
- **Amaç**: `web.xml` yerine programatik olarak DispatcherServlet'i kaydeder.
- **Extends**: `AbstractAnnotationConfigDispatcherServletInitializer`
- **Override edilen metodlar**:
  - `getRootConfigClasses()` → `DatabaseConfig.class` (Root Application Context)
  - `getServletConfigClasses()` → `WebConfig.class` (Servlet Context)
  - `getServletMappings()` → `"/"` (tüm istekleri yakala)

#### **`DatabaseConfig.java`**
- **Annotations**: `@Configuration`, `@EnableTransactionManagement`
- **Bean'ler**:
  - **`dataSource()`** → `ComboPooledDataSource` (c3p0)
    - Driver: `com.mysql.cj.jdbc.Driver`
    - URL: `jdbc:mysql://${DB_HOST}:3306/url_shortener_db`
    - MinPool: 5, MaxPool: 20, MaxIdleTime: 3000s
  - **`sessionFactory()`** → `LocalSessionFactoryBean`
    - Entity scan: `tr.edu.duzce.mf.bm.entity`
    - Dialect: `org.hibernate.dialect.MySQLDialect`
    - `hbm2ddl.auto=update` (tabloyu otomatik oluştur/güncelle)
    - `show_sql=true` (SQL'leri konsola bas)
  - **`transactionManager()`** → `HibernateTransactionManager`

#### **`WebConfig.java`**
- **Annotations**: `@Configuration`, `@EnableWebMvc`, `@ComponentScan("tr.edu.duzce.mf.bm")`
- **Implements**: `WebMvcConfigurer`
- **Bean'ler**:
  - **`messageSource()`** → `ResourceBundleMessageSource` (basename: `messages`, encoding: UTF-8)
  - **`localeResolver()`** → `CookieLocaleResolver` (cookie: `language-cookie`, default: `tr`, maxAge: 1h)
  - **`viewResolver()`** → `InternalResourceViewResolver` (prefix: `/WEB-INF/views/`, suffix: `.jsp`)
- **`addInterceptors()`**:
  - `LocaleChangeInterceptor` (param: `lang`)
  - `RequestLoggingInterceptor`

#### **`RequestLoggingInterceptor.java`**
- **Implements**: `HandlerInterceptor`
- **Override edilen metodlar**:
  - `preHandle()` → URI, HTTP metot ve parametreleri loglar
  - `postHandle()` → View adı ve model verilerini loglar

### **2. Entity Katmanı (`entity/`)**

#### **`UrlLink.java`**
- **Annotations**: `@Entity`, `@Table(name="url_links")`, `@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`
- **Alanlar**:
  | Alan | Tip | Annotation | Açıklama |
  |------|-----|------------|----------|
  | `id` | `Long` | `@Id`, `@GeneratedValue(IDENTITY)` | Birincil anahtar |
  | `originalUrl` | `String` | `@Column(length=2048, nullable=false)` | Asıl uzun URL |
  | `shortCode` | `String` | `@Column(length=10, unique=true, nullable=false)` | 6 haneli kısa kod |
  | `clickCount` | `int` | `@Column(nullable=false)` | Tıklama sayacı |
  | `createdAt` | `LocalDateTime` | `@Column(nullable=false)` | Oluşturulma zamanı |
- **Lifecycle**:
  - `@PrePersist` → `onCreate()` ile `createdAt` ve `clickCount` otomatik set edilir.

### **3. DAO Katmanı (`dao/`)**

#### **`UrlDao.java` (Interface)**
```java
void save(UrlLink urlLink);
UrlLink findByShortCode(String shortCode);
boolean existsByShortCode(String shortCode);
```

#### **`UrlDaoImpl.java`**
- **Annotations**: `@Repository`
- **Bağımlılıklar**: `@Autowired private SessionFactory sessionFactory;`
- **Metodlar**:
  - `save()` → `session.persist(urlLink)`
  - `findByShortCode()` → HQL: `FROM UrlLink u WHERE u.shortCode = :shortCode`
  - `existsByShortCode()` → HQL: `SELECT COUNT(u) FROM UrlLink u WHERE u.shortCode = :shortCode`

### **4. Service Katmanı (`service/`)**

#### **`UrlService.java` (Interface)**
```java
UrlLink createShortUrl(String originalUrl);
UrlLink getUrlAndIncrementClick(String shortCode);
boolean isShortCodeAvailable(String shortCode);
```

#### **`UrlServiceImpl.java`**
- **Annotations**: `@Service`, `@Transactional`
- **Bağımlılıklar**: `@Autowired private UrlDao urlDao;`
- **Sabitler**:
  - `CHARACTERS` = `[A-Za-z0-9]` (62 karakter)
  - `SHORT_CODE_LENGTH` = 6
- **Algoritma (Kısa Kod Üretimi)**:
  ```
  do {
      shortCode = generateShortCode();   // SecureRandom ile 6 karakter
  } while (urlDao.existsByShortCode(shortCode));  // Çakışma varsa tekrarla
  ```
- **Olasılık**: 62^6 = **56,800,235,584** benzersiz kod

### **5. Controller Katmanı (`controller/`)**

#### **`UrlController.java`**
- **Annotations**: `@Controller`
- **Bağımlılıklar**: `@Autowired private UrlService urlService;`
- **Endpoint'ler**:

| HTTP Metot | URL | Annotation | Metot | Açıklama |
|------------|-----|------------|-------|----------|
| `GET` | `/` | `@GetMapping("/")` | `home()` | Ana sayfayı gösterir |
| `POST` | `/shorten` | `@PostMapping("/shorten")` | `shortenUrl()` | URL'yi kısaltır |
| `GET` | `/{shortCode}` | `@GetMapping("/{shortCode}")` | `redirectToOriginalUrl()` | Kısa koddan orijinale redirect |

### **6. View Katmanı (`webapp/WEB-INF/views/`)**

#### **`index.jsp`**
- **Taglib'ler**:
  - `<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>` (i18n)
  - `<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>` (JSTL)
- **Form**: POST → `/shorten` (input: `originalUrl`)
- **i18n**: `<spring:message code="..."/>` ile dinamik metinler
- **CSS**: Inline modern responsive design (gradient, hover, transition)

---

## 🏷 Kullanılan Annotation'lar

### **Spring Core / Context**
| Annotation | Yer | Amaç |
|------------|-----|------|
| `@Configuration` | `DatabaseConfig`, `WebConfig` | Bean tanımı içeren konfigürasyon sınıfı |
| `@Bean` | Bean üreten metodlar | Spring container'a bean kaydet |
| `@ComponentScan` | `WebConfig` | Belirtilen pakette `@Component`'leri tara |
| `@Autowired` | Field/Constructor | Bağımlılık enjeksiyonu (DI) |

### **Spring Stereotype**
| Annotation | Yer | Amaç |
|------------|-----|------|
| `@Controller` | `UrlController` | Web katmanı bileşeni |
| `@Service` | `UrlServiceImpl` | İş mantığı bileşeni |
| `@Repository` | `UrlDaoImpl` | Veri erişim bileşeni (exception translation) |

### **Spring MVC**
| Annotation | Yer | Amaç |
|------------|-----|------|
| `@EnableWebMvc` | `WebConfig` | Spring MVC'yi aktive et |
| `@GetMapping` | `home()`, `redirectToOriginalUrl()` | HTTP GET endpoint |
| `@PostMapping` | `shortenUrl()` | HTTP POST endpoint |
| `@RequestParam` | Form parametreleri | Query/Form parametresi |
| `@PathVariable` | `{shortCode}` | URL path değişkeni |

### **Spring Transaction**
| Annotation | Yer | Amaç |
|------------|-----|------|
| `@EnableTransactionManagement` | `DatabaseConfig` | TX yönetimini aktive et |
| `@Transactional` | `UrlServiceImpl` (class + method) | Otomatik transaction sınırı + rollback |

### **JPA / Jakarta Persistence**
| Annotation | Yer | Amaç |
|------------|-----|------|
| `@Entity` | `UrlLink` | JPA entity sınıfı |
| `@Table(name="url_links")` | `UrlLink` | Tablo adı |
| `@Id` | `id` field | Birincil anahtar |
| `@GeneratedValue(strategy=IDENTITY)` | `id` field | Auto-increment |
| `@Column` | Tüm field'lar | Sütun ayarları (length, nullable, unique) |
| `@PrePersist` | `onCreate()` | Insert öncesi callback |

### **Lombok**
| Annotation | Yer | Amaç |
|------------|-----|------|
| `@Data` | `UrlLink` | Getter/Setter/toString/equals/hashCode |
| `@NoArgsConstructor` | `UrlLink` | Boş constructor |
| `@AllArgsConstructor` | `UrlLink` | Tüm alanlı constructor |

---

## 🗄 Veritabanı Şeması

**Veritabanı**: `url_shortener_db`  
**Tablo**: `url_links`

```sql
CREATE TABLE url_links (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    original_url VARCHAR(2048) NOT NULL,
    short_code   VARCHAR(10)   NOT NULL UNIQUE,
    click_count  INT           NOT NULL DEFAULT 0,
    created_at   DATETIME(6)   NOT NULL
);
```

> **Not**: Tablo Hibernate `hbm2ddl.auto=update` ile otomatik oluşturulur.

---

## 🌐 Endpoint'ler

| HTTP | Endpoint | Açıklama | Örnek |
|------|----------|----------|-------|
| `GET` | `/` | Ana sayfa | `http://localhost:8080/bm470/` |
| `POST` | `/shorten` | URL kısaltma | Form: `originalUrl=https://google.com` |
| `GET` | `/{shortCode}` | Yönlendirme | `/aB3xY7` → `https://google.com` |
| `GET` | `/?lang=tr` | Türkçe arayüz | Cookie set eder |
| `GET` | `/?lang=en` | İngilizce arayüz | Cookie set eder |

---

## 🌍 Çoklu Dil (i18n) Desteği

### **Mekanizma**
1. `WebConfig.messageSource()` → `messages_tr.properties` ve `messages_en.properties` okur
2. `WebConfig.localeResolver()` → `CookieLocaleResolver` ile dili cookie'de saklar
3. `LocaleChangeInterceptor` → `?lang=tr` veya `?lang=en` parametresiyle değiştirir
4. JSP'de `<spring:message code="..."/>` ile dinamik metin

### **Mesaj Anahtarları**
```properties
app.title              # Sayfa başlığı
app.subtitle           # Alt başlık
form.url.label         # URL input label
form.url.placeholder   # URL input placeholder
form.button.shorten    # Kısalt butonu
result.title           # Sonuç başlığı
result.original        # "Orijinal URL"
result.short           # "Kısaltılmış URL"
result.code            # "Kısa Kod"
error.title            # "Hata"
```

---

## 🚀 Kurulum ve Çalıştırma

### **Ön Gereksinimler**
- Java 17+ (JAVA_HOME ayarlı)
- Docker Desktop
- Maven (veya IntelliJ IDEA dahili Maven)

### **1. Projeyi Build Et**
```bash
mvn clean package -DskipTests
```
Çıktı: `target/bm470.war`

### **2. Docker Compose ile Çalıştır (MySQL + Tomcat)**
```bash
docker-compose up -d
```
Bu tek komut hem MySQL'i hem Tomcat'i başlatır. Tomcat otomatik olarak `bm470.war`'ı deploy eder.

### **3. Uygulamaya Eriş**
🌐 **http://localhost:8080/bm470/**

### **4. Durdur ve Baştan Başlat**
```bash
docker-compose down
mvn clean package -DskipTests
docker-compose up -d
```

---

## 🧪 Test Senaryoları

| # | Senaryo | Beklenen Sonuç |
|---|---------|----------------|
| 1 | Ana sayfayı aç (`/`) | Form ve TR/EN butonları görünür |
| 2 | URL form'unu boş gönder | "URL boş olamaz!" hatası |
| 3 | `https://www.google.com` gönder | 6 haneli kısa kod üretilir |
| 4 | Üretilen kısa linke tıkla | Google'a yönlendirir |
| 5 | `?lang=en` parametresi ekle | Arayüz İngilizce olur |
| 6 | MySQL'de `url_links` sorgula | `click_count` artmış olur |
| 7 | Olmayan kısa kod (`/xyz123`) | Ana sayfaya redirect |

---

## 🔧 Karşılaşılan Sorunlar ve Çözümler

| # | Sorun | Çözüm |
|---|-------|-------|
| 1 | `WebConfig.java`'da çift `package` deklarasyonu | Boş class ve duplicate package silindi |
| 2 | `AppInitializer.java`'da boş class + duplicate package | Aynı şekilde temizlendi |
| 3 | `RequestLoggingInterceptor.java` aynı sorun | Düzeltildi |
| 4 | Yanlış yazılmış paketler (`contoller`, `doa`, `entitiy`) | Doğru paketler oluşturulup eski boşlar silindi |
| 5 | Maven `release version 18 not supported` | `pom.xml` → release 17 (JAVA_HOME uyumu) |
| 6 | Spring 7'de `CookieLocaleResolver.setCookieName()` kaldırıldı | Constructor ile geçirildi |
| 7 | Docker container'dan `localhost:3306` MySQL'e ulaşılmıyor | `DB_HOST=host.docker.internal` env var |
| 8 | Hibernate 6+: `Could not interpret path expression 'u'` | HQL'e alias eklendi: `FROM UrlLink u WHERE u.shortCode` |
| 9 | `UrlServiceImpl` kısa kod üretiminde mantık hatası | `while (!exists)` → `while (exists)` |

---

## 📝 BM470 Ders Gereksinimleri Karşılama Tablosu

| Gereksinim | Karşılandı mı? | Nerede? |
|------------|:--:|---------|
| Spring MVC (XML'siz) | ✅ | `AppInitializer`, `WebConfig` |
| Hibernate ORM | ✅ | `DatabaseConfig.sessionFactory()` |
| c3p0 Connection Pool | ✅ | `DatabaseConfig.dataSource()` |
| Maven Build | ✅ | `pom.xml` |
| MySQL (Docker) | ✅ | `mysql-url-shortener` container |
| Çoklu Dil (i18n) | ✅ | `messages_tr/en.properties`, `localeResolver()` |
| @Transactional | ✅ | `UrlServiceImpl` |
| Logging (SLF4J + Log4j2) | ✅ | `RequestLoggingInterceptor` |
| Interceptor | ✅ | `RequestLoggingInterceptor`, `LocaleChangeInterceptor` |
| Layered Architecture | ✅ | Controller → Service → DAO → Entity |
| JSP + JSTL View | ✅ | `WEB-INF/views/index.jsp` |
| DAO Pattern (Interface + Impl) | ✅ | `UrlDao`, `UrlDaoImpl` |
| Service Pattern (Interface + Impl) | ✅ | `UrlService`, `UrlServiceImpl` |

---

## 👤 Geliştirici

**Düzce Üniversitesi - Bilgisayar Mühendisliği**  
**BM470 İleri Java Programlama** - Dönem Projesi

---

## 📄 Lisans

Bu proje akademik amaçlı geliştirilmiştir.