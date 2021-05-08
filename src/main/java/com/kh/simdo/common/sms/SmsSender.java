package com.kh.simdo.common.sms;

import org.apache.tomcat.util.codec.binary.Base64;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Component
public class SmsSender {

    @Autowired
    private RestTemplate http;

    public int authenticateTel(String userTel, HttpSession session) {
        String method = "POST";
        String url = "/sms/v2/services//messages";
        String timestamp = Long.toString(System.currentTimeMillis());
        String accessKey = "";
        String secretKey = "";

        String signature = makeSignature(url, timestamp, method, accessKey, secretKey);
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Type", "application/json; charset=UTF-8");
        header.add("x-ncp-apigw-timestamp", timestamp);
        header.add("x-ncp-iam-access-key", accessKey);
        header.add("x-ncp-apigw-signature-v2", signature);

        String certNum = makeCertNum();
        session.setAttribute("certNum", certNum);

        JSONObject params = new JSONObject();
        JSONObject params2 = new JSONObject();
        JSONArray messages = new JSONArray();
        try {
            params.put("type", "SMS");
            params.put("from", "");
            params.put("content", "[SIMDO:wm] 본인 확인을 위해 인증번호 [" + certNum + "]를 입력해주세요.");
            params2.put("to", userTel);
            messages.put(params2);
            params.put("messages", messages);
            String body = params.toString();

            RequestEntity<String> request =
                    RequestEntity
                            .post("https://sens.apigw.ntruss.com/sms/v2/services//messages")
                            .headers(header)
                            .body(body);

            ResponseEntity<String> response = http.exchange(request, String.class);
            return response.getStatusCodeValue();

        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return 400;
    }

    //인증번호 생성 메서드
    public String makeCertNum() {
        Random random = new Random();
        String certNum = "";

        for(int i = 0; i < 6; i++) {
            String rand = Integer.toString(random.nextInt(10));

            if(!certNum.contains(rand)) {
                certNum += rand;
            }else {
                i -= 1;
            }
        }

        return certNum;
    }

    public String makeSignature(String url, String timestamp, String method, String accessKey, String secretKey) {
        String space = " ";
        String newLine = "\n";

        String message = new StringBuilder().append(method).append(space)
                .append(url).append(newLine).append(timestamp).append(newLine)
                .append(accessKey).toString();

        SecretKeySpec signingKey;
        String encodeBase64String = "";

        try {
            signingKey = new SecretKeySpec(secretKey.getBytes("UTF-8"), "HmacSHA256");
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(signingKey);
            byte[] rawHmac = mac.doFinal(message.getBytes("UTF-8"));
            encodeBase64String = Base64.encodeBase64String(rawHmac);
        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return encodeBase64String;

    }

}
