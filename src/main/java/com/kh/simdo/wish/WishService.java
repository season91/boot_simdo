package com.kh.simdo.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishService {

    private final WishRepository wishRepository;

    //찜하기
    public Wish insertWish(String mvNo, long userNo){
        Wish wish = new Wish();
        wish.setMvNo(mvNo);
        wish.setUserNo(userNo);
        return wishRepository.save(wish);
    }

    //찜해제하기

}
