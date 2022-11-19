<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="formulaire.Formulaire" %>
<%@ page import="user.User" %>
<%
    Formulaire form = Formulaire.createFormulaire(new User());
    form.getListeChamp()[0].setVisible(false, "");
    form.getListeChamp()[2].setType("password");
    form.getListeChamp()[3].changeToDrop(new String[] {"feminin", "masculin"}, new String[] {"feminin", "masculin"});
    form.getListeChamp()[4].setVisible(false, "");
    form.getListeChamp()[5].setVisible(false, "");
    form.getListeChamp()[6].setVisible(false, "");
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="./assets/css/bootstrap.css">
    <title>Inscription</title>
</head>
<body>
    <div class="container w-50" style="margin-top: 160px;">
        <h2 class="text-center mb-5">Inscription</h2>
        <% out.print(form.getHTMLString("inscription", "post")); %>
        <div class="row mt-4">
            <a href="index" class="btn btn-primary">Back to Login</a>
        </div>
    </div>
</body>
<script src="./assets/js/bootstrap.min.js"></script>
<script src="./assets/js/jquery.min.js"></script>
</html>