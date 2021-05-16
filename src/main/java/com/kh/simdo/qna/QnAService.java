package com.kh.simdo.qna;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QnAService {

    private final QnARepository qnARepository;

    public QnA insertQnA(QnA qna){
        return qnARepository.save(qna);
    }
}
