<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Bootstrap Example</title>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.0/jquery.min.js"></script>
<script
	src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.6/js/bootstrap.min.js"></script>
</head>
<body>

	<div class="container">
		<h2>Form control: checkbox</h2>
		<p>The form below contains three checkboxes. The last option is
			disabled:</p>
		<form role="form">
			<div class="checkbox">
				<label><input type="checkbox" value="">Option 1</label>
			</div>
			<div class="checkbox">
				<label><input type="checkbox" value="">Option 2</label>
			</div>
			<div class="checkbox disabled">
				<label><input type="checkbox" value="" disabled>Option
					3</label>
			</div>
		</form>
	</div>




	<div class="container">
		<h2>Form control: radio buttons</h2>
		<p>The form below contains three radio buttons. The last option is
			disabled:</p>
		<form role="form">
			<div class="radio">
				<label><input type="radio" name="optradio">Option 1</label>
			</div>
			<div class="radio">
				<label><input type="radio" name="optradio">Option 2</label>
			</div>
			<div class="radio disabled">
				<label><input type="radio" name="optradio" disabled>Option
					3</label>
			</div>
		</form>
	</div>
	<c:forEach items="${activityAnswers}" var="activityAnswer">
		${activityAnswer}
	</c:forEach>

</body>
</html>