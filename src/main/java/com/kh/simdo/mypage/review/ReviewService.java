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
    public Review isReview(User user, Movie movie){
        return reviewRepository.findByUserAndMovieAndIsReviewDel(user, movie, false);
    }

    //아영: 영화상세에 후기내역 표시를 위한
    public List<Review> findReviewList(Movie movie, boolean isReviewDel){
        return reviewRepository.findByMovieAndIsReviewDelOrderByReviewRegDateDesc(movie, isReviewDel);
    }

    //아영: 리뷰 좋아요 +1 업데이트
    public Review reviewLike(String reviewNo){
        Review review = reviewRepository.findByReviewNo(reviewNo);
        review.setReviewLike(review.getReviewLike()+1);
        return reviewRepository.save(review);

    }

    //아영: 리뷰 싫어요 +1 업데이트
    public Review reviewHate(String reviewNo){
        Review review = reviewRepository.findByReviewNo(reviewNo);
        review.setReviewHate(review.getReviewHate()+1);
        return reviewRepository.save(review);

    }
}
