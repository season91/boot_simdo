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

    public Fmsline findByFmslineNo(String fmslineNo) {
        return fmslineRepository.findByFmslineNo(fmslineNo);
    }

    public void deleteFmsline(String fmslineNo) {
        Fmsline fmsline = fmslineRepository.findByFmslineNo(fmslineNo);
        fmsline.setFmlDel(true);

        fmslineRepository.save(fmsline);
    }
}
