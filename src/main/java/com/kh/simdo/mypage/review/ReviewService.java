package com.kh.simdo.mypage.review;

import com.kh.simdo.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewService {

    public final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> findByUserAndIsReviewDelOrderByReviewRegDateDesc(User user) {
        return reviewRepository.findByUserAndIsReviewDelOrderByReviewRegDateDesc(user, false);
    }

    public void deleteReview(String reviewNo) {
        Review review = reviewRepository.findByReviewNo(reviewNo);
        review.setReviewDel(true);

        reviewRepository.save(review);
    }
}
