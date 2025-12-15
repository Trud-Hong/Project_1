package com.health.heal;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

@Repository
public class ReviewRepository {
    private Map<Long, Review> reviews = new HashMap<Long, Review>();
    private Long currentId = 1L;
    
    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(currentId++);
        }
        reviews.put(review.getId(), review);
        return review;
    }
    
    public List<Review> findAll() {
        return new ArrayList<Review>(reviews.values());
    }
    
    public Review findById(Long id) {
        return reviews.get(id);  // Optional 없이 바로 Review 반환
    }
    
    public void deleteById(Long id) {
        reviews.remove(id);
    }
    
    public boolean existsById(Long id) {
        return reviews.containsKey(id);
    }
}