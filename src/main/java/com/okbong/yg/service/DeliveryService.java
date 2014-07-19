/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.okbong.yg.entity.Delivery;
import com.okbong.yg.repository.DeliveryDao;

// Spring Bean的標識.
@Component
// 類中所有public函數都納入事務管理的標識.
@Transactional
public class DeliveryService {

	private DeliveryDao deliveryDao;

	public Delivery getDelivery(Long id) {
		return deliveryDao.findOne(id);
	}

	public void saveDelivery(Delivery entity) {
		deliveryDao.save(entity);
	}

	public void deleteDelivery(Long id) {
		deliveryDao.delete(id);
	}

	public List<Delivery> findAllDelivery() {
		return (List<Delivery>) deliveryDao.findAll();
	}
	
	public List<Delivery> findDelivery(Map<String, Object> searchParams) {
		Specification<Delivery> spec = buildSpecification(searchParams);
		return deliveryDao.findAll(spec);
	}


	/**
	 * 創建動態查詢準則組合.
	 */
	private Specification<Delivery> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		Specification<Delivery> spec = DynamicSpecifications.bySearchFilter(filters.values(), Delivery.class);
		return spec;
	}

	@Autowired
	public void setDeliveryDao(DeliveryDao deliveryDao) {
		this.deliveryDao = deliveryDao;
	}
}
