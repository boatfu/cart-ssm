<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en"><head>

    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>Enjoy VPN - 添加商品</title>

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
            <<ul class="navbar-nav ml-auto my-2 my-lg-0">
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
                <form class="form-signin" action="/addProduct" method="post">
                    ${msg}
                    <label for="name" class="sr-only">商品名称</label>
                    <input type="text" id="name" name = "name" class="form-control" placeholder="商品名称" required="" autofocus="">

                    <label for="price" class="sr-only">商品价格</label>
                    <input type="text" id="price" name="price" class="form-control" placeholder="价格" required="" >
                    <button class="btn btn-lg btn-primary btn-block" type="submit">添加商品</button>
                </form>
            </div>


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




</body></html>
