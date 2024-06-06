<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User Task</title>
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

<h2>Edit User Task</h2>

<form action="<c:url value='/usertasks/update'/>" method="post">
    <input type="hidden" name="user.id" value="${userTask.user.id}" />
    <input type="hidden" name="id" value="${userTask.id}" />
    <div>
        <label for="task">Task:</label>
        <select name="taskId" id="task">
            <c:forEach items="${tasks}" var="task">
                <option value="${task.id}" ${task.id == userTask.task.id ? 'selected' : ''}>${task.name}</option>
            </c:forEach>
        </select>

    </div>
    <div>
        <label for="priority">Priority:</label>
        <input type="number" name="priority" id="priority" min="1" max="5" value="${userTask.priority}" required>
    </div>
    <div>
        <label for="deadline">Deadline:</label>
        <input type="text" name="deadline" id="deadline" placeholder="yyyy-MM-dd"  readonly>
    </div>
    <div>
        <label for="status">Status:</label>
        <select name="status" id="status" required>
            <c:forEach items="${statuses}" var="status">
                <option value="${status}" ${status == userTask.status ? 'selected' : ''}>${status}</option>
            </c:forEach>
        </select>
    </div>
    <button type="submit">Update Task</button>
</form>

</body>
</html>