<%@ page import="formulaire.Formulaire" %>
<%@ page import="user.User" %>
<%
    Formulaire form = Formulaire.createFormulaire(new User());
    form.getListeChamp()[0].setVisible(false, "");
    form.getListeChamp()[2].setType("password");
    form.getListeChamp()[3].setVisible(false, "");
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
    <title>Login</title>
</head>
<body>
    <div class="container w-50" style="margin-top: 200px;">
        <h2 class="text-center mb-5">Login</h2>
        <% out.print(form.getHTMLString("login", "post")); %>
        <div class="row mt-4">
            <a href="inscription.jsp" class="btn btn-primary">S'inscrire</a>
        </div>
    </div>
</body>
<script src="./assets/js/bootstrap.min.js"></script>
<script src="./assets/js/jquery.min.js"></script>
</html>