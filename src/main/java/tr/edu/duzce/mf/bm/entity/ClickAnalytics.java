package tr.edu.duzce.mf.bm.entity;

import java.time.LocalDateTime;

/**
 * Bu sınıf artık kullanılmamaktadır.
 * Tıklama analitikleri 'logs' tablosu ve Log entity'si üzerinden yönetilmektedir.
 * @deprecated Log entity'sini kullanın.
 */
@Deprecated
public class ClickAnalytics {

    private Long id;
    private LocalDateTime clickTime;
    private String ipAddress;
    private String referer;
}
