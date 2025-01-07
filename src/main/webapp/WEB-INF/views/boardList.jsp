<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>게시판 글 리스트</title>
</head>
<body>
	<h2>게시판 글 목록</h2>
	<hr>
	<table border="1" cellspacing="0" cellpadfding="0" width="800">
		<tr>
			<th>번호</th>
			<th width="400">제목</th>
			<th>작성자</th>
			<th>등록일</th>
			<th>조회수</th>
			<th>삭제</th>
		</tr>
		<c:forEach items="${boardList}" var="boardDto">
		<tr>
			<td>${boardDto.bnum}</td>
			<td><a href="content_view?bnum=${boardDto.bnum}">${boardDto.btitle}</a></td>
			<td>${boardDto.bid}</td>
			<td>${boardDto.bdate}</td>
			<td>${boardDto.bhit}</td>
			<td><input type="button" value="삭제" onclick="javascript:location.href='content_delete?bnum=${boardDto.bnum}'"></td>
		</tr>
		</c:forEach>
		<tr>
			<td colspan="6">
				<input type="button" value="글쓰기" onclick="javascript:location.href='writeform'">
			</td>
		</tr>
	</table>

</body>
</html>