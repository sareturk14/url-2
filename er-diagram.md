# URL Kısaltma Sistemi E-R Diyagramı

Aşağıdaki E-R (Entity-Relationship) diyagramı uygulamanın güncel veritabanı şemasını göstermektedir:

```mermaid
erDiagram
    URL_LINKS {
        Long id PK
        String original_url
        String short_code UK
        String category
        String summary
        int click_count
        LocalDateTime created_at
        Long user_id FK
    }

    USERS {
        Long id PK
        String username
        String email
        String password
    }

    USERS ||--o{ URL_LINKS : "creates"
```

Açıklamalar:
- **URL_LINKS** tablosu oluşturulan kısa kodları, orijinal url adreslerini, tıklanma sayılarını ve AI servisi tarafından üretilen analizleri (summary, category) tutar.
- **USERS** tablosu kullanıcı hesapları için ayrılmıştır ve linklerle One-To-Many ilişkisine sahiptir.
