package com.project.code.Controller;

import com.project.code.Model.Customer;
import com.project.code.Model.Review;
import com.project.code.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @GetMapping("/{storeId}/{productId}")
    public Map<String, Object> getReviews(@PathVariable Long storeId, @PathVariable Long productId) {
        List<Review> rawReviews = reviewRepository.findByStoreIdAndProductId(storeId, productId);
        List<Map<String, Object>> processedReviews = new ArrayList<>();

        for (Review r : rawReviews) {
            Map<String, Object> reviewMap = new HashMap<>();
            reviewMap.put("comment", r.getComment());
            reviewMap.put("rating", r.getRating());

            Customer customer = customerRepository.findByid(r.getCustomerId());
            reviewMap.put("customerName", (customer != null) ? customer.getName() : "Bilinmiyor");
            
            processedReviews.add(reviewMap);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("reviews", processedReviews);
        return response;
    }
}