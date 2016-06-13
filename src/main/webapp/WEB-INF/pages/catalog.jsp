<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--@elvariable id="books" type="List<com.iread.form.BookForm>"--%>
<%--@elvariable id="recommendations" type="List<com.iread.form.BookForm>"--%>
<%--@elvariable id="errorMessage" type="String>"--%>

<sec:authorize var="isAuth" access="isAuthenticated()" />
<sec:authentication var="principal" property="principal" />

<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <title>Буду читать</title>

    <!-- Bootstrap -->
    <link href="css/bootstrap.css" rel="stylesheet">
    <link href="css/font-awesome.css" rel="stylesheet">
    <link href="css/style.css" rel="stylesheet">
    <link href="css/bootstrap-rating.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
<input id="isAuthed" type="hidden" value="${isAuth}" />
<input id="bookListDataUrl" type="hidden" value="<c:url value="/books"/>" />
<input id="ratingPostUrl" type="hidden" value="<c:url value="/rating"/>" />
<input id="deleteBookUrl" type="hidden" value="<c:url value="/remove"/>" />

<div class="navbar navbar-inverse navbar-fixed-top">
    <div class="container">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
                <span class="sr-only">Открыть навигацию</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">#БУДУЧИТАТЬ</a>
        </div>
        <div class="collapse navbar-collapse" id="responsive-menu">
            <ul class="nav navbar-nav">
                <c:choose>
                    <c:when test="${isAuth}">
                        <sec:authorize access="hasRole('ADMIN')">
                            <li><a href="<c:url value="/addbook" />">Добавить книгу</a></li>
                        </sec:authorize>
                    </c:when>
                </c:choose>
                <!--li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">пункт 2 <b class="caret"></b></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">пункт 1</a></li>
                        <li><a href="#">пункт 2</a></li>
                        <li><a href="#">пункт 3</a></li>
                        <li><a href="#">пункт 4</a></li>
                    </ul>
                </li>
                <li><a href="#">пункт 3</a></li>
                <li><a href="#">пункт 4</a></li-->
            </ul>
            <c:choose>
                <c:when test="${isAuth}">
                    <ul class="nav navbar-nav">
                        <li class="dropdown">
                            <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                                    ${principal.username} <span class="caret"></span>
                            </a>
                            <ul class="dropdown-menu">
                                <li><a href="<c:url value="/profile" />">Профиль</a></li>
                                <li><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
                            </ul>
                        </li>
                    </ul>
                </c:when>
                <c:otherwise>
                    <div class="navbar-right">
                        <div class="row">
                            <section class="col-lg-9">
                                <form action="<c:url value='/j_spring_security_check' />" method="POST" class="navbar-form" style="width:530px">
                                    <div class="form-group">
                                        <input type="text" class="form-control" id="user" name="j_username" placeholder="Имя пользователя" value="">
                                    </div>
                                    <div class="form-group">
                                        <input type="password" class="form-control" id="password" name="j_password" placeholder="Пароль" value="">
                                    </div>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fa fa-sign-in"></i> ВОЙТИ
                                    </button>
                                </form>
                            </section>
                            <!--button class="btn btn-primary"-->
                            <section class="col-lg-3 navbar-form">
                                <a class="btn btn-primary" href="<c:url value="/signup"/>"><i class="fa fa-sign-in"></i> РЕГИСТРАЦИЯ</a>
                            </section>
                            <!--/button-->
                        </div>
                    </div>
                </c:otherwise>
            </c:choose>
        </div>
    </div>
</div>

<c:if test="${not empty errorMessage}">
    <div class="alert alert-danger" role="alert">
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true">&times;</span></button>
        <strong>Внимание!</strong> ${errorMessage}.
    </div>
</c:if>

<div id="carousel" class="carousel slide">
    <!--индикаторы слайдов-->
    <ol class="carousel-indicators">
        <li class="active" data-target="#carousel" data-slide-to="0"></li>
        <li data-target="#carousel" data-slide-to="1"></li>
        <li data-target="#carousel" data-slide-to="2"></li>
    </ol>

    <!--слайды-->
    <div class="carousel-inner">
        <div class="item active">
            <img src="images/1.jpg" alt="" class="center-block img-thumbnail">

            <div class="carousel-caption">
                <h3>Первый слайд</h3>

                <p>Описание первого слайда</p>
            </div>
        </div>

        <div class="item">
            <img src="images/2.jpg" alt="" class="center-block img-thumbnail">

            <div class="carousel-caption">
                <h3>Второй слайд</h3>

                <p>Описание второго слайда</p>
            </div>
        </div>

        <div class="item">
            <img src="images/3.jpg" alt="" class="center-block img-thumbnail">

            <div class="carousel-caption">
                <h3>Третий слайд</h3>

                <p>Описание третьего слайда</p>
            </div>
        </div>
    </div>

    <!--стрелки переключения слайдов-->
    <a href="#carousel" class="left carousel-control" data-slide="prev">
        <span class="glyphicon glyphicon-chevron-left"></span>
    </a>

    <a href="#carousel" class="right carousel-control" data-slide="next">
        <span class="glyphicon glyphicon-chevron-right"></span>
    </a>
</div>

<div class="container">
    <c:choose>
        <c:when test="${not empty recommendations}">
            <h3>Рекомендации </h3>

            <div class="row">
                <div class="row masonry" data-columns>
                    <c:forEach var="item" items="${recommendations}">
                        <div class="item">
                            <div class="thumbnail">
                                <c:choose>
                                    <c:when test="${not empty item.book.cover}">
                                        <img src="<c:url value="/download?uid=${item.book.cover}"/>" alt="" height="500" width="200" class="img-responsive">
                                    </c:when>
                                    <c:otherwise>
                                        <img src="images/placeholder.png" alt="" class="img-responsive">
                                    </c:otherwise>
                                </c:choose>

                                <div class="caption">
                                    <h3><a href="#">${item.book.bookName}</a></h3>
                                    <h4><a href="#">${item.book.authorName}</a></h4>

                                    <c:choose>
                                        <c:when test="${not empty item.avgRating}">
                                            <div style="display: inline-block; position: relative;" class="rating-symbol">
                                                <div style="visibility: hidden;"
                                                     class="rating-symbol-background glyphicon glyphicon-star-empty"></div>
                                                <div style="display: inline-block; position: absolute; overflow: hidden; left: 0px; right: 0px; width: auto;"
                                                     class="rating-symbol-foreground">
                                                    <span class="glyphicon glyphicon-star"></span>
                                                </div>
                                            </div>
                                            <span class="avg-rating label label-default">${item.avgRating}</span>
                                        </c:when>
                                    </c:choose>

                                    <p>${item.book.annotation}</p>
                                    <!--a href="#" class="btn btn-success">Подробнее <i class="fa fa-arrow-right"></i></a-->
                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>

            <hr>
        </c:when>
    </c:choose>

    <h3>Доступные книги </h3>

    <div class="row">
        <div id="bookStore" class="row masonry" data-columns >
            <!--div rv-each-book="books">
                <div class="item">
                    <div class="thumbnail">
                        <img src="images/placeholder.png" alt="" class="img-responsive">

                        <div class="caption">
                            <h3><a href="#">{book.bookName}</a></h3>

                            <p>{book.annotation}</p>
                            <a href="#" class="btn btn-success">Подробнее <i class="fa fa-arrow-right"></i></a>
                            <input type="hidden" class="rating"/>
                            <span class="label label-default"></span>
                        </div>
                    </div>
                </div>
            </div-->
            <c:forEach var="item" items="${books}">
                <div class="item" bookId="${item.book.id}">
                    <div class="thumbnail">
                        <c:choose>
                            <c:when test="${not empty item.book.cover}">
                                <img src="<c:url value="/download?uid=${item.book.cover}"/>" alt="" height="500" width="200" class="img-responsive">
                            </c:when>
                            <c:otherwise>
                                <img src="images/placeholder.png" alt="" class="img-responsive">
                            </c:otherwise>
                        </c:choose>

                        <div class="caption">
                            <h3><a href="#">${item.book.bookName} (${item.book.publicationYear})</a></h3>
                            <h4><a href="#">${item.book.authorName}</a></h4>
                            <c:if test="${not empty item.avgRating}">
                                <div style="display: inline-block; position: relative;" class="rating-symbol">
                                    <div style="visibility: hidden;" class="rating-symbol-background glyphicon glyphicon-star-empty"></div>
                                    <div style="display: inline-block; position: absolute; overflow: hidden; left: 0px; right: 0px; width: auto;" class="rating-symbol-foreground">
                                        <span class="glyphicon glyphicon-star"></span>
                                    </div>
                                </div>
                                <span class="avg-rating label label-default">${item.avgRating}</span>
                            </c:if>

                            <p>${item.book.annotation}</p>
                            <!--a href="#" class="btn btn-success">Подробнее <i class="fa fa-arrow-right"></i></a-->
                            <div>
                                <c:choose>
                                <c:when test="${isAuth}">
                                    <c:choose>
                                        <c:when test="${not empty item.ownRating}">
                                            <input type="hidden" class="rating" data-fractions="4" value="${item.ownRating}" />
                                        </c:when>
                                        <c:otherwise>
                                            <input type="hidden" class="rating" data-fractions="4" value="0" />
                                        </c:otherwise>
                                    </c:choose>
                                </c:when>
                                </c:choose>
                                <span class="label label-default"></span>

                                <c:if test="${isAuth && not empty item.book.owner && item.book.owner.username == principal.username}">
                                    <div class="pull-right">
                                        <button class="btn btn-default btn-del"><i class="fa fa-remove"></i> Удалить</button>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                </div>
            </c:forEach>
        </div>
    </div>
</div>



    <div class="navbar navbar-inverse navbar-static-bottom">
        <div id="footer" class="container">

            &copy; Ангелина
            <div id="btn_up"><a href="#" onclick="$('html, body').animate({scrollTop:0},1000)"> <span
                    class="glyphicon glyphicon-chevron-up">Наверх</span></a></div>
        </div>
        <!--main-->
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<!--script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script-->
<script src="js/jquery-1.11.3.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/salvattore.min.js"></script>
<script src="js/bootstrap-rating.js"></script>
<script src="js/catalog.js"></script>
<script src="js/rivets.js"></script>
<!--script src="js/jquery-2.1.4.js"></script-->
</body>
</html>