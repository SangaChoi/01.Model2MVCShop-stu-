package com.model2.mvc.framework;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.model2.mvc.common.util.HttpUtil;


public class ActionServlet extends HttpServlet {
	
	private RequestMapping mapper;

	@Override
	public void init() throws ServletException {
		super.init();
		System.out.println("액션서블릿 init() 시작");
		String resources=getServletConfig().getInitParameter("resources");
		mapper=RequestMapping.getInstance(resources);
		System.out.println("액션서블릿 init() 완료");
	}

	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) 
																									throws ServletException, IOException {
		
		System.out.println("-----------------액션서블릿 service() 시작");
		
		String url = request.getRequestURI();
		System.out.println("액션서블릿 url : "+url);
		String contextPath = request.getContextPath();
		System.out.println("액션서블릿 contextPath : "+contextPath);
		String path = url.substring(contextPath.length());
		System.out.println("액션서블릿 path : "+path);
		
		try{
			Action action = mapper.getAction(path);
//////////////getAction
			action.setServletContext(getServletContext());
			System.out.println("액션서블릿 setServletContext 완료");
			
			String resultPage=action.execute(request, response);
			System.out.println("액션서블릿 resultPage : "+resultPage);
			String result=resultPage.substring(resultPage.indexOf(":")+1);
			System.out.println("액션서블릿 result : "+result);
			
			if(resultPage.startsWith("forward:"))
				HttpUtil.forward(request, response, result);
			else
				HttpUtil.redirect(response, result);
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		System.out.println("액션서블릿 service() 끝-------------------");
	}
}