package com.kh.simdo.qna;

import com.kh.simdo.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_QNA")
public class QnA {

    @Id
    @GeneratedValue
    private long qnaNo;

    @ManyToOne
    private User user;

    private String qnaTitle;
    private String qnaContent;
    private String qnaComent;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default sysdate")
    private Date qnaRegDate;

    @Column(columnDefinition = "number default 0")
    private boolean isFile;

    @Column(columnDefinition = "number default 0")
    private boolean isDel;


    @Override
    public String toString() {
        return "QnA{" +
                "qnaNo=" + qnaNo +
                ", user=" + user +
                ", qnaTitle='" + qnaTitle + '\'' +
                ", qnaContent='" + qnaContent + '\'' +
                ", qnaComent='" + qnaComent + '\'' +
                ", qnaRegDate=" + qnaRegDate +
                ", isFile=" + isFile +
                ", isDel=" + isDel +
                '}';
    }
}
