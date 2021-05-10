package com.kh.simdo.mypage.fmsline;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FmslineRepository extends JpaRepository<Fmsline, String> {
}
