<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="annexe.Annexe" %>
<%
    Annexe[] annexes = (Annexe[]) request.getAttribute("axes");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/bootstrap.css">
    <title>Critere</title>
</head>
<body>
    <h2 class="text-center mt-5">Vos critere important de match</h2>
    <div class="container mt-5 w-50">
        <form action="insert-critere" method="post">
            <% for (Annexe axe : annexes) { %>
            <div class="mt-3">
                <label for="<% out.print(axe.getNom()); %>" class="form-label"><% out.print(axe.getNom()); %></label>
                <input type="text" class="form-control" name="<% out.print(axe.getNom()); %>" id="<% out.print(axe.getNom()); %>" placeholder="/10">
            </div>
            <% } %>
            <div class="row mt-4">
                <input type="submit" value="OK" class="btn btn-primary mt-3">
            </div>
            <div class="row mt-4">
                <a href="index" class="btn btn-primary">Back to Login</a>
            </div>
        </form>
    </div>
</body>
<script src="./assets/js/bootstrap.min.js"></script>
<script src="./assets/js/jquery.min.js"></script>
</html>