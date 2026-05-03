# Örnek Uygulama Logları (test-logs)

Aşağıda uygulamanın çalıştırılması sırasında konsola ve dosyaya yazdırılan örnek `log4j2` log çıktıları yer almaktadır:

```log
2026-05-03 01:15:23,124 INFO  [main] tr.edu.duzce.mf.bm.config.AppInitializer - Web application starting...
2026-05-03 01:15:25,450 INFO  [main] org.hibernate.dialect.Dialect - HHH000400: Using dialect: org.hibernate.dialect.MySQLDialect
2026-05-03 01:15:26,112 INFO  [main] com.mchange.v2.c3p0.impl.AbstractPoolBackedDataSource - Initializing c3p0 pool... com.mchange.v2.c3p0.ComboPooledDataSource [ java.beans.PropertyChangeSupport@5c29bfd ... ]
2026-05-03 01:15:28,301 INFO  [http-nio-8080-exec-1] tr.edu.duzce.mf.bm.config.RequestLoggingInterceptor - [PRE-HANDLE] Request URI: /bm470/, Method: GET, Parameters: {}
2026-05-03 01:15:28,345 INFO  [http-nio-8080-exec-1] tr.edu.duzce.mf.bm.config.RequestLoggingInterceptor - [POST-HANDLE] ViewName: index, Model: {urls=[]}
2026-05-03 01:15:35,112 INFO  [http-nio-8080-exec-3] tr.edu.duzce.mf.bm.config.RequestLoggingInterceptor - [PRE-HANDLE] Request URI: /bm470/shorten, Method: POST, Parameters: {originalUrl=[https://github.com]}
2026-05-03 01:15:35,140 INFO  [http-nio-8080-exec-3] tr.edu.duzce.mf.bm.service.AiServiceImpl - İstek AI servisine gönderiliyor: https://github.com
2026-05-03 01:15:35,850 INFO  [http-nio-8080-exec-3] tr.edu.duzce.mf.bm.service.AiServiceImpl - AI servisinden yanıt alındı, özet: GitHub is a developer platform...
2026-05-03 01:15:35,901 INFO  [http-nio-8080-exec-3] org.hibernate.engine.transaction.internal.TransactionImpl - commiting
2026-05-03 01:15:35,910 INFO  [http-nio-8080-exec-3] tr.edu.duzce.mf.bm.config.RequestLoggingInterceptor - [POST-HANDLE] ViewName: redirect:/, Model: {shortUrl=http://localhost:8080/bm470/b, originalUrl=https://github.com, shortCode=b, aiSummary=GitHub is a developer platform...}
2026-05-03 01:16:01,234 INFO  [http-nio-8080-exec-5] tr.edu.duzce.mf.bm.config.RequestLoggingInterceptor - [PRE-HANDLE] Request URI: /bm470/b, Method: GET, Parameters: {}
2026-05-03 01:16:01,240 INFO  [http-nio-8080-exec-5] org.hibernate.engine.transaction.internal.TransactionImpl - commiting
2026-05-03 01:16:01,245 INFO  [http-nio-8080-exec-5] tr.edu.duzce.mf.bm.config.RequestLoggingInterceptor - [POST-HANDLE] ViewName: redirect:https://github.com, Model: {}
```

### Log Formatı Açıklaması
Loglar, Interceptor yardımıyla gelen tüm istekleri yakalar. Uygulama çalışırken kullanıcı `https://github.com` adresini kısaltmak için istek atar. Bu istek `/shorten` endpointine gelir. Spring `UrlController` bunu alır ve `UrlService` katmanına iletir. `AiService` harici Python test servisine HTTP isteği atarak URL özetini ve metin analizini çeker. Veritabanına kaydettikten sonra ID `b` Base62 değerine dönüştürülür ve kullanıcı `http://localhost:8080/bm470/b` adresine sahip olur.
