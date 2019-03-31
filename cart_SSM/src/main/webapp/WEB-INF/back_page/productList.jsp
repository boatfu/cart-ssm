
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>





<html lang="en"><head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Enjoy VPN - 管理商品</title>

    <!-- Font Awesome Icons -->
    <link href="${pageContext.request.contextPath}/front_page/vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">

    <!-- Google Fonts -->
    <link href="https://fonts.googleapis.com/css?family=Merriweather+Sans:400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Merriweather:400,300,300italic,400italic,700,700italic" rel="stylesheet" type="text/css">

    <!-- Plugin CSS -->
    <link href="${pageContext.request.contextPath}/front_page/vendor/magnific-popup/magnific-popup.css" rel="stylesheet">

    <!-- Theme CSS - Includes Bootstrap -->
    <link href="${pageContext.request.contextPath}/front_page/css/creative.min.css" rel="stylesheet">

</head>




<body id="page-top">

<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-light fixed-top py-3" id="mainNav">
    <div class="container">
        <a class="navbar-brand js-scroll-trigger" href="/products">Enjoy VPN</a>
        <button class="navbar-toggler navbar-toggler-right collapsed" type="button" data-toggle="collapse" data-target="#navbarResponsive" aria-controls="navbarResponsive" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="navbar-collapse collapse" id="navbarResponsive" style="">
            <ul class="navbar-nav ml-auto my-2 my-lg-0">
                <c:if test = "${user==null}">
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="/loginHtml">登录</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="/registHtml">注册</a>
                </li>
                </c:if>
                <c:if test = "${user!=null}">
                <li class="nav-item">
                    <a class="nav-link js-scroll-trigger" href="/myAccount">我的账号</a>
                </li></c:if>

            </ul>
        </div>
    </div>
</nav>

<!-- Masthead -->
<header class="masthead">
    <div class="container h-100">
        <div class="row h-100 align-items-center justify-content-center text-center">

            <div class="col-xs-6 col-md-4 col-center-block">
                商品列表 - 共${total}件商品

                <a class="btn btn-lg btn-primary btn-block" href="/addProductHtml" >点我添加商品</a>
                ${msg}


            </div>

            <table class="table table-striped table-bordered table-hover table-condensed">


                <thead>
                <tr class="success">

                    <th>商品名称</th>
                    <th>商品价格</th>
                    <th>编辑</th>
                    <th>删除</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${productList}" var="s" varStatus="status">
                    <tr>

                        <td>${s.name}</td>
                        <td>${s.price}</td>



                        <td><a href="/editProductHtml?id=${s.id}" class="btn btn-info btn-lg"><span class="glyphicon glyphicon-edit"></span> </a></td>
                        <td><a href="/deleteProduct?id=${s.id}" onclick="javascript:return del();" class="btn btn-info btn-lg"><span
                                class="glyphicon glyphicon-trash"></span> </a></td>
                    </tr>
                </c:forEach>

                </tbody>
            </table>

        </div>
    </div>
</header>
<!-- Footer -->
<footer class="bg-light py-5">
    <div class="container">
        <div class="small text-center text-muted">Copyright © 2019 - Enjoy VPN</div>
    </div>
</footer>

<!-- Bootstrap core JavaScript -->
<script src="../../front_page/vendor/jquery/jquery.min.js"></script>
<script src="../../front_page/vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

<!-- Plugin JavaScript -->
<script src="../../front_page/vendor/jquery-easing/jquery.easing.min.js"></script>
<script src="../../front_page/vendor/magnific-popup/jquery.magnific-popup.min.js"></script>

<!-- Custom scripts for this template -->
<script src="../../front_page/js/creative.min.js"></script>
<script>

    function del() {
        var msg = "您真的确定要删除吗？\n\n请确认！";
        if (confirm(msg) == true) {
            return true;
        } else {
            return false;
        }
    }
</script>



</body></html>
