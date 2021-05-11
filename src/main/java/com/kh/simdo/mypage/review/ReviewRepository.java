package com.kh.simdo.mypage.review;

import com.kh.simdo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {

    List<Review> findByUserAndIsReviewDelOrderByReviewRegDateDesc(User user, boolean isReviewDel);

}
