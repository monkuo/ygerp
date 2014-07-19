/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;
import org.springside.modules.persistence.SearchFilter.Operator;

import com.okbong.yg.entity.Vendor;
import com.okbong.yg.repository.VendorDao;

// Spring Bean的標識.
@Component
// 類中所有public函數都納入事務管理的標識.
@Transactional
public class VendorService {

	private VendorDao taskDao;

	public Vendor getVendor(Long id) {
		return taskDao.findOne(id);
	}

	public void saveVendor(Vendor entity) {
		taskDao.save(entity);
	}

	public void deleteVendor(Long id) {
		taskDao.delete(id);
	}

	public List<Vendor> getAllVendor() {
		return (List<Vendor>) taskDao.findAll();
	}

	public Page<Vendor> getUserVendor(Long userId, Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {
		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<Vendor> spec = buildSpecification(userId, searchParams);

		return taskDao.findAll(spec, pageRequest);
	}

	/**
	 * 創建分頁請求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {
		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "id");
		} else if ("title".equals(sortType)) {
			sort = new Sort(Direction.ASC, "title");
		}

		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 創建動態查詢準則組合.
	 */
	private Specification<Vendor> buildSpecification(Long userId, Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<Vendor> spec = DynamicSpecifications.bySearchFilter(filters.values(), Vendor.class);
		return spec;
	}

	@Autowired
	public void setVendorDao(VendorDao taskDao) {
		this.taskDao = taskDao;
	}
}
