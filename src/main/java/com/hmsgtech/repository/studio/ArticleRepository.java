package com.hmsgtech.repository.studio;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioArticle.Article;

public interface ArticleRepository extends CrudRepository<Article, Integer>, JpaSpecificationExecutor<Article> {

}
