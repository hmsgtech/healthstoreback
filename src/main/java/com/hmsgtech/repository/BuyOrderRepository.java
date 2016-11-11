package com.hmsgtech.repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.hmsgtech.domain.order.BuyOrder;

public interface BuyOrderRepository extends CrudRepository<BuyOrder, Long> {
	List<BuyOrder> findBuyOrderByStatusAndUserid(int status, String userid);

	@Query("SELECT b from BuyOrder b where b.status in(?1,?2) and userid = ?3")
	List<BuyOrder> findBuyOrderByStatusAndUserid(int status1, int status2, String userid);

	@Query("SELECT b from BuyOrder b where userid = ?1 and b.status in ?2")
	List<BuyOrder> findByUseridAndStatusIn(String userid, Collection<Integer> status);
	
	List<BuyOrder> findByUserid(String userid);

	List<BuyOrder> findBuyOrderByUseridAndReportStatus(String userid, int status);

	Long countByUseridAndReportStatus(String userid, int status);

	List<BuyOrder> findBuyOrderByUseridAndReportStatusNotOrderByReportTimeDesc(String userid, int status);

	List<BuyOrder> findBuyOrderByUseridAndReportStatusNot(String userid, int status);

	@Modifying
	@Transactional
	@Query("update BuyOrder b set b.status=?1  where b.id = ?2")
	void updateBuyOrderById(int status, long orderId);
	
	@Modifying
	@Transactional
	@Query("update BuyOrder b set b.activation_code=?2 , b.status=?3 where b.id = ?1")
	void updateBuyOrderById(long orderId,String code,int status);

	@Modifying
	@Transactional
	@Query("delete from BuyOrder where createTime<?1 and status=?2 and userid=?3")
	void deleteExpireOrdersBycreateTime(Date date, int status, String userid);

	@Modifying
	@Transactional
	@Query("update BuyOrder b set b.coupon = ?1 where b.id = ?2")
	void updateCouponOrdersById(int coupon, long id);

}
