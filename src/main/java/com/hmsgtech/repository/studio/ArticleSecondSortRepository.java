package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioArticle.ArticleSecondSort;

public interface ArticleSecondSortRepository extends CrudRepository<ArticleSecondSort, Integer> {
	List<ArticleSecondSort> findByFirstSort(int first);
}
