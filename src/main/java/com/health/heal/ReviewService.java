package com.health.heal;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private ReviewRepository reviewRepository;
    
    // 기본 생성자
    public ReviewService() {}
    
    // Setter 주입
    public void setReviewRepository(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }
    
    public void saveReview(Review review) {
        if (reviewRepository != null) {
            reviewRepository.save(review);
        } else {
            System.out.println("ReviewRepository가 주입되지 않았습니다.");
        }
    }
    
    public List<Review> getAllReviews() {
        if (reviewRepository != null) {
            return reviewRepository.findAll();
        }
        return new java.util.ArrayList<Review>();
    }
    
    public Review getReview(Long id) {
        if (reviewRepository != null) {
            return reviewRepository.findById(id);
        }
        return null;
    }
    
    public void deleteReview(Long id) {
        if (reviewRepository != null) {
            reviewRepository.deleteById(id);
        }
    }
    
    // checkPassword 메서드 제거됨 (로그인 기반으로 변경)
}