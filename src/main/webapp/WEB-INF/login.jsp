<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login</title>

</head>
<body>
<div class="container">
    <h2>Login</h2>
    <form:form method="post" modelAttribute="user">
        <div class="form-group">
            <label for="email">Email:</label>
            <form:input path="email" type="text" id="email" required="required"/>
        </div>
        <div class="form-group">
            <label for="password">Password:</label>
            <form:password path="password" id="password" required="required"/>
        </div>
        <button type="submit">Login</button>
    </form:form>
</div>
</body>
</html>