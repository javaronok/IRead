<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%--@elvariable id="book" type="com.iread.model.IReadBook"--%>

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
  <link rel="stylesheet" href="css/bootstrap-multiselect.css" type="text/css"/>

  <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
  <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
  <!--[if lt IE 9]>
  <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
  <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
  <![endif]-->
</head>
<body>
<input id="bookId" type="hidden" value="${book.id}" />
<input id="loadBookUrl" type="hidden" value="<c:url value="/loadbook"/>" />
<input id="tagsGetUrl" type="hidden" value="<c:url value="/tags"/>" />
<input id="postBookUrl" type="hidden" value="<c:url value="/book_persist"/>" />
<input id="editBookUrl" type="hidden" value="<c:url value="/book_update"/>" />
<input id="uploadBookCoverUrl" type="hidden" value="<c:url value="/upload"/>">
<input id="homeUrl" type="hidden" value="<c:url value="/catalog"/>">

<div class="navbar navbar-inverse">
  <div class="container">
    <div class="navbar-header">
      <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#responsive-menu">
        <span class="sr-only">Открыть навигацию</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${pageContext.request.contextPath}/catalog">#БУДУЧИТАТЬ</a>
    </div>
    <div class="collapse navbar-collapse" id="responsive-menu">
      <!--ul class="nav navbar-nav">
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
      </ul-->
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
          <form action="<c:url value='/j_spring_security_check' />" method="POST" class="navbar-form navbar-right">
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
        </c:otherwise>
      </c:choose>
    </div>
  </div>
</div>

<div class="container">
  <form class="form-horizontal">
    <h4 class="muted offset1">Добавление книги:</h4>
    <div class="control-group">
      <label class="control-label" for="bookName">Название книги</label>
      <div class="controls">
        <input type="text" id="bookName" placeholder="Название книги" path="bookName" value="${book.bookName}"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="authorLastName">Фамилия автора</label>
      <div class="controls">
        <input type="text" id="authorLastName" placeholder="Фамилия автора" path="authorLastName" value="${book.authorLastName}"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="authorFirstName">Имя автора</label>
      <div class="controls">
        <input type="text" id="authorFirstName" placeholder="Имя автора" path="authorFirstName" value="${book.authorFirstName}"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="authorPatronymic">Отчество автора</label>
      <div class="controls">
        <input type="text" id="authorPatronymic" placeholder="Отчество автора" path="authorPatronymic" value="${book.authorPatronymic}"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="bookYear">Год издания</label>
      <div class="controls">
        <input id="bookYear" placeholder="Год издания" path="bookYear" value="${book.publicationYear}"/>
      </div>
    </div>
    <div class="control-group">
      <label class="control-label" for="bookAnnotation">Аннотация</label>
      <div class="controls">
        <textarea id="bookAnnotation" placeholder="Аннотация" rows="5" cols="40" path="bookAnnotation">${book.annotation}</textarea>
      </div>
    </div>

    <div>
      <select id="book-tags" multiple="multiple"></select>
    </div>

    <div class="control-group">
      <label class="control-label" for="bookAnnotation">Обложка</label>
      <div class="controls">
        <input id="bookCoverFile" type="file" class="file" data-preview-file-type="text">
      </div>
    </div>

    <div class="control-group">
      <div class="span5">
        <button id="addBookBtn" class="btn btn-primary btn-small pull-right">Добавить книгу на сайт</button>
      </div>
    </div>
  </form>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-1.11.3.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/salvattore.min.js"></script>
<script src="js/bootstrap-rating.js"></script>
<script src="js/bootstrap-multiselect.js"></script>
<script src="js/add-book.js"></script>
<script src="js/rivets.js"></script>
<!--script src="js/jquery-2.1.4.js"></script-->
</body>
</html>