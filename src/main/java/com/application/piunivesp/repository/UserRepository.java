package com.application.piunivesp.repository;

import com.application.piunivesp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    Long countByEmailIgnoreCaseAndIdNotLike(String email, long l);

}
