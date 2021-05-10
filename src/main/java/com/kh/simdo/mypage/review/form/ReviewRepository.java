package com.kh.simdo.mypage.review.form;

import com.kh.simdo.mypage.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, String> {
}
