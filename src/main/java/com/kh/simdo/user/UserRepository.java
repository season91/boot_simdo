package com.kh.simdo.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserEmailAndIsLeave(String userEmail, boolean isLeave);
    boolean existsByUserEmail(String userEmail);

}
