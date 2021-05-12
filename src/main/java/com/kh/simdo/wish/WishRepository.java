package com.kh.simdo.wish;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WishRepository extends JpaRepository<Wish, String> {

    //영화번호와 유저번호로 찜내역이 있는지 확인
    Wish findByUserNoAndMvNo(long userNo, String mvNo);

    //영화상세에 보여줄 찜내역 확인
    Wish findByUserNoAndMvNoAndIsWishDel(long userNo, String mvNo, boolean isWishDel);
}
