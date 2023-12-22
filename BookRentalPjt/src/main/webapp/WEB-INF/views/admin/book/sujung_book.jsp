<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">

<jsp:include page="../include/modify_book_form_js.jsp" />

</head>
<body>
	
	<section>
		
		<div id="section_wrap">
		
			<div class="word">
			
				<h3>책 정보 수정하기</h3>
				
			</div>
		
			<div class="modify_book_form">
			
				<form action="<c:url value='/book/admin/modifyBookConfirm' />" name="modify_book_form" method="post" enctype="multipart/form-data">
					<c:forEach var="item" items="${bookVos}">
					<h2>코드 : <input type="text" name="b_no" value="${item.b_no}"></h2>
								
								<h2>책 제목 : <input type="text" name="b_name" value="${item.b_name}" placeholder="INPUT BOOK NAME."></h2> <br>
								<h2>저자 : <input type="text" name="b_author" value="${item.b_author}" placeholder="INPUT BOOK AUTHOR."></h2> <br>
								<h2>만든회사 : <input type="text" name="b_publisher" value="${item.b_publisher}" placeholder="INPUT BOOK PUBLISHER."></h2> <br>
								<h2>발행연도 : <input type="text" name="b_publish_year" value="${item.b_publish_year}" placeholder="INPUT BOOK PUBLISH YEAR."></h2> <br>
								<h2>ISBN : <input type="text" name="b_isbn" value="${item.b_isbn}" placeholder="INPUT BOOK ISBN."></h2> <br>
								<h2>청구번호 : <input type="text" name="b_call_number" value="${item.b_call_number}" placeholder="INPUT BOOK CALL NUMBER."></h2> <br>
								<h2>대출여부 :
								<select name="b_rantal_able">
									<option value="">SELECT BOOK RANTAL ABLE.</option>
									<option value="0" <c:if test="${item.b_rantal_able eq '0'}"> selected </c:if>>UNABLE.</option>
									<option value="1" <c:if test="${utem.b_rantal_able eq '1'}"> selected </c:if>>ABLE.</option>
								</select>
								</h2>
								<br>
								<h2>파일 : <input type="file" name="file"></h2><br>
								<input type="button" value="수정" onclick="modifyBookForm();"> 
					</c:forEach>
				</form>
				
			</div>
		
		</div>
		
	</section>
	
</body>
</html>