package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.user.Recipient;

public interface RecipientRepository extends CrudRepository<Recipient, Long> {

	List<Recipient> findByUserid(String userid);

}
