package com.kh.simdo.common.util.http;

import com.kh.simdo.common.code.ErrorCode;
import com.kh.simdo.common.exception.ToAlertException;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

public class HttpUtils {

    // 서버가 서버에게 요청하는 통신이다.
    // fetch는 클라이언트가 서버에 요청하는 비동기통신이다.
    // HttpURLConnection : http, https 통신을 모두 지원
    // 준비물 : 시작줄(url, method), 헤더, 바디

    // 기능 메서드 분리
    // 0. get/post 통신 메서드
    // 1. urlEncoded 포맷팅
    // 2. HttpURLConnection 객체
    // 3. 헤더작성 -> map 형태로
    // 4. 바디작성
    // 5. 응답받기
    HttpURLConnection conn = null;
    // 0-0. get 방식, 헤더,바디 없는 통신
    public String get(String url) {
        String res = "";

        try {
            // 커넥션만들고 응답받기.
            setConnection(url, "GET");
            res = getResponse();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new ToAlertException(ErrorCode.CODE_500,e);
        }
        return res;

    }

    // 0-1. get 방식, 헤더있고 바디없는 통신
    public String get(String url, Map<String, String> headers) {
        String res = "";

        try {
            setConnection(url, "GET");
            setHeaders(headers);
            res = getResponse();
        } catch (IOException e) {
            throw new ToAlertException(ErrorCode.CODE_500,e);
        }
        return res;
    }

    // 0-3. post 방식, 헤더 바디 필요
    public String post(String url, String body, Map<String, String> headers) {
        String res = "";
        try {
            setConnection(url, "POST");
            setHeaders(headers);
            setBody(body);
            res = getResponse();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            throw new ToAlertException(ErrorCode.CODE_500,e);

        }

        return res;

    }

    // 1. urlEncoded 포맷팅
    // 외부에서 바디데이터 뿌릴 수 있도록 만든 인코디드 폼.
    // 파라미터 인코딩해준다.
    public String urlEncodedForm( Map<String, String> params) {
        String res ="";

        try {
            for(String key : params.keySet()) {
                res += "&" + URLEncoder.encode(key, "UTF-8") + "=" + URLEncoder.encode(params.get(key),"UTF-8");
            }
            System.out.println("서브스트링1 하기전 값 " + res);
            if(res.length() > 0) {
                res = res.substring(1);
                System.out.println("무슨서브스트링?"+res);
            }
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        System.out.println("출력값은????"+res);
        return res;
    }

    // 2. HttpURLConnection 객체
    public void setConnection(String url, String method) throws IOException {
        URL u = new URL(url);
        conn = (HttpURLConnection) u.openConnection();
        conn.setRequestMethod(method);

        // post 방식일 경우 doOutput 옵션 true로 지정해 출력스트림 사용여부 true
        if(method.equals("POST")) {
            conn.setDoOutput(true);
        }

        // conn 셋팅시 함께 해주면 좋은 코드!
        // 통신이 지연될 경우 통신을 종료할 시간 10초로 지정
        conn.setConnectTimeout(10000);
        conn.setReadTimeout(10000);
    }

    // 3. 헤더작성 -> map 형태로
    private void setHeaders(Map<String, String> headers) {
        for (String key : headers.keySet()) {
            // conn에 헤더정보 넣어주기!
            conn.addRequestProperty(key, headers.get(key));

        }
    }

    // 4. 바디작성
    private void setBody(String body) throws IOException {
        // output스트림 열고 conn의 내용을 가져와 출력한다.넣어준다.
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

        try {
            bw.write(body);
            bw.flush();
        } finally {
            bw.close();
        }

    }

    // 5. 응답받기
    private String getResponse() throws IOException {
        String res = "";
        BufferedReader br = null;

        int status = conn.getResponseCode();

        if(status >= 200 && status <300) {
            //맞다면 body정보를 inputStream으로 읽어 문자열에 저장!
            // http 통신이니까 대량의 데이터가 오고 갈 수 있는 환경이다. 최대한 신경써서 처리

            try {
                br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                // 내용ㅇ ㅣ많이 readlin으로  읽기 한계가 있음.
                String line;
                StringBuffer sb = new StringBuffer();
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
                res = sb.toString();

            } finally {
                br.close();
                // TODO: handle finally clause
            }


        } else {
            throw new ToAlertException(ErrorCode.CODE_500,new Exception(status + "가 입력되었습니다."));
        }
        return res;
    }
}
