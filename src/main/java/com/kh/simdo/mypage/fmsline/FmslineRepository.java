package com.kh.simdo.mypage.fmsline;

import com.kh.simdo.movie.Movie;
import com.kh.simdo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FmslineRepository extends JpaRepository<Fmsline, String> {

    List<Fmsline> findByUserAndIsFmlDelOrderByFmlRegDateDesc(User user, boolean IsFmlDel);
    Fmsline findByFmslineNo(String fmslineNo);

    //아영: 영화상세에 들어갈 명대사 내역
    List<Fmsline> findByMovieAndIsFmlDel(Movie movie, boolean isFmlDel);
}
