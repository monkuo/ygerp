/*******************************************************************************
 * Copyright (c)  2014 O.K Bong
 * Author : Benson Kuo
 *******************************************************************************/
package com.okbong.yg.web;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springside.modules.web.Servlets;

import com.okbong.yg.entity.Delivery;
import com.okbong.yg.entity.User;
import com.okbong.yg.service.DeliveryService;
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
	@Autowired
	private DeliveryService deliveryService;

	@RequestMapping(method = RequestMethod.GET)
	public String list(Model model, ServletRequest request) {
		Map<String, Object> searchParams = Servlets.getParametersStartingWith(request, "search_");

		List<Delivery> deliverys = deliveryService.findDelivery(searchParams);

		model.addAttribute("deliverys", deliverys);
		// 將搜索條件編碼成字串，用於排序，分頁的URL
		model.addAttribute("searchParams", Servlets.encodeParameterStringWithPrefix(searchParams, "search_"));

		return "delivery/deliveryList";
	}

	@RequestMapping(value = "create", method = RequestMethod.GET)
	public String createForm(Model model) {
		model.addAttribute("delivery", new Delivery());
		model.addAttribute("action", "create");
		return "delivery/deliveryForm";
	}

	@RequestMapping(value = "create", method = RequestMethod.POST)
	public String create(@Valid Delivery newDelivery, RedirectAttributes redirectAttributes) {
		User user = new User(getCurrentUserId());
		newDelivery.setCreateUser(user);

		deliveryService.saveDelivery(newDelivery);
		redirectAttributes.addFlashAttribute("message", "創建任務成功");
		return "redirect:/delivery/";
	}

	@RequestMapping(value = "update/{id}", method = RequestMethod.GET)
	public String updateForm(@PathVariable("id") Long id, Model model) {
		model.addAttribute("delivery", deliveryService.getDelivery(id));
		model.addAttribute("action", "update");
		return "delivery/deliveryForm";
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	public String update(@Valid @ModelAttribute("delivery") Delivery delivery, RedirectAttributes redirectAttributes) {
		deliveryService.saveDelivery(delivery);
		redirectAttributes.addFlashAttribute("message", "更新任務成功");
		return "redirect:/delivery/";
	}

	@RequestMapping(value = "delete/{id}")
	public String delete(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
		deliveryService.deleteDelivery(id);
		redirectAttributes.addFlashAttribute("message", "刪除任務成功");
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
}
