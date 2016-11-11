package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioArticle.ArticleTemplet;

public interface ArticleTempletRepository extends CrudRepository<ArticleTemplet, Integer> {
	List<ArticleTemplet> findByUnionidAndTypeAndStatus(String unionid, int type, int status);

}
