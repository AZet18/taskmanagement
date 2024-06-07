<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tasks</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-4">
    <h1 class="mb-4">Tasks</h1>

    <div class="row">
        <c:forEach items="${tasks}" var="task">
            <div class="col-md-4 mb-3">
                <div class="card">
                    <div class="card-body">
                        <h5 class="card-title">${task.name}</h5>
                        <p class="card-text">${task.description}</p>
                        <c:if test="${sessionScope.isAdmin}">
                            <form action="/admin/tasks/delete/${task.id}" method="post" class="d-inline">
                                <button type="submit" class="btn btn-danger btn-sm">Delete</button>
                            </form>
                        </c:if>
                    </div>
                </div>
            </div>
        </c:forEach>
    </div>

    <c:if test="${sessionScope.isAdmin}">
        <form action="/admin/tasks/add" method="get" class="mt-4">
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