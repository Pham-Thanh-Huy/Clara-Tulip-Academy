package com.huypt.user_service.security.jwt;

import com.huypt.user_service.models.User;
import org.springframework.data.repository.Repository;

interface UserRepository extends Repository<User, Long> {
    User getByUsername(String username);
}
