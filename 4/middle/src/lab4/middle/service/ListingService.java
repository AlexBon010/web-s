package lab4.middle.service;

import lab4.middle.dto.ListingRequest;
import lab4.middle.dto.ListingResponse;
import lab4.middle.entity.Listing;
import lab4.middle.entity.User;
import lab4.middle.repository.ListingRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ListingService {

    private final ListingRepository listingRepository;
    private final UserService userService;

    public ListingService(ListingRepository listingRepository, UserService userService) {
        this.listingRepository = listingRepository;
        this.userService = userService;
    }

    @Transactional(readOnly = true)
    public Page<ListingResponse> search(String city, String q, int page, int size) {
        Pageable p = PageRequest.of(page, size);
        Page<Listing> list = listingRepository.search(
                (city == null || city.isBlank()) ? null : city.trim(),
                (q == null || q.isBlank()) ? null : q.trim(),
                p);
        return list.map(ListingResponse::from);
    }

    @Transactional(readOnly = true)
    public ListingResponse getById(Long id) {
        Listing l = listingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Объявление не найдено"));
        return ListingResponse.from(l);
    }

    @Transactional(readOnly = true)
    public Page<ListingResponse> getBySellerId(Long sellerId, int page, int size) {
        User seller = userService.getCurrentUser();
        if (!seller.getId().equals(sellerId)) {
            throw new IllegalArgumentException("Доступ запрещён");
        }
        Pageable p = PageRequest.of(page, size);
        return listingRepository.findBySeller_Id(sellerId, p).map(ListingResponse::from);
    }

    @Transactional
    public ListingResponse create(ListingRequest req) {
        User seller = userService.getCurrentUser();
        Listing l = new Listing();
        l.setTitle(req.getTitle().trim());
        l.setDescription(req.getDescription() != null ? req.getDescription().trim() : null);
        l.setPrice(req.getPrice().trim());
        l.setCity(req.getCity().trim());
        l.setSeller(seller);
        l.setPhoto1Base64(req.getPhoto1Base64());
        l.setPhoto2Base64(req.getPhoto2Base64());
        l.setPhoto3Base64(req.getPhoto3Base64());
        l = listingRepository.save(l);
        return ListingResponse.from(l);
    }

    @Transactional
    public ListingResponse update(Long id, ListingRequest req) {
        Listing l = listingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Объявление не найдено"));
        if (!l.getSeller().getId().equals(userService.getCurrentUser().getId())) {
            throw new IllegalArgumentException("Доступ запрещён");
        }
        l.setTitle(req.getTitle().trim());
        l.setDescription(req.getDescription() != null ? req.getDescription().trim() : null);
        l.setPrice(req.getPrice().trim());
        l.setCity(req.getCity().trim());
        if (req.getPhoto1Base64() != null) l.setPhoto1Base64(req.getPhoto1Base64());
        if (req.getPhoto2Base64() != null) l.setPhoto2Base64(req.getPhoto2Base64());
        if (req.getPhoto3Base64() != null) l.setPhoto3Base64(req.getPhoto3Base64());
        l = listingRepository.save(l);
        return ListingResponse.from(l);
    }

    @Transactional
    public void delete(Long id) {
        Listing l = listingRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Объявление не найдено"));
        if (!l.getSeller().getId().equals(userService.getCurrentUser().getId())) {
            throw new IllegalArgumentException("Доступ запрещён");
        }
        listingRepository.delete(l);
    }
}
