package com.kh.simdo.mypage.fmsline;

import com.kh.simdo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FmslineRepository extends JpaRepository<Fmsline, String> {

    List<Fmsline> findByUserAndIsFmlDelOrderByFmlRegDateDesc(User user, boolean IsFmlDel);
    Fmsline findByFmslineNo(String fmslineNo);

}