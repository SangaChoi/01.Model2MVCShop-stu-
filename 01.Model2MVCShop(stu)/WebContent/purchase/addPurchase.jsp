<%@ page contentType="text/html; charset=EUC-KR"%>
<%@ page import="com.model2.mvc.service.product.vo.*" %>
<%@ page import="com.model2.mvc.service.user.vo.*" %>
<%@ page import="com.model2.mvc.service.purchase.vo.*" %>

	<%
	UserVO user=(UserVO)request.getAttribute("user");
	ProductVO product=(ProductVO)request.getAttribute("product");
	PurchaseVO purchase=(PurchaseVO)request.getAttribute("purchase");
	%>

<html>
<head>
<title>Insert title here</title>
</head>

<body>

<form name="updatePurchase" action="/updatePurchaseView.do?tranNo=0" method="post">

다음과 같이 구매가 되었습니다.

<table border=1>
	<tr>
		<td>물품번호</td>
		<td><%=product.getProdNo() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자아이디</td>
		<td><%=user.getUserId()%></td>
		<td></td>
	</tr>
	<tr>
		<td>구매방법</td>
		<td>
		
	 	<%if(purchase.getPaymentOption().equals("1")){ %>
		현금구매
		<%}else if(purchase.getPaymentOption().equals("2")){ %>
		신용구매
		<%} %>
				
		</td>
		<td></td>
	</tr>
	<tr>
		<td>구매자이름</td>
		<td><%=user.getUserName() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자연락처</td>
		<td><%=user.getPhone() %></td>
		<td></td>
	</tr>
	<tr>
		<td>구매자주소</td>
		<td><%=user.getAddr() %></td>
		<td></td>
	</tr>
		<tr>
		<td>구매요청사항</td>
		<td><%=purchase.getDivyRequest() %></td>
		<td></td>
	</tr>
	<tr>
		<td>배송희망일자</td>
		<td><%=purchase.getDivyDate() %></td>
		<td></td>
	</tr>
</table>
</form>

</body>
</html>