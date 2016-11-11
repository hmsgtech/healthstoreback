package com.hmsgtech.repository;

import com.hmsgtech.domain.FrontPage;
import com.hmsgtech.domain.GoodsCategoryRelationship;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 晓丰 on 2016/4/2.
 */
public interface GoodsCategoryRepository extends CrudRepository<GoodsCategoryRelationship, Long> {
	
    public List<GoodsCategoryRelationship> findGoodsCategoryRelationshipsByCategoryId(long catelog);
    
    public List<GoodsCategoryRelationship> findGoodsCategoryRelationshipsByGoodsId(long goodsId);
}
