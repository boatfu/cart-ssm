<%--
  Created by IntelliJ IDEA.
  User: boate
  Date: 2019/3/25
  Time: 18:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%String result = (String)request.getSession().getAttribute("result") ;%>
<%out.print(result);%>
</body>
</html>
