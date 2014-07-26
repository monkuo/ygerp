/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.web;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.util.AutoPopulatingList;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.okbong.yg.common.CommonConstant;
import com.okbong.yg.common.ProcessPropertiesLoader;
import com.okbong.yg.entity.Delivery;
import com.okbong.yg.entity.DeliveryDetail;
import com.okbong.yg.entity.User;
import com.okbong.yg.entity.Vendor;
import com.okbong.yg.service.DeliveryDetailService;
import com.okbong.yg.service.DeliveryService;
import com.okbong.yg.service.VendorService;
import com.okbong.yg.service.account.ShiroDbRealm.ShiroUser;

/**
 * Delivery管理的Controller, 使用Restful風格的Urls:
 * 
 * List page : GET /delivery/
 * Create page : GET /delivery/create
 * Create action : POST /delivery/create
 * Update page : GET /delivery/update/{id}
 * Update action : POST /delivery/update
 * Delete action : GET /delivery/delete/{id}
 * 
 * @author calvin
 */
@Controller
@RequestMapping(value = "/delivery")
public class DeliveryController {
	private static Logger logger = LoggerFactory.getLogger(DeliveryController.class);
	@Autowired
	private DeliveryService deliveryService;
	@Autowired
	private DeliveryDetailService deliveryDetailService;
	@Autowired
	private VendorService vendorService;
	
	@InitBinder
	private void init(HttpSession session, final WebDataBinder binder) {
		if (session.getAttribute(CommonConstant.LIST_VENDOR) == null){
			session.setAttribute(CommonConstant.LIST_VENDOR, vendorService.findAllVendor()); 
		}
	}
	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");
		String key = "";
		String value = null;
		Calendar now = Calendar.getInstance();
		try{
		for(Entry<String, Object> entry : searchParams.entrySet()) {
			key = entry.getKey();
			value = (String)entry.getValue();
			if (!StringUtils.isBlank(value)){
				if (key.indexOf("deliveryDate")>-1 ){
						Date stdt = CommonConstant.DATEMONTH_FORMAT.parse(value);
						now.setTime(stdt);
						now.set(Calendar.DAY_OF_MONTH,1);
						searchParams.put("GTE_deliveryDate",now.getTime());
						now.set(Calendar.DAY_OF_MONTH,now.getActualMaximum(Calendar.DAY_OF_MONTH));
						searchParams.put("LTE_deliveryDate", now.getTime());
				}else if (key.indexOf("vendor")>-1 ){
					long id = Integer.parseInt(value);
					Vendor vendor = vendorService.getVendor(id);
					searchParams.put(key, vendor);
				}
			}
		}
		}catch(Exception e){
			e.printStackTrace();
		}
		List<Delivery> deliverys = deliveryService.findDelivery(searchParams);

		model.addAttribute("deliverys", deliverys);
		// 將搜索條件編碼成字串，用於排序，分頁的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "delivery/deliveryList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(@ModelAttribute Delivery delivery, Model model) {
		delivery.setDetails(new AutoPopulatingList<DeliveryDetail>(DeliveryDetail.class));
		model.addAttribute("action", "create");
		return "delivery/deliveryForm";
	}

	@Transactional
	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid @ModelAttribute Delivery newDelivery, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newDelivery.setVendor(vendorService.getVendor(newDelivery.getVendor().getId()));
		newDelivery.setCreateUser(user);
		newDelivery.setCreateDate(Calendar.getInstance().getTime());		
		
		deliveryService.saveDelivery(newDelivery);
		
		manageDeliveryDetails(newDelivery);
		deliveryDetailService.save(newDelivery.getDetails());
		
		redirectAttributes.addFlashAttribute("message", "建立出貨資料成功");
		return "redirect:/delivery";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("delivery", deliveryService.getDelivery(id));
		model.addAttribute("action", "update");
		return "delivery/deliveryForm";
	}

	@Transactional
	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("delivery") Delivery delivery, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		delivery.setVendor(vendorService.getVendor(delivery.getVendor().getId()));
		delivery.setModifyUser(user);
		delivery.setModifyDate(Calendar.getInstance().getTime());		
		
		deliveryService.saveDelivery(delivery);
		
		deliveryDetailService.delete(manageDeliveryDetails(delivery));
		deliveryDetailService.save(delivery.getDetails());
		
		redirectAttributes.addFlashAttribute("message", "更新出貨資料成功");
		return "redirect:/delivery/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		deliveryDetailService.deleteDeliveryDetailByMaster(id);
		deliveryService.deleteDelivery(id);
		redirectAttributes.addFlashAttribute("message", "刪除出貨資料成功");
		return "redirect:/delivery/";
	}

	/**
	 * 所有RequestMapping方法調用前的Model準備方法, 實現Struts2 Preparable二次部分綁定的效果,先根據form的id從資料庫查出Delivery物件,再把Form提交的內容綁定到該物件上。
	 * 因為僅update()方法的form中有id屬性，因此僅在update時實際執行.
	 */
	@ModelAttribute
	public void getDelivery(@RequestParam(value = "id", defaultValue = "-1") Long id, Model model) {
		if (id != -1) {
			model.addAttribute("delivery", deliveryService.getDelivery(id));
		}
	}

	/**
	 * 取出Shiro中的當前用戶Id.
	 */
	private Long getCurrentUserId() {
		ShiroUser user = (ShiroUser) SecurityUtils.getSubject().getPrincipal();
		return user.id;
	}
	
	private void export(List<Delivery> deliverys){
		Properties props = null;
		try {
			String date = "";
			props = ProcessPropertiesLoader.loadProperties(CommonConstant.PROPERTIES_COMMON);
			String fileName = String.format(props.getProperty(CommonConstant.EXPORT_XLS_DELIVERY,date));
			
		} catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
	}
	
	// Manage dynamically added or removed deliveryDetails
    private List<DeliveryDetail> manageDeliveryDetails(Delivery delivery) {
        List<DeliveryDetail> deliveryDetails2remove = new ArrayList<DeliveryDetail>();
        if (delivery.getDetails() != null) {
            for (Iterator<DeliveryDetail> i = delivery.getDetails().iterator(); i.hasNext();) {
                DeliveryDetail deliveryDetail = i.next();
                if (deliveryDetail.getRemove() == 1) {
                    deliveryDetails2remove.add(deliveryDetail);
                    i.remove();
                } else {
                    deliveryDetail.setDelivery(delivery);
                }
            }
        }
        return deliveryDetails2remove;
    }
}
