package lab4.middle.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(nullable = false)
    private String passwordHash;

    @Column(name = "profile_photo_base64", columnDefinition = "TEXT")
    private String profilePhotoBase64;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Listing> listings = new ArrayList<>();

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getPasswordHash() { return passwordHash; }
    public void setPasswordHash(String passwordHash) { this.passwordHash = passwordHash; }
    public String getProfilePhotoBase64() { return profilePhotoBase64; }
    public void setProfilePhotoBase64(String profilePhotoBase64) { this.profilePhotoBase64 = profilePhotoBase64; }
    public List<Listing> getListings() { return listings; }
    public void setListings(List<Listing> listings) { this.listings = listings; }
}
