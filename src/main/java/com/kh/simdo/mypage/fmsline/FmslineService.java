package com.kh.simdo.mypage.fmsline;

import org.springframework.stereotype.Service;

@Service
public class FmslineService {

    private final FmslineRepository fmslineRepository;

    public FmslineService(FmslineRepository fmslineRepository) {
        this.fmslineRepository = fmslineRepository;
    }

    public void saveFmsline(Fmsline fmsline) {
        fmslineRepository.save(fmsline);
    }

}
