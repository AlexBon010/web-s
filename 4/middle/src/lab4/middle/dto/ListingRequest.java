package lab4.middle.dto;

import jakarta.validation.constraints.NotBlank;

public class ListingRequest {

    @NotBlank
    private String title;

    private String description;

    @NotBlank
    private String price;

    @NotBlank
    private String city;

    private String photo1Base64;
    private String photo2Base64;
    private String photo3Base64;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public String getPrice() { return price; }
    public void setPrice(String price) { this.price = price; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getPhoto1Base64() { return photo1Base64; }
    public void setPhoto1Base64(String photo1Base64) { this.photo1Base64 = photo1Base64; }
    public String getPhoto2Base64() { return photo2Base64; }
    public void setPhoto2Base64(String photo2Base64) { this.photo2Base64 = photo2Base64; }
    public String getPhoto3Base64() { return photo3Base64; }
    public void setPhoto3Base64(String photo3Base64) { this.photo3Base64 = photo3Base64; }
}
