<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Tasks</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"></script>
    <script>
        $(function () {
            $("#deadline").datepicker({
                dateFormat: "yy-mm-dd",
                minDate: 0
            });
        });
    </script>
</head>
<body>
<div class="container mt-5">
    <h2>User Tasks</h2>

    <form action="<c:url value='/usertasks/add'/>" method="post" class="mb-4">
        <div class="form-group">
            <label for="task">Task:</label>
            <select name="taskId" id="task" class="form-control">
                <c:forEach items="${tasks}" var="task">
                    <option value="${task.id}">${task.name}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="priority">Priority:</label>
            <input type="number" name="priority" id="priority" class="form-control" min="1" max="5" required>
        </div>
        <div class="form-group">
            <label for="deadline">Deadline:</label>
            <input type="text" name="deadline" id="deadline" class="form-control" placeholder="yyyy-MM-dd" required>
        </div>
        <div class="form-group">
            <label for="status">Status:</label>
            <select name="status" id="status" class="form-control" required>
                <c:forEach items="${statuses}" var="status">
                    <option value="${status}">${status}</option>
                </c:forEach>
            </select>
        </div>
        <div class="form-group">
            <label for="tags">Tags:</label>
            <select name="tagIds" id="tags" class="form-control" multiple>
                <c:forEach items="${tags}" var="tag">
                    <option value="${tag.id}">${tag.name}</option>
                </c:forEach>
            </select>
        </div>
        <button type="submit" class="btn btn-primary">Add Task</button>
    </form>

    <h2>User Tasks List</h2>

    <table class="table table-striped">
        <thead>
        <tr>
            <th>Task Name</th>
            <th>Priority</th>
            <th>Deadline</th>
            <th>Status</th>
            <th>Tags</th>
            <th>Created Date</th>
            <th>Updated Date</th>
            <th>Actions</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${userTasks}" var="userTask">
            <tr id="userTaskRow-${userTask.id}">
                <td>${userTask.taskName}</td>
                <td>${userTask.priority}</td>
                <td>${userTask.deadline}</td>
                <td>${userTask.status}</td>
                <td>
                    <c:forEach items="${userTask.tags}" var="tag">
                        <span class="badge badge-secondary">${tag.name}</span>
                    </c:forEach>
                </td>
                <td>${userTask.createdDate}</td>
                <td>${userTask.updatedDate}</td>
                <td>
                    <form id="deleteTaskForm-${userTask.id}" action="<c:url value='/usertasks/delete/${userTask.id}'/>" method="post" class="d-inline">
                        <button type="submit" class="btn btn-danger">Delete</button>
                    </form>
                    <form action="<c:url value='/usertasks/edit/${userTask.id}'/>" method="get" class="d-inline">
                        <button type="submit" class="btn btn-secondary">Edit</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <form action="<c:url value='/logout'/>" method="post" class="mt-4">
        <button type="submit" class="btn btn-secondary">Logout</button>
    </form>
</div>
</body>
</html>
