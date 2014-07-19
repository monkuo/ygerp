/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.okbong.yg.entity.Vendor;

public interface VendorDao extends PagingAndSortingRepository<Vendor, Long>, JpaSpecificationExecutor<Vendor> {

}
