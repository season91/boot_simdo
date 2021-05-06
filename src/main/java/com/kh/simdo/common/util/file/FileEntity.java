package com.kh.simdo.common.util.file;

import com.kh.simdo.common.code.ConfigCode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@Entity
@DynamicInsert
@DynamicUpdate
@Table(name = "TB_FILE")
public class FileEntity {

    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @GeneratedValue(generator = "uuid")
    private String fileIdx;
    private String originFileName;
    private String renameFileName;
    private String savePath;
    @Column(columnDefinition = "date default sysdate")
    @Temporal(TemporalType.DATE)
    private Date regDate;
    @Column(columnDefinition = "number default 0")
    private boolean isDel;

    // 저장경로랑헷갈려서 풀 경로따로 확인하기 위한 확인용메서드
    public String getFullPath() {
        return ConfigCode.UPLOAD_PATH + savePath;
    }

    public String getFileIdx() {
        return fileIdx;
    }

    public void setFileIdx(String fileIdx) {
        this.fileIdx = fileIdx;
    }


    public String getOriginFileName() {
        return originFileName;
    }

    public void setOriginFileName(String originFileName) {
        this.originFileName = originFileName;
    }

    public String getRenameFileName() {
        return renameFileName;
    }

    public void setRenameFileName(String renameFileName) {
        this.renameFileName = renameFileName;
    }

    public String getSavePath() {
        return savePath;
    }

    public void setSavePath(String savePath) {
        this.savePath = savePath;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    public boolean getIsDel() {
        return isDel;
    }

    public void setIsDel(boolean isDel) {
        this.isDel = isDel;
    }
}
