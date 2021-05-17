package com.kh.simdo.qna;

import com.kh.simdo.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QnARepository extends JpaRepository<QnA,String> {

    Page<QnA> findQnAByUserAndIsDel(User user, boolean isDel, Pageable page);

    QnA findQnAByQnaNoAndIsDel(long QnaNo, boolean isDel);
}
