package com.model2.mvc.service.user.impl;

import java.util.HashMap;

import com.model2.mvc.common.SearchVO;
import com.model2.mvc.service.user.UserService;
import com.model2.mvc.service.user.dao.UserDAO;
import com.model2.mvc.service.user.vo.UserVO;


public class UserServiceImpl implements UserService{
	
	private UserDAO userDAO;
	
	public UserServiceImpl() {
		userDAO=new UserDAO();
		System.out.println("유저서비스임플 생성자 생성");
	}

	public void addUser(UserVO userVO) throws Exception {
		userDAO.insertUser(userVO);
	}

	public UserVO loginUser(UserVO userVO) throws Exception {
		
		System.out.println("유저서비스임플 loginUser() 시작");
		
			UserVO dbUser=userDAO.findUser(userVO.getUserId());

			if(! dbUser.getPassword().equals(userVO.getPassword())) {
				throw new Exception("로그인에 실패했습니다.");
			}
			
		System.out.println("유저서비스임플 loginUser() 끝");
			
			return dbUser;
	}

	public UserVO getUser(String userId) throws Exception {
		return userDAO.findUser(userId);
	}

	public HashMap<String,Object> getUserList(SearchVO searchVO) throws Exception {
		return userDAO.getUserList(searchVO);
	}

	public void updateUser(UserVO userVO) throws Exception {
		userDAO.updateUser(userVO);
	}

	public boolean checkDuplication(String userId) throws Exception {
		boolean result=true;
		UserVO userVO=userDAO.findUser(userId);
		if(userVO != null) {
			result=false;
		}
		return result;
	}
}