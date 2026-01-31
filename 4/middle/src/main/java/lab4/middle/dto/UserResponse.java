package lab4.middle.dto;

import lab4.middle.entity.User;

public class UserResponse {

    private Long id;
    private String username;
    private String email;
    private String phone;
    private String profilePhotoBase64;

    public static UserResponse from(User u) {
        UserResponse r = new UserResponse();
        r.setId(u.getId());
        r.setUsername(u.getUsername());
        r.setEmail(u.getEmail());
        r.setPhone(u.getPhone());
        r.setProfilePhotoBase64(u.getProfilePhotoBase64());
        return r;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getProfilePhotoBase64() { return profilePhotoBase64; }
    public void setProfilePhotoBase64(String profilePhotoBase64) { this.profilePhotoBase64 = profilePhotoBase64; }
}
