package com.kh.simdo.mypage.review;

import com.kh.simdo.movie.Movie;
import com.kh.simdo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByUserAndIsReviewDelOrderByReviewRegDateDesc(User user, boolean isReviewDel);
    Review findByReviewNo(String reviewNo);

    //아영 : 영화상세에 영화후기작성여부 표시를 위한
    Review findByUserAndMovieAndIsReviewDel(User user, Movie movie, boolean isReviewDel);
}
