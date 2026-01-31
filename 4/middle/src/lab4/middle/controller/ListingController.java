package lab4.middle.controller;

import jakarta.validation.Valid;
import lab4.middle.dto.ListingRequest;
import lab4.middle.dto.ListingResponse;
import lab4.middle.service.ListingService;
import lab4.middle.service.UserService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/listings")
public class ListingController {

    private final ListingService listingService;
    private final UserService userService;

    public ListingController(ListingService listingService, UserService userService) {
        this.listingService = listingService;
        this.userService = userService;
    }

    @GetMapping
    public Page<ListingResponse> search(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) String q,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        return listingService.search(city, q, page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(listingService.getById(id));
    }

    @GetMapping("/my")
    public Page<ListingResponse> getMy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size) {
        Long sellerId = userService.getCurrentUser().getId();
        return listingService.getBySellerId(sellerId, page, size);
    }

    @PostMapping
    public ResponseEntity<ListingResponse> create(@Valid @RequestBody ListingRequest req) {
        ListingResponse created = listingService.create(req);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingResponse> update(@PathVariable Long id, @Valid @RequestBody ListingRequest req) {
        return ResponseEntity.ok(listingService.update(id, req));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        listingService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
