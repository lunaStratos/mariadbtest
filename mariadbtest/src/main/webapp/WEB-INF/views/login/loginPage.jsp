<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head lang="jp">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ログインが必要です</title>
<meta name="viewport" content="width=device-width, initial-scale=1"> 
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
#errorMsg {
	color : red;
}
</style>
</head>
<body>

<div class="container form-group">
	<h2>ページをご利用したい方、ログインお願い申し上げます。</h2>
	<form name="login" action="loginOK" method="post">
		<label for="uid">ID</label>:<br/>
		<input class="form-control" width="50" type="text" name="uid" value="${cookie.ipValue.value}" autocomplete="on" autofocus><br/>
		
		<label for="password">暗証番号</label>:<br/>
		<input class="form-control" width="50" type="password" name="password"><br/>
		
		<input type="checkbox" name="rememberMe" >IDを覚えます<br/>
		<br/>
		
		<b id="errorMsg">${errorMsg}</b><br/>
		<br/>
		<button class="btn btn-default" type="submit">ログイン</button>&nbsp;<button class="btn btn-info" type="reset">やり直し</button>
	</form>
</div>

<div class="container">
	<p>まだ、私たちの会員ではありませんか。それなら、会員登録に、出発！</p>
	<a href="register"><button class="btn btn-info">会員登録ページに</button></a>
</div>

</body>
</html>