package com.kh.simdo.qna;

import com.kh.simdo.user.User;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.ManyToAny;

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

    private String qnaType;
    private String qnaTitle;
    private String qnaContent;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default sysdate")
    private Date qnaRegDate;

    @Column(columnDefinition = "number default 0")
    private boolean isFile;

    @Override
    public String toString() {
        return "QnA{" +
                "qnaNo=" + qnaNo +
                ", user=" + user +
                ", qnaType='" + qnaType + '\'' +
                ", qnaTitle='" + qnaTitle + '\'' +
                ", qnaContent='" + qnaContent + '\'' +
                ", qnaRegDate=" + qnaRegDate +
                ", isFile=" + isFile +
                '}';
    }
}
