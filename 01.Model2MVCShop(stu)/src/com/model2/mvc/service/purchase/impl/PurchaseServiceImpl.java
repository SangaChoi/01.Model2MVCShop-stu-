package com.model2.mvc.service.purchase.impl;

import java.util.HashMap;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.product.dao.ProductDAO;
import com.model2.mvc.service.purchase.PurchaseService;
import com.model2.mvc.service.purchase.dao.PurchaseDAO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;

public class PurchaseServiceImpl implements PurchaseService {
	
	private ProductDAO productDAO;
	private PurchaseDAO purchaseDAO;
	
	public PurchaseServiceImpl() {
		purchaseDAO=new PurchaseDAO();
	}

	@Override
	public void addPurchase(PurchaseVO purchase) throws Exception {
		purchaseDAO.insertPurchase(purchase);
	}

	@Override
	public PurchaseVO getPurchase(int tranNo) throws Exception {
		return purchaseDAO.findPurchase(tranNo);
	}

	@Override
	public PurchaseVO getPurchase2(int ProdNo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<String, Object> getPurchaseList(SearchVO search, String buyerId) throws Exception {
		return purchaseDAO.getPurchaseList(search, buyerId);	
	}

	@Override
	public HashMap<String, Object> getSaleList(SearchVO search) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void updatePurcahse(PurchaseVO purchase) throws Exception {
		purchaseDAO.updatePurchase(purchase);		
	}

	@Override
	public void updateTranCode(PurchaseVO purchase) throws Exception {
		purchaseDAO.updateTranCode(purchase);
		
	}
	
}
