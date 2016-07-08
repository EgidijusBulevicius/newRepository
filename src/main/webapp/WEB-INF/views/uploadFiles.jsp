<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page session="false" %>
<html>
<head>
<title>Upload File Request Page</title>
</head>
<body>
	<form method="POST" action="uploadMultipleFile" enctype="multipart/form-data">
		Prisegamas failas: <input type="file" name="file"><br>
		Prisegamas failas: <input type="file" name="file"><br>
		Prisegamas failas: <input type="file" name="file"><br>
		Prisegamas failas: <input type="file" name="file"><br> 	
		<input type="submit" value="Ikelti"> Ikelti pasirinktus failus!
	</form>	
</body>
</html>