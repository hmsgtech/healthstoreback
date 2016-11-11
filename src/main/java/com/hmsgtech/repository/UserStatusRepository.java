package com.hmsgtech.repository;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.user.UserStatus;

public interface UserStatusRepository extends CrudRepository<UserStatus, String> {
}
