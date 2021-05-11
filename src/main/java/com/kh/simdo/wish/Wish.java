package com.kh.simdo.wish;

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
@Table(name = "TB_WISH")
public class Wish {
    @Id
    @GeneratedValue
    private long wishNo;

    @Temporal(TemporalType.DATE)
    @Column(columnDefinition = "date default sysdate")
    private Date wishRegDate;

    private String mvNo;
    private long userNo;

    @Column(columnDefinition = "number default 0")
    private boolean isWishDel;

    @Override
    public String toString() {
        return "Wish{" +
                "wishNo='" + wishNo + '\'' +
                ", wishRegDate=" + wishRegDate +
                ", mvNo='" + mvNo + '\'' +
                ", userNo='" + userNo + '\'' +
                '}';
    }
}
