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

<input id="homeUrl" type="hidden" value="<c:url value="/catalog"/>">
<input id="ratingsTableUrl" type="hidden" value="<c:url value="/rating_table_full"/>">
<input id="compositeTableUrl" type="hidden" value="<c:url value="/composite_table_full"/>">
<input id="cbfTableUrl" type="hidden" value="<c:url value="/cbf_table_full"/>">
<input id="collaborativeTableUrl" type="hidden" value="<c:url value="/collaborative_table_full"/>">

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
  <h4 class="muted offset1">Профиль пользователя:</h4>

  <!-- Nav tabs -->
  <ul class="nav nav-tabs">
    <li class="active"><a href="#ratings-tab" data-toggle="tab">Оценки</a></li>
    <li><a href="#cbf-tab" data-toggle="tab">Content-based</a></li>
    <li><a href="#collabr-tab" data-toggle="tab">Collabarative</a></li>
    <li><a href="#composite-tab" data-toggle="tab">Composite</a></li>
  </ul>

  <!-- Tab panes -->
  <div class="tab-content" id="control-content-view">
    <div class="tab-pane active" id="ratings-tab">
      <div class="tableContent">
        <table id="ratingsTable" class="table table-striped table-bordered table-hover">
          <thead>
          <tr>
            <th>Книга</th>
            <th>Автор</th>
            <th>Тэги</th>
            <th>Средний балл</th>
            <th>Оценка пользователя</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
    <div class="tab-pane" id="cbf-tab">
      <div class="tableContent">
        <table id="cbfTable" class="table table-striped table-bordered table-hover">
          <thead>
          <tr>
            <th>Книга</th>
            <th>Автор</th>
            <th>Тэги</th>
            <th>Средний балл</th>
            <th>Значение рекомендации</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
    <div class="tab-pane" id="collabr-tab">
      <div class="tableContent">
        <table id="collabrTable" class="table table-striped table-bordered table-hover">
          <thead>
          <tr>
            <th>Книга</th>
            <th>Автор</th>
            <th>Тэги</th>
            <th>Средний балл</th>
            <th>Значение рекомендации</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
    <div class="tab-pane" id="composite-tab">
      <div class="tableContent">
        <table id="compositeTable" class="table table-striped table-bordered table-hover">
          <thead>
          <tr>
            <th>Книга</th>
            <th>Автор</th>
            <th>Тэги</th>
            <th>Средний балл</th>
            <th>Значение рекомендации</th>
          </tr>
          </thead>
        </table>
      </div>
    </div>
  </div>
</div>

<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="js/jquery-1.11.3.js"></script>
<!-- Include all compiled plugins (below), or include individual files as needed -->
<script src="js/bootstrap.js"></script>
<script src="js/salvattore.min.js"></script>
<script src="js/rivets.js"></script>
<script src="js/user_profile.js"></script>
<!--script src="js/jquery-2.1.4.js"></script-->

<!-- Datatables -->
<script src="js/datatables/jquery.dataTables.js"></script>
<script src="js/datatables/dataTables.bootstrap.min.js"></script>
<script src="js/datatables/dataTables.tableTools.min.js"></script>
<script src="js/datatables/dataTables.colReorder.min.js"></script>
<script src="js/datatables/dataTables.colVis.min.js"></script>
<script src="js/datatables/dataTables.columnFilter.js"></script>
<script src="js/datatables/datatables.responsive.min.js"></script>

</body>
</html>