package com.model2.mvc.view.purchase;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.impl.PurchaseServiceImpl;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class AddPurchaseAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		
		ProductVO product=new ProductVO();
		ProductService productService=new ProductServiceImpl();
		product=productService.getProduct(prodNo);
		request.setAttribute("product", product);
		
		String userId=request.getParameter("buyerId");
		
		UserVO user=new UserVO();
		UserService userUservice=new UserServiceImpl();
		user=userUservice.getUser(userId);
		request.setAttribute("user", user);
				
		PurchaseVO purchase=new PurchaseVO();
		purchase.setBuyer(user);
		purchase.setDivyAddr(user.getAddr());		
		purchase.setDivyDate(request.getParameter("receiverDate"));
		purchase.setDivyRequest(request.getParameter("receiverRequest"));
		purchase.setPaymentOption(request.getParameter("paymentOption"));
		purchase.setPurchaseProd(product);
		purchase.setReceiverName(user.getUserName());
		purchase.setReceiverPhone(user.getPhone());
		purchase.setTranCode("");
		
		PurchaseService purchaseService=new PurchaseServiceImpl();
		purchaseService.addPurchase(purchase);
		request.setAttribute("purchase", purchase);
		
		return "forward:/purchase/addPurchase.jsp";
	}

}
