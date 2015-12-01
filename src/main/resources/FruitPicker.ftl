<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Fruit Picker</title>
</head>
<body>
    <h1>Hello</h1>

    <form action="/favorite_fruit" method="POST">
    	<#list fruits as fruit>
    		<p>
    			<input type="radio" name="fruit" value="${fruit}">${fruit}</input>
    		</p>
    	</#list>
		<input type="submit" value="Submit"/>
    </form>

</body>
</html>