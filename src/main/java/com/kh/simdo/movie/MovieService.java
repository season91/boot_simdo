package com.kh.simdo.movie;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor //생성자 생성, 의존성 주입 편의성을 위해 사용. 대신 setter가 없는 메서드 강제로 생성되어, 필드값이 변경될 수 있다.
@Transactional
public class MovieService {

    private final MovieRepository movieRepository;
    private final RestTemplate http;

}
