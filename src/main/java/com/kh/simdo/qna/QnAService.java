package com.kh.simdo.qna;

import com.kh.simdo.common.util.paging.Paging;
import com.kh.simdo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Transactional
public class QnAService {

    private final QnARepository qnARepository;

    public QnA insertQnA(QnA qna){
        return qnARepository.save(qna);
    }

    public Map<String, Object> qnaList(User user, boolean isDel, PageRequest page){
        Page<QnA> qnas = qnARepository.findQnAByUserAndIsDel(user, isDel, page);
        Paging paging = Paging.builder()
                .currentPage(page.getPageNumber()+1)
                .blockCnt(5)
                .cntPerPage(page.getPageSize())
                .type("qna")
                .total((int)qnARepository.count())
                .build();
        
        Map<String, Object> commandMap = new HashMap<>();
        commandMap.put("paging", paging);
        commandMap.put("qnaList", qnas.getContent());

        return commandMap;
    }

    public QnA findQnAByQnaNoAndIsDel(long QnaNo, boolean isDel){
        return qnARepository.findQnAByQnaNoAndIsDel(QnaNo, isDel);
    }

    // admin유저용 목록
    public Map<String, Object> qnaAllList(PageRequest page){
        Page<QnA> qnas = qnARepository.findAll(page);
        Paging paging = Paging.builder()
                .currentPage(page.getPageNumber()+1)
                .blockCnt(5)
                .cntPerPage(page.getPageSize())
                .type("qna")
                .total((int)qnARepository.count())
                .build();

        Map<String, Object> commandMap = new HashMap<>();
        commandMap.put("paging", paging);
        commandMap.put("qnaList", qnas.getContent());

        return commandMap;
    }

    // coment 추가
    public QnA updateQnA(QnA beforeQnA){
        QnA qna = findQnAByQnaNoAndIsDel(beforeQnA.getQnaNo(), false);
        qna.setQnaComent(beforeQnA.getQnaComent());
        return qnARepository.save(qna);
    }
}
