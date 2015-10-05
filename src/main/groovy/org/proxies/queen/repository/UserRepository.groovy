package org.proxies.queen.repository

import org.proxies.queen.dao.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    UserEntity findByUsername(String username)

    UserEntity findByEmail(String email)
}