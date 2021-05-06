package com.kh.simdo.movie;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MovieRepository extends JpaRepository<Movie, String> {

    // 영화 나라별 조회 List
    List<Movie> findMovieByNationContains(String nation);

    // 영화 장르별 조회 List
    List<Movie> findMovieByGenreContains(String genre);

    // 영화 제목으로 조회 List
    List<Movie> findMovieByMvTitleContains(String title);

    // 영화 번호로 조회 1개
    Movie findMovieByMvNo(String mvNo);
}
