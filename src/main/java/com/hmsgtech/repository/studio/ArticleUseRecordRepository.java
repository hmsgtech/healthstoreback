package com.hmsgtech.repository.studio;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.studioArticle.ArticleUseRecord;

public interface ArticleUseRecordRepository extends CrudRepository<ArticleUseRecord, Integer> {
	// 查询使用历史列表
	@Query("SELECT a from ArticleUseRecord a where a.unionid=?1 and a.articleType in ?2")
	List<ArticleUseRecord> findByUnionidAndArticleType(String unionid, List<Integer> types, Pageable pageable);

	// 查询某个文章或模板的使用历史记录
	ArticleUseRecord findByUnionidAndArticleIdAndArticleType(String unionid, int id, int type);
	// List<ArticleUseRecord> findListByUnionidAndArticleIdAndArticleType(String
	// unionid, int id, int type);

}
