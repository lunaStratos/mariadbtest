<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
.formTable{
	margin: auto;
}

.checkid{
	margin: auto;
}
</style>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<script>
	$(document).ready(function() {
		// 키 입력때마다 id 중복 체크 
		$('#uid').keyup(function(event) {
			
			//공백 입력시 백스페이스 
			if (event.keyCode == 32) {
				$('#uid').val($("#uid").val().slice(0, -1));

			}
			var id = $('#uid').val();
			$.ajax({
				url : 'idcheck',
				method : 'post',
				data : {
					id : id
				},
				dataType : 'text',
				success : function(item) {
					$('#checkid').text(item);
					//id의 길이가 0인 경우 다시 원 상태 
					if (id.length == 0) {
						$('#checkid').text('idを入力してください。');
					}
				}

			});
		});
		
		// name 공백 방지 
		$('#name').keyup(function(event) {
			if (event.keyCode == 32) {
				$('#name').val($("#name").val().slice(0, -1));
			}
			
		});
		
		// name 공백 방지 
		$('#password').keyup(function(event) {
			if (event.keyCode == 32) {
				$('#password').val($("#password").val().slice(0, -1));
			}
			
		});


	});

	function submitok() {
		var form = document.getElementById('forms'); //form id get
		var id = document.getElementById('uid').value; //input id
		var name = document.getElementById('name').value; //input name
		var password = document.getElementById('password').value; // input password

		// id: english와 int만 입력  (java단에도 있음 )
		if (id.match(/[^a-zA-Z0-9]/) != null) {
			alert("idは数値と英語のみです。");
			return false;
		}
		// id가 length가 0이면 
		if (id.length < 1) {
			alert("idを入力してください。");
			return false;
		}
		// name가 length가 0이면
		if (name.length < 1) {
			alert("名前を入力してください。");
			return false;
		}
		// password가 length가 0이면
		if (password.length < 1) {
			alert("パスワードを入力してください。");
			return false;
		}

		// password: 반각 제외한 글자는 deny (java단에도 있음 )
		// 전각->반각 변환이 되 있는 관계로 alert는 안뜰 거으로 예상 
		chkstrPass = escape(gf_Convert2ByteChar2(password));
		if (chkstrPass.indexOf('%') != -1) {
			alert('パスワードは半角英数字でお願いします。');
			return false;
		}

		//　名前: 전각 제외한 글자는 deny (java단에도 있음 )
		chkstrName = escape(name);
		if (chkstrName.indexOf('%') == -1) {
			alert('名前は全角でお願いします。');
			return false;
		}

		form.submit(); // submit
	}

	// 전각 -> 반각 
	function gf_Convert2ByteChar2(x_char) {

		var x_2byteChar = new String;
		var len = x_char.length;
		for (var i = 0; i < len; i++) {
			var c = x_char.charCodeAt(i);

			if (c >= 65281 && c <= 65374 && c != 65340) {
				x_2byteChar += String.fromCharCode(c - 65248);
			} else if (c == 8217) {
				x_2byteChar += String.fromCharCode(39);
			} else if (c == 8221) {
				x_2byteChar += String.fromCharCode(34);
			} else if (c == 12288) {
				x_2byteChar += String.fromCharCode(32);
			} else if (c == 65507) {
				x_2byteChar += String.fromCharCode(126);
			} else if (c == 65509) {
				x_2byteChar += String.fromCharCode(92);
			} else {
				x_2byteChar += x_char.charAt(i);
			}
		}
		return x_2byteChar;
	}
</script>
<title>会員登録</title>
</head>
<body>
	<div class="container form-group">
		
		<form action="register" method="post" id="forms">
			<table class="formTable">
				<tr>
					<td colspan="2"><h1>会員登録</h1></td>
					

				</tr>
				<tr>
					<td>ID</td>
					<td><input class="form-control" type="text" name="uid"
						id="uid"></td>

				</tr>
				<tr>
					<td colspan="2"><span id="checkid" class = "checkid">idを入力してください。</span></td>


				</tr>
				<tr>
					<td>名前</td>
					<td><input class="form-control" type="text" name="name"
						id="name"></td>

				</tr>
				<tr>
					<td>パスワード</td>
					<td><input class="form-control" type="password"
						name="password" id="password"></td>

				</tr>

				<tr>
					<td colspan="2"><input class="btn btn-default" type="button"
						value="submit" onclick="javascript:submitok()"></td>


				</tr>


			</table>
		</form>

		<c:choose>
			<c:when test="${registerResult eq '0'}">
			同じidがあります。
		</c:when>

			<c:when test="${registerResult eq '2'}">
			idは英語と数字で入力してください。
		</c:when>

			<c:when test="${registerResult eq '3'}">
			名前は全角に入力してください。
		</c:when>

			<c:when test="${registerResult eq '4'}"> 
			パスワードは半角に入力してください。
		</c:when>
		</c:choose>

	</div>
</body>
</html>