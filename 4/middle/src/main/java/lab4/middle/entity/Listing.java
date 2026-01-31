package lab4.middle.entity;

import jakarta.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "listings")
public class Listing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private String price;

    @Column(nullable = false)
    private String city;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id", nullable = false)
    private User seller;

    @Column(name = "photo1_base64", columnDefinition = "TEXT")
    private String photo1Base64;

    @Column(name = "photo2_base64", columnDefinition = "TEXT")
    private String photo2Base64;

    @Column(name = "photo3_base64", columnDefinition = "TEXT")
    private String photo3Base64;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt = Instant.now();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public User getSeller() { return seller; }
    public void setSeller(User seller) { this.seller = seller; }
    public String getPhoto1Base64() { return photo1Base64; }
    public void setPhoto1Base64(String photo1Base64) { this.photo1Base64 = photo1Base64; }
    public String getPhoto2Base64() { return photo2Base64; }
    public void setPhoto2Base64(String photo2Base64) { this.photo2Base64 = photo2Base64; }
    public String getPhoto3Base64() { return photo3Base64; }
    public void setPhoto3Base64(String photo3Base64) { this.photo3Base64 = photo3Base64; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
