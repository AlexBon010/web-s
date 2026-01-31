package lab4.middle.repository;

import lab4.middle.entity.Listing;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface ListingRepository extends JpaRepository<Listing, Long> {

    Page<Listing> findByCity(String city, Pageable pageable);

    Page<Listing> findBySeller_Id(Long sellerId, Pageable pageable);

    @Query("SELECT l FROM Listing l WHERE (:city IS NULL OR l.city = :city) AND (:q IS NULL OR :q = '' OR LOWER(l.title) LIKE LOWER(CONCAT('%', :q, '%')) OR LOWER(l.description) LIKE LOWER(CONCAT('%', :q, '%')))")
    Page<Listing> search(@Param("city") String city, @Param("q") String keyword, Pageable pageable);
}
