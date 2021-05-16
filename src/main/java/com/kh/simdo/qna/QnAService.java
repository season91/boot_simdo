package com.kh.simdo.qna;

import com.kh.simdo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class QnAService {

    private final QnARepository qnARepository;

    public QnA insertQnA(QnA qna){
        return qnARepository.save(qna);
    }

    public List<QnA> findQnAByUserAndIsDel(User user, boolean isDel){
        return qnARepository.findQnAByUserAndIsDel(user, isDel);
    }

}
