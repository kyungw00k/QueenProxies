package org.proxies.queen.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import org.proxies.queen.entity.UserEntity

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
}