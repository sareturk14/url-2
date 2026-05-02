package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Entity
@Table(name = "url_analytics")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ClickAnalytics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "click_time")
    private LocalDateTime clickTime;

    @Column(name = "ip_address", length = 45)
    private String ipAddress;

    @Column(length = 500)
    private String referer; // Kullanıcının linke nereden tıklayıp geldiği

    // Bir URL'nin birden fazla tıklama istatistiği olabilir
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "url_id", nullable = false)
    private UrlLink urlLink;

    @PrePersist
    protected void onCreate() {
        this.clickTime = LocalDateTime.now();
    }
}