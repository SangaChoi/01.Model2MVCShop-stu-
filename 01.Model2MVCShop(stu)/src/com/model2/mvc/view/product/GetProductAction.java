package com.model2.mvc.view.product;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.framework.Action;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;

public class GetProductAction extends Action{

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int prodNo=Integer.parseInt(request.getParameter("prodNo"));
		String prodNo2=request.getParameter("prodNo");
		
		
		Cookie[] cookies = request.getCookies();
		if(cookies!=null && cookies.length>0) {
			System.out.println("기존 Cookie 이용");
		  for(int i=0;i<cookies.length;i++) {	
			  Cookie cookie = cookies[i];
			if(cookie.getName().equals("history")) {
				cookie.setValue(cookie.getValue()+","+prodNo);
				cookie.setMaxAge(60*60);
				response.addCookie(cookie);
			}else{
			System.out.println("Cookie 첫 생성");
			cookie = new Cookie("history",prodNo2);
			cookie.setMaxAge(60*60);
			response.addCookie(cookie);
			}
		  }
		}
			
		
		ProductService service=new ProductServiceImpl();
		ProductVO vo=service.getProduct(prodNo);
		
		request.setAttribute("vo", vo);
		
		return "forward:/product/getProduct.jsp";
	}

}
