package com.model2.mvc.service.product.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.common.util.DBUtil;
import com.model2.mvc.service.product.vo.ProductVO;
import com.model2.mvc.service.user.vo.UserVO;

public class ProductDAO {

	public ProductDAO() {
	}
	
	public void insertProduct(ProductVO productVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql="insert into product values (seq_product_prod_no.nextval,?,?,?,?,?,sysdate)";
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate().replace("-", ""));
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		
		stmt.executeUpdate();
		
		con.close();
	}
	
	public HashMap<String, Object> getProductList(SearchVO searchVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql= 
				"SELECT p.PROD_NO, p.prod_name, p.prod_detail, p.MANUFACTURE_DAY, p.PRICE, p.IMAGE_FILE, p.REG_DATE, t.tran_status_code"
				+" FROM product p, transaction t ";
		if(searchVO.getSearchCondition() !=null) {
			if(searchVO.getSearchCondition().equals("0")) {
				sql +="WHERE PROD_NO like '%"+searchVO.getSearchKeyword()+"%' AND p.prod_no=t.prod_no(+)";
			}else if(searchVO.getSearchCondition().equals("1")) {
				sql +="WHERE PROD_NAME like '%"+searchVO.getSearchKeyword()+"% AND p.prod_no=t.prod_no(+)";
			}else if(searchVO.getSearchCondition().equals("2")) {
				sql +="WHERE PRICE like '%"+searchVO.getSearchKeyword()+"% AND p.prod_no=t.prod_no(+)";
			}
		}else {
			sql+=" WHERE p.prod_no=t.prod_no(+)";
		}
		sql += " ORDER BY p.prod_no";
		
		System.out.println(sql);
		
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

		ArrayList<ProductVO> list = new ArrayList<ProductVO>();
		if (total > 0) {
			for (int i = 0; i < searchVO.getPageUnit(); i++) {
				ProductVO vo = new ProductVO();
				vo.setProdNo(rs.getInt("PROD_NO"));
				vo.setProdName(rs.getString("prod_name"));
				vo.setProdDetail(rs.getString("prod_detail"));
				vo.setManuDate(rs.getString("MANUFACTURE_DAY"));
				vo.setPrice(rs.getInt("PRICE"));
				vo.setFileName(rs.getString("IMAGE_FILE"));
				vo.setRegDate(rs.getDate("REG_DATE"));
				//vo.setProTranCode(rs.getString("tran_status_code"));
				
				list.add(vo);
				if (!rs.next())
					break;
			}
		}
		System.out.println("list.size() : "+ list.size());
		map.put("list", list);
		System.out.println("map().size() : "+ map.size());

		con.close();

		return map;
	}

	public ProductVO findProduct(int prodNo) throws Exception{
		
		System.out.println("프로덕트VO findProduct() 시작");
		
		Connection con = DBUtil.getConnection();
		
		String sql="select * from product where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setInt(1, prodNo);
		
		ResultSet rs = stmt.executeQuery();
		
		ProductVO productVO=null;
		while(rs.next()) {
			productVO=new ProductVO();
			productVO.setProdNo(rs.getInt("PROD_NO"));
			productVO.setProdName(rs.getString("prod_name"));
			productVO.setProdDetail(rs.getString("prod_detail"));
			productVO.setManuDate(rs.getString("MANUFACTURE_DAY"));
			productVO.setPrice(rs.getInt("PRICE"));
			productVO.setFileName(rs.getString("IMAGE_FILE"));
			productVO.setRegDate(rs.getDate("REG_DATE"));
		}
		
		con.close();
		
		return productVO;
	}
	
	public void updateProduct(ProductVO productVO) throws Exception{
		
		Connection con = DBUtil.getConnection();
		
		String sql = "update product set prod_name=?, prod_detail=?, MANUFACTURE_DAY=?, PRICE=?, IMAGE_FILE=? where prod_no=?";
		
		PreparedStatement stmt = con.prepareStatement(sql);
		stmt.setString(1, productVO.getProdName());
		stmt.setString(2, productVO.getProdDetail());
		stmt.setString(3, productVO.getManuDate());
		stmt.setInt(4, productVO.getPrice());
		stmt.setString(5, productVO.getFileName());
		stmt.setInt(6, productVO.getProdNo());
		stmt.executeUpdate();
		
		con.close();
		
	}
}
