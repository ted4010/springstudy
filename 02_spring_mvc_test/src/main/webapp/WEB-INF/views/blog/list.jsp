<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"  prefix="c"%>
<c:set var="contextPath " />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블로그 목록</title>
<style type="text/css">
  .blog{
    width: 200px;
    cursor: pointer;
    background-color: yellow;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>
  
  <!--  ${ } 사이에 변수나 연산 등을 삽입할 수 있게 되었다  -->
  <c:forEach items="${blogList}" var="blog" varStatus="vs">
    <div class="blog">
      <span>${vs.index}</span>
      <span class="blog-no">${blog.blogNo}</span>
      <span>${blog.title}</span>
    </div>
  </c:forEach>
  
  <script type="text/javascript">
  
<!-- $(선택자).동작함수1().동작함수2()
     선택자를 이용하여 원하는 HTML 요소를 선택하고, 
     동작 함수를 정의하여 선택된 요소에 원하는 동작을 설정한다. 
  -->
  $('.blog').on('click', function(evt){
<!-- 그냥 쓰거나 함수 안에서 쓰면 this는 window를 뜻합니다.
	   window는 모든 전역변수, 함수, DOM을 보관하고 관리하는 전역객체입니다.-->
	  let blogNo = $(this).find('.blog-no').text()  
	  location.href = '${contextPath}/blog/detail.do?blogNo=' + blogNo;
	});
  
  </script>

</body>
</html>