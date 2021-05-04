package com.kh.simdo.users;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends JpaRepository<Users, String> {

    Users findByUserEmailAndIsLeave(String userEmail, boolean isLeave);

}
