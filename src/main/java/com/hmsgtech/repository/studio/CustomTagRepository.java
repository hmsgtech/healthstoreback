package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioCustom.CustomTag;

public interface CustomTagRepository extends CrudRepository<CustomTag, Integer> {
	List<CustomTag> findByUnionid(String unionid, Pageable pageable);

	/*查询标签是否存在*/
	Long countByUnionidAndName(String unionid, String name);
}
