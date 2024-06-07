<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

        <c:if test="${sessionScope.isAdmin}">
            <form action="/admin/tasks/delete/${task.id}" method="post">
                <button type="submit">Delete</button>
            </form>
        </c:if>
    </div>
</c:forEach>

<c:if test="${sessionScope.isAdmin}">
    <h2>Add New Task</h2>
    <form action="/admin/tasks/add" method="post">
        <div>
            <label for="name">Name:</label>
            <input type="text" id="name" name="name" required>
        </div>
        <div>
            <label for="description">Description:</label>
            <textarea id="description" name="description"></textarea>
        </div>
        <button type="submit">Add Task</button>
    </form>
</c:if>

<form action="/logout" method="post">
    <button type="submit">Logout</button>
</form>
</body>
</html>