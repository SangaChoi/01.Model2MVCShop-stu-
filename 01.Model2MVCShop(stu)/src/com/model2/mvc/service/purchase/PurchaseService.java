package com.model2.mvc.service.purchase;

import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;


public interface PurchaseService {

	public void addPurchase(PurchaseVO purchase) throws Exception;
	
	public PurchaseVO getPurchase(int tranNo) throws Exception;
	
	public PurchaseVO getPurchase2(int ProdNo) throws Exception;
	
	public Map<String,Object> getPurchaseList(SearchVO search,String buyerId) throws Exception;
	
	public Map<String,Object> getSaleList(SearchVO search) throws Exception;
	
	public void updatePurcahse(PurchaseVO purchase) throws Exception;
	
	public void updateTranCode(PurchaseVO purchase) throws Exception;
	
}