package com.model2.mvc.service.product.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.product.vo.ProductVO;

public class ProductServiceImpl implements ProductService{
	
	private ProductDAO productDAO;
	
	public ProductServiceImpl() {
		productDAO=new ProductDAO();
		System.out.println("프로덕트 서비스 임플 생성==>프로덕트DAO 생성");
	}
	
	public void addProduct(ProductVO productVO) throws Exception{
		productDAO.insertProduct(productVO);
	}

	@Override
	public ProductVO getProduct(int prodNo) throws Exception {
		return productDAO.findProduct(prodNo);
	}

	@Override
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception {
		return productDAO.getProductList(searchVO);
		
	}

	@Override
	public void updateProduct(ProductVO productVO) throws Exception {
		productDAO.updateProduct(productVO);
		
	}

}
