package com.kh.simdo.mypage.fmsline;

import com.kh.simdo.user.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FmslineService {

    private final FmslineRepository fmslineRepository;

    public FmslineService(FmslineRepository fmslineRepository) {
        this.fmslineRepository = fmslineRepository;
    }

    public void saveFmsline(Fmsline fmsline) {
        fmslineRepository.save(fmsline);
    }

    public List<Fmsline> findByUserAndIsFmlDelOrderByFmlRegDateDesc(User user) {
        return fmslineRepository.findByUserAndIsFmlDelOrderByFmlRegDateDesc(user, false);
    }
}
