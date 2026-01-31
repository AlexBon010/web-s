package lab4.middle.dto;

import lab4.middle.entity.Listing;

public class ListingResponse {

    private Long id;
    private String title;
    private String description;
    private String price;
    private String city;
    private Long sellerId;
    private String sellerUsername;
    private String photo1Base64;
    private String photo2Base64;
    private String photo3Base64;
    private String createdAt;

    public static ListingResponse from(Listing l) {
        ListingResponse r = new ListingResponse();
        r.setId(l.getId());
        r.setTitle(l.getTitle());
        r.setDescription(l.getDescription());
        r.setPrice(l.getPrice());
        r.setCity(l.getCity());
        r.setSellerId(l.getSeller().getId());
        r.setSellerUsername(l.getSeller().getUsername());
        r.setPhoto1Base64(l.getPhoto1Base64());
        r.setPhoto2Base64(l.getPhoto2Base64());
        r.setPhoto3Base64(l.getPhoto3Base64());
        r.setCreatedAt(l.getCreatedAt() != null ? l.getCreatedAt().toString() : null);
        return r;
    }

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
    public Long getSellerId() { return sellerId; }
    public void setSellerId(Long sellerId) { this.sellerId = sellerId; }
    public String getSellerUsername() { return sellerUsername; }
    public void setSellerUsername(String sellerUsername) { this.sellerUsername = sellerUsername; }
    public String getPhoto1Base64() { return photo1Base64; }
    public void setPhoto1Base64(String photo1Base64) { this.photo1Base64 = photo1Base64; }
    public String getPhoto2Base64() { return photo2Base64; }
    public void setPhoto2Base64(String photo2Base64) { this.photo2Base64 = photo2Base64; }
    public String getPhoto3Base64() { return photo3Base64; }
    public void setPhoto3Base64(String photo3Base64) { this.photo3Base64 = photo3Base64; }
    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }
}
