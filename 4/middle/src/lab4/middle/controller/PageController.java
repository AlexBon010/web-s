package lab4.middle.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String index() {
        return "search";
    }

    @GetMapping("/search")
    public String search() {
        return "search";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }

    @GetMapping("/my-items")
    public String myItems() {
        return "my-items";
    }

    @GetMapping("/listings/{id}")
    public String listingDetail() {
        return "listing-detail";
    }

    @GetMapping("/sellers/{id}")
    public String seller() {
        return "seller";
    }
}
