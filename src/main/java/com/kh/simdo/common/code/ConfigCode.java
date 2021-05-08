package com.kh.simdo.common.code;

public enum ConfigCode {

    DOMAIN("http://localhost:9196"),
    EMAIL("hhieathdd_@naver.com"),
    UPLOAD_PATH("C:\\CODE\\06_Spring\\resources\\upload\\");

    public String desc;

    private ConfigCode(String desc){
        this.desc = desc;
    }

    public String toString() {
        return desc;
    }
}
