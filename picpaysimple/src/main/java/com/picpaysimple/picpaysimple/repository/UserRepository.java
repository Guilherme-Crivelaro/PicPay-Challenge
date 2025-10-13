package com.picpaysimple.picpaysimple.repository;

import com.picpaysimple.picpaysimple.domain.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByDocuments ( String documents );

    Optional<User> findUserById ( Long id );
}
