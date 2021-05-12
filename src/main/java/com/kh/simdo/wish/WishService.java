package com.kh.simdo.wish;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class WishService {

    private final WishRepository wishRepository;

    //찜한내역 검색
    public Wish findByUserNoAndMvNo(Wish wish){
        return wishRepository.findByUserNoAndMvNo(wish.getUserNo(), wish.getMvNo());
    }

    //찜하기, 찜해제하기
    public Wish saveWish(Wish wish){
        // 매개변수로 넘어온 찜이 내역에 없다면 정상추가해야하고, 내역이 있다면 넘어온 isDel값으로 업데이트 해준다.
        Wish findWish = findByUserNoAndMvNo(wish);
        System.out.println(findWish);
        if(findWish == null){
            //내역이 없다면 새로 추가.
            return wishRepository.save(wish);

        } else if (findWish.isWishDel()) {
            //내역이 있다고 isDel이 true라면 false로 업데이트
            findWish.setWishDel(false);
            return wishRepository.save(findWish);
        } else {
            // 내역이 있고 찾은내역이 false라면 true로 업데이트
            findWish.setWishDel(true);
            return wishRepository.save(findWish);
        }
    }

    //영화 상세에 보여줄 찜내역 확인
    public Wish findByUserNoAndMvNoAndIsWishDel(long userNo, String mvNo, boolean isWishDel){
        return wishRepository.findByUserNoAndMvNoAndIsWishDel(userNo, mvNo, false);
    }
}
