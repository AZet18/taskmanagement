<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tasks</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h1 class="mb-4">Tasks</h1>

    <div class="list-group">
        <c:forEach items="${tasks}" var="task">
            <div class="list-group-item">
                <h2 class="h5">${task.name}</h2>
                <p>${task.description}</p>

                <c:if test="${sessionScope.isAdmin}">
                    <form action="/admin/tasks/delete/${task.id}" method="post" class="d-inline">
                        <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                    </form>
                </c:if>
            </div>
        </c:forEach>
    </div>

    <c:if test="${sessionScope.isAdmin}">
        <h2 class="mt-5">Add New Task</h2>
        <form action="/admin/tasks/add" method="post">
            <div class="form-group">
                <label for="name">Name:</label>
                <input type="text" id="name" name="name" class="form-control" required>
            </div>
            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" class="form-control"></textarea>
            </div>
            <button type="submit" class="btn btn-primary">Add Task</button>
        </form>
    </c:if>

    <form action="/logout" method="post" class="mt-4">
        <button type="submit" class="btn btn-secondary">Logout</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
