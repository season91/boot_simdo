package com.kh.simdo.common.util.encoding;

import java.io.UnsupportedEncodingException;

public class EncodingUtil {

    // 경로에 파일 저장할떄 파일 이름이 꺠지기때문에 인코딩하는 클래스를 따로 만들어주자
    public void checkEncoding(String encode) {
        String [] charSet = {"utf-8","euc-kr","ksc5601","iso-8859-1","x-windows-949"};
        for (int i=0; i<charSet.length; i++) {
            for (int j=0; j<charSet.length; j++) {
                try {
                    System.out.println("[" + charSet[i] +"," + charSet[j] +"] = "
                            + new String(encode.getBytes(charSet[i]), charSet[j]));

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
