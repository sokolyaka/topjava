<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<link rel="stylesheet" href="webjars/datatables/1.10.12/css/jquery.dataTables.min.css">

<body>
<jsp:include page="fragments/bodyHeader.jsp"/>

<div class="jumbotron">
    <div class="container">
        <div class="shadow">
            <h3><fmt:message key="meals.title"/></h3>

            <form class="form" method="get" id="filterForm">
                <div class="form-group">
                    <label for="fromDate" class="control-label col-xs-3">From Date:</label>

                    <div class="col-xs-3">
                        <input type="date" class="form-control" name="fromDate" id="fromDate">
                    </div>
                </div>

                <div class="form-group">
                    <label for="toDate" class="control-label col-xs-3">To Date:</label>

                    <div class="col-xs-3">
                        <input type="date" class="form-control" name="toDate" id="toDate">
                    </div>
                </div>

                <div class="form-group">
                    <label for="fromTime" class="control-label col-xs-3">From Time:</label>

                    <div class="col-xs-3">
                        <input type="time" class="form-control" name="fromTime" id="fromTime">
                    </div>
                </div>

                <div class="form-group">
                    <label for="toTime" class="control-label col-xs-3">To Time:</label>

                    <div class="col-xs-3">
                        <input type="time" class="form-control" name="toTime" id="toTime">
                    </div>
                </div>

                <button class="btn btn-primary" type="submit"><fmt:message key="meals.filter"/></button>
            </form>

            <hr>
            <a class="btn btn-sm btn-info" id="add"><fmt:message key="meals.add"/></a>
            <hr>
            <table class="table table-striped display" id="datatable">
                <thead>
                <tr>
                    <th>Date</th>
                    <th>Description</th>
                    <th>Calories</th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${mealList}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.UserMealWithExceed"/>
                    <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a class="btn btn-xs btn-primary edit" id="${meal.id}">Update</a></td>
                        <td><a class="btn btn-xs btn-danger delete" id="${meal.id}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

<div class="modal fade" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h2 class="modal-title"><fmt:message key="meals.edit"/></h2>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" method="post" id="detailsForm">
                    <input type="text" hidden="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="datetime" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="datetime-local" class="form-control" name="datetime" id="datetime">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="description" class="control-label col-xs-3">Description</label>

                        <div class="col-xs-9">
                            <input type="text" class="form-control" name="description" id="description"
                                   placeholder="description">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="calories" class="control-label col-xs-3">Date</label>

                        <div class="col-xs-9">
                            <input type="number" class="form-control" name="calories" id="calories"
                                   placeholder="calories">
                        </div>
                    </div>

                    <div class="form-group">
                        <div class="col-xs-offset-3 col-xs-9">
                            <button type="submit" class="btn btn-primary">Save</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
<script type="text/javascript" src="webjars/jquery/2.2.4/jquery.min.js"></script>
<script type="text/javascript" src="webjars/bootstrap/3.3.6/js/bootstrap.min.js"></script>
<script type="text/javascript" src="webjars/datatables/1.10.12/js/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="webjars/noty/2.3.8/js/noty/packaged/jquery.noty.packaged.min.js"></script>
<script type="text/javascript" src="resources/js/datatablesUtil.js"></script>
<script type="text/javascript">


    var ajaxUrl = 'ajax/meals/';
    var mealsUrl = 'ajax/meals/';
    var datatableApi;
    var filterForm;

    $(function () {
        datatableApi = $('#datatable').dataTable({
            "bPaginate": false,
            "bInfo": false,
            "aoColumns": [
                {"mData": "dateTime"},
                {"mData": "description"},
                {"mData": "calories"},
                {
                    "sDefaultContent": "edit",
                    "bSortable": false
                },
                {
                    "sDefaultContent": "delete",
                    "bSortable": false
                }
            ],
            "aaSortable": [
                0,
                "asc"
            ]
        });
        makeEditable();

        $('#filterForm').submit(function () {
            debugger;
            ajaxUrl = mealsUrl + 'filter?'+$('#filterForm').serialize();
            updateTable();
            return false;
        })
    })


</script>
</html>
