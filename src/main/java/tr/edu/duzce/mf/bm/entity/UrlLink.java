package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "url_links")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UrlLink {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;
    
    @Column(name = "short_code", nullable = false, unique = true, length = 10)
    private String shortCode;
    
    @Column(name = "click_count", nullable = false)
    private int clickCount;
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        clickCount = 0;
    }
}
