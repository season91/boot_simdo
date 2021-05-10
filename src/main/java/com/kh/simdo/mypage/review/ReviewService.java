package com.kh.simdo.mypage.review;

import com.kh.simdo.mypage.review.form.ReviewRepository;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    public final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

}
