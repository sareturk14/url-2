package tr.edu.duzce.mf.bm.entity;

import jakarta.persistence.*;
<<<<<<< HEAD
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

=======
>>>>>>> 2a3b034 (algoritmanın temelini attım)
import java.time.LocalDateTime;

@Entity
@Table(name = "url_links")
<<<<<<< HEAD
@Data
@NoArgsConstructor
@AllArgsConstructor
=======
>>>>>>> 2a3b034 (algoritmanın temelini attım)
public class UrlLink {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "original_url", nullable = false, length = 2048)
    private String originalUrl;

<<<<<<< HEAD
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
=======
    @Column(name = "short_code", nullable = false, unique = true, length = 20)
    private String shortCode;

    @Column(length = 50)
    private String category;

    @Column(length = 1000)
    private String summary;

    @Column(name = "click_count")
    private int clickCount = 0;

    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public int getClickCount() {
        return clickCount;
    }

    public void setClickCount(int clickCount) {
        this.clickCount = clickCount;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getShortCode() {
        return shortCode;
    }

    public void setShortCode(String shortCode) {
        this.shortCode = shortCode;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }

    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
>>>>>>> 2a3b034 (algoritmanın temelini attım)
    }
}