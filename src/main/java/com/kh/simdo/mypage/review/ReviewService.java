package com.kh.simdo.mypage.review;

import com.kh.simdo.movie.Movie;
import com.kh.simdo.user.User;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReviewService {

    public final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Transactional
    public void saveReview(Review review) {
        reviewRepository.save(review);
    }

    public List<Review> findByUserAndIsReviewDelOrderByReviewRegDateDesc(User user) {
        return reviewRepository.findByUserAndIsReviewDelOrderByReviewRegDateDesc(user, false);
    }

    public Review findByReviewNo(String reviewNo) {
        return reviewRepository.findByReviewNo(reviewNo);
    }

    @Transactional
    public void deleteReview(String reviewNo) {
        Review review = reviewRepository.findByReviewNo(reviewNo);
        review.setReviewDel(true);

        reviewRepository.save(review);
    }

    //아영 : 영화상세에 후기작성 여부 표시
    public Review findByUserAndMovieAndIsReviewDel(User user, Movie movie){
        return reviewRepository.findByUserAndMovieAndIsReviewDel(user, movie, false);
    }

    //아영: 영화상세에 후기내역 표시를 위한
    public List<Review> findByIsReviewDelOrderByReviewRegDateDesc(boolean isReviewDel){
        return reviewRepository.findByIsReviewDelOrderByReviewRegDateDesc(isReviewDel);
    }
}
