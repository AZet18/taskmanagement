<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Tasks</title>
</head>
<body>
<h1>Tasks</h1>

<c:forEach items="${tasks}" var="task">
    <div>
        <h2>${task.name}</h2>
        <p>${task.description}</p>
        <p><strong>Priority:</strong> ${task.taskDetails.priority}</p>
        <p><strong>Deadline:</strong> ${task.taskDetails.deadline}</p>
        <p><strong>Status:</strong> ${task.taskDetails.status}</p>
        <p><strong>Created Date:</strong> ${task.taskDetails.createdDate}</p>
        <p><strong>Updated Date:</strong> ${task.taskDetails.updatedDate}</p>
    </div>
</c:forEach>
<form action="/logout" method="post">
    <button type="submit">Logout</button>
</form>
</body>
</html>