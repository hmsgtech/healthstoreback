package com.hmsgtech;

import java.util.Comparator;

import com.hmsgtech.domain.order.BuyOrder;

public class BuyderComparator  implements Comparator<BuyOrder>{

	@Override
	public int compare(BuyOrder o1, BuyOrder o2) {
		return o2.getCreateTime().compareTo(o1.getCreateTime());
	}

}
