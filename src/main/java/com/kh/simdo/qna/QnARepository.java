package com.kh.simdo.qna;

import com.kh.simdo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA,String> {

    List<QnA> findQnAByUserAndIsDel(User user, boolean isDel);
}
