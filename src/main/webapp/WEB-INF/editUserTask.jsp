<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User Task</title>
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
    <h2 class="mb-4">Edit User Task</h2>
    <form action="<c:url value='/usertasks/update'/>" method="post">
        <input type="hidden" name="user.id" value="${userTask.user.id}" />
        <input type="hidden" name="id" value="${userTask.id}" />

        <div class="form-group">
            <label for="task">Task:</label>
            <select name="taskId" id="task" class="form-control">
                <c:forEach items="${tasks}" var="task">
                    <option value="${task.id}" <c:if test="${task.id == userTask.task.id}">selected</c:if>>${task.name}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="priority">Priority:</label>
            <input type="number" name="priority" id="priority" class="form-control" min="1" max="5" value="${userTask.priority}" required>
        </div>

        <div class="form-group">
            <label for="deadline">Deadline:</label>
            <input type="text" name="deadline" id="deadline" class="form-control" placeholder="yyyy-MM-dd" value="${userTask.deadline}" required>
        </div>

        <div class="form-group">
            <label for="status">Status:</label>
            <select name="status" id="status" class="form-control" required>
                <c:forEach items="${statuses}" var="status">
                    <option value="${status}" <c:if test="${status == userTask.status}">selected</c:if>>${status}</option>
                </c:forEach>
            </select>
        </div>

        <div class="form-group">
            <label for="tags">Tags:</label>
            <select name="tagIds" id="tags" class="form-control" multiple required>
                <c:forEach items="${tags}" var="tag">
                    <option value="${tag.id}" <c:if test="${userTask.task.tags.contains(tag)}">selected</c:if>>${tag.name}</option>
                </c:forEach>
            </select>
        </div>

        <button type="submit" class="btn btn-primary btn-block">Update Task</button>
    </form>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.4/dist/umd/popper.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
