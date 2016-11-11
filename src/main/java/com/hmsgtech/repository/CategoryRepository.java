package com.hmsgtech.repository;

import com.hmsgtech.domain.Category;
import com.hmsgtech.domain.goods.Goods;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by 晓丰 on 2016/4/2.
 */
public interface CategoryRepository extends CrudRepository<Category, Long> {

}
