<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>

<sec:authorize var="isAuth" access="isAuthenticated()" />
<sec:authentication var="principal" property="principal" />

<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
  <title>Bootstrap Template</title>

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
        <li><a href="#">пункт 1</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">пункт 2 <b class="caret"></b></a>
          <ul class="dropdown-menu">
            <li><a href="#">пункт 1</a></li>
            <li><a href="#">пункт 2</a></li>
            <li><a href="#">пункт 3</a></li>
            <li><a href="#">пункт 4</a></li>
          </ul>
        </li>
        <li><a href="#">пункт 3</a></li>
        <li><a href="#">пункт 4</a></li>
      </ul>
      <c:choose>
        <c:when test="${isAuth}">
          <ul class="nav navbar-nav">
            <li class="dropdown">
              <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                  ${principal.username} <span class="caret"></span>
              </a>
              <ul class="dropdown-menu">
                <li><a href="<c:url value="/j_spring_security_logout" />">Выйти</a></li>
              </ul>
            </li>
          </ul>
        </c:when>
        <c:otherwise>
          <form action="<c:url value='/j_spring_security_check' />" method="POST" class="navbar-form navbar-right">
            <div class="form-group">
              <input type="text" class="form-control" id="user" name="j_username" placeholder="E-mail" value="">
            </div>
            <div class="form-group">
              <input type="password" class="form-control" id="password" name="j_password" placeholder="Пароль" value="">
            </div>
            <button type="submit" class="btn btn-primary">
              <i class="fa fa-sign-in"></i> ВОЙТИ
            </button>
          </form>
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<div class="container">
  <form:form class="form-horizontal" method="POST" commandName="userSignUp" action="signup_save">
    <h4 class="muted offset1">Регистрация пользователя</h4>
    <div class="control-group">
      <label class="control-label" for="inputLogin">Логин</label>
      <div class="controls">
        <form:input type="text" id="inputLogin" placeholder="Логин" path="login"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="inputFirstName">Имя</label>
      <div class="controls">
        <form:input type="text" id="inputFirstName" placeholder="Имя" path="firstName"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="inputLastName">Фамилия</label>
      <div class="controls">
        <form:input type="text" id="inputLastName" placeholder="Фамилия" path="lastName"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="inputPassword">Пароль</label>
      <div class="controls">
        <form:input type="password" id="inputPassword" placeholder="Пароль" path="passwd"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="repeatPassword">Пароль ещё раз</label>
      <div class="controls">
        <form:input type="password" id="repeatPassword" placeholder="Пароль ещё раз" path="passwdRepeat"/>
      </div>
    </div>
    <div class="control-group">
      <div class="span5">
        <button type="submit" class="btn btn-primary btn-small pull-right">Зарегистрироваться</button>
      </div>
    </div>
  </form:form>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
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