package com.kh.simdo.common.code;

public enum ErrorCode {

    FILE01("파일 업로드 중 에러가 발생하였습니다."),
    CODE_500("서버에서 에러가 발생하였습니다."),
    NONE_EXIST_ARTICLE("찾으시는 게시물이 존재하지 않습니다");

    private String errMsg;

    private String url = "/index";

    ErrorCode(String errMsg){
        this.errMsg = errMsg;
    }

    ErrorCode(String errMsg, String url) {
        this.errMsg = errMsg;
        this.url = url;
    }

    public String errMsg() {
        return errMsg;
    }

    public String url() {
        return url;
    }
}
