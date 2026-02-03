package com.project.code.Repo;
import com.kursaddcinar.model.Review;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ReviewRepository extends MongoRepository<Review, String> {

    /**
     * Belirli bir ürün ve mağaza için yapılan tüm incelemeleri getirir.
     * MongoDB'de findBy kuralı otomatik olarak alan eşleştirmesi yapar.
     */
    List<Review> findByStoreIdAndProductId(Long storeId, Long productId);
}