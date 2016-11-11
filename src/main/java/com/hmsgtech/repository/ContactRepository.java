package com.hmsgtech.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.Category;
import com.hmsgtech.domain.user.Contact;

/**
 * Created by dengyanqing on 2016-6-12.
 */
public interface ContactRepository extends CrudRepository<Contact, Long> {

	@Query("SELECT c from Contact c WHERE c.userid = ?1")
	List<Contact> findContactListByUserid(String userid);

	List<Contact> findByUseridAndCardNumberNotNull(String userid);
	
}
