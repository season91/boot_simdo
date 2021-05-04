package com.kh.simdo.common.util.file;

import com.kh.simdo.common.code.ConfigCode;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

public class FileUtil {

    public List<FileEntity> fileUpload(List<MultipartFile> files) throws IllegalStateException, IOException{
        // file 메타 정보를 가지고 반환될 list 선언
        List<FileEntity> fileDatas =  new ArrayList<FileEntity>();
        // 파일이 저장될 폴더 경로 생성
        String savePath = getSavePath();
        // 게시판에서 아무것도 안담겨있으면 빈파일로 올라가버린다.
        // 그래서 여기서 분기처리해줘야한다. foreach문 돌리기전에 foreach를 돌릴지 말지 검증
        // 안올려도 1이고 1개올려도 1이다. 1보다크면서 본파일명이 있는경우 포문 돌린다(빈파일은 오리진파일명이 null이다)

        if(files.size() >= 1 && !files.get(0).getOriginalFilename().equals("")) {
            for (MultipartFile multipartFile : files) {
                // 저장될 파일명 생성
                String renameFileName =  UUID.randomUUID().toString();

                // 원본 파일명
                String originFileName = multipartFile.getOriginalFilename();

                FileEntity fileEntity = new FileEntity();
                fileEntity.setOriginFileName(originFileName);
                fileEntity.setRenameFileName(renameFileName);
                fileEntity.setSavePath(savePath);

                fileDatas.add(fileEntity);

                saveFile(multipartFile, fileEntity);

            }
        }

        return fileDatas;
    }

    // 저장 경로
    private String getSavePath() {
        Calendar today = Calendar.getInstance();
        return today.get(Calendar.YEAR) + "/" + (today.get(Calendar.MONTH)+1) +"/" + today.get(Calendar.DAY_OF_MONTH) + "/";
    }

    // 파일 저장
    private void saveFile(MultipartFile multipartFile, FileEntity fileEntity) throws IllegalStateException, IOException {
        File file = new File(fileEntity.getFullPath() + fileEntity.getRenameFileName());
        if(!file.exists()) {
            new File(fileEntity.getFullPath()).mkdirs();
        }

        // 왜 던지냐면 빈으로 등록하지도 않았고, 스프링이 동작하는 서비스나 컨트롤러에서 받아 처리해야 트랜잭션처리 및 예외처리가 가능하다.
        multipartFile.transferTo(file);
    }

    // 파일 삭제
    public void deleteFile(String fullPath, String renameFileName) {
        // 기초 저장경로 + 날짜 저장경로 + 파일이름
        File file = new File(ConfigCode.UPLOAD_PATH + fullPath + renameFileName);
        file.delete();
    }
}
