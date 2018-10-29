package com.model2.mvc.service.purchase.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.ProductService;
import com.model2.mvc.service.product.impl.ProductServiceImpl;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.purchase.vo.PurchaseVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.impl.UserServiceImpl;
import com.model2.mvc.service.user.vo.UserVO;

public class PurchaseDAO {
	
	public PurchaseDAO() {
	}
	
	public void insertPurchase(PurchaseVO purchase) throws Exception{
				
		Connection con = DBUtil.getConnection();
		
		String sql="INSERT INTO transaction"
				+ " VALUES (seq_transaction_tran_no.nextval,?,?,?,?,?,?,?,?,SYSDATE,?)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
		stmt.setString(2, purchase.getBuyer().getUserId());
		stmt.setString(3, purchase.getPaymentOption());
		stmt.setString(4, purchase.getReceiverName());
		stmt.setString(5, purchase.getReceiverPhone());
		stmt.setString(6, purchase.getDivyAddr());
		stmt.setString(7, purchase.getDivyRequest());
		stmt.setString(8, purchase.getTranCode());
		stmt.setString(9, purchase.getDivyDate());
		
		stmt.executeUpdate();
		
		stmt.close();
		con.close();
		
	}
	
	public HashMap<String, Object> getPurchaseList(SearchVO searchVO, String buyerId) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="SELECT * FROM transaction WHERE buyer_id='"+buyerId+"'";
		
		PreparedStatement stmt = 
				con.prepareStatement(	sql,
															ResultSet.TYPE_SCROLL_INSENSITIVE,
															ResultSet.CONCUR_UPDATABLE);
		ResultSet rs = stmt.executeQuery();
		
		rs.last();
		int total = rs.getRow();
		System.out.println("로우의 수:" + total);

		HashMap<String,Object> map = new HashMap<String,Object>();
		map.put("count", new Integer(total));

		rs.absolute(searchVO.getPage() * searchVO.getPageUnit() - searchVO.getPageUnit()+1);
		System.out.println("searchVO.getPage():" + searchVO.getPage());
		System.out.println("searchVO.getPageUnit():" + searchVO.getPageUnit());
		
		List<PurchaseVO> list = new ArrayList<PurchaseVO>();
		if(total>0) {
			for(int i = 0; i < searchVO.getPageUnit(); i++) {
			
			UserVO user=new UserVO();
			UserService userUservice=new UserServiceImpl();
			user=userUservice.getUser(buyerId);
			
			ProductVO product=new ProductVO();
			ProductService productService=new ProductServiceImpl();
			product.setProdNo(rs.getInt("PROD_NO"));
			product=productService.getProduct(product.getProdNo());
			
			PurchaseVO purchase=new PurchaseVO();
			purchase.setBuyer(user);
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setPurchaseProd(product);
			purchase.setTranNo(rs.getInt("TRAN_NO"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			
			list.add(purchase);
			if (!rs.next())
				break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		
		rs.close();
		stmt.close();
		con.close();
		
		return map;
	}
	
	public PurchaseVO findPurchase(int tranNo) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="SELECT * FROM transaction WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, tranNo);
		
		ResultSet rs = stmt.executeQuery();
		
		PurchaseVO purchase=null;
		
		while(rs.next()) {
			
			UserVO user=new UserVO();
			UserService userUservice=new UserServiceImpl();
			user=userUservice.getUser(rs.getString("BUYER_ID"));
			
			ProductVO product=new ProductVO();
			ProductService productService=new ProductServiceImpl();
			product=productService.getProduct(rs.getInt("prod_no"));
						
			purchase=new PurchaseVO();
			purchase.setBuyer(user);
			purchase.setDivyAddr(rs.getString("demailaddr"));
			purchase.setDivyDate(rs.getString("dlvy_date"));
			purchase.setDivyRequest(rs.getString("dlvy_request"));
			purchase.setOrderDate(rs.getDate("order_date"));
			purchase.setPaymentOption((rs.getString("payment_option")));
			purchase.setPurchaseProd(product);
			purchase.setReceiverName(rs.getString("receiver_name"));
			purchase.setReceiverPhone(rs.getString("receiver_phone"));
			purchase.setTranCode(rs.getString("tran_status_code"));
			purchase.setTranNo(rs.getInt("tran_no"));	
		}
		
		con.close();
		
		return purchase;
	}
	
	public void updatePurchase(PurchaseVO purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="UPDATE transaction SET payment_option=?, receiver_name=?, "
				+ "receiver_phone=?, demailaddr=?, dlvy_request=?,"
				+ "dlvy_date=? WHERE tran_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, purchase.getPaymentOption());
		stmt.setString(2, purchase.getReceiverName());
		stmt.setString(3, purchase.getReceiverPhone());
		stmt.setString(4, purchase.getDivyAddr());
		stmt.setString(5, purchase.getDivyRequest());
		stmt.setString(6, purchase.getDivyDate());
		stmt.setInt(7, purchase.getTranNo());
		stmt.executeUpdate();
		
		con.close();

	}

	public void updateTranCode(PurchaseVO purchase) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		if(purchase.getPurchaseProd().getProTranCode().equals("2")) {
			
			String sql="UPDATE transaction SET tran_status_code='2'"
					+ " WHERE prod_no=?";
		
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getPurchaseProd().getProdNo());
			stmt.executeUpdate();
					
		}else if(purchase.getPurchaseProd().getProTranCode().equals("3")) {
			
			String sql="UPDATE transaction SET tran_status_code='3'"
					+ " WHERE tran_no=?";
			
			PreparedStatement stmt = con.prepareStatement(sql);
			stmt.setInt(1, purchase.getTranNo());
			stmt.executeUpdate();
		}
		
		con.close();
	}

}
