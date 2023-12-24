<!doctype html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
    <head>
        <!-- Required meta tags -->
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
        <link rel="stylesheet" href="/static/css/style.css">

        <!-- Bootstrap CSS -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css"
              integrity="sha384-zCbKRCUGaJDkqS1kPbPd7TveP5iyJE0EjAuZQTgFLD2ylzuqKfdKlfG/eSrtxUkn"
              crossorigin="anonymous">

        <title>GreatStoryteller</title>
    </head>
    <body>
    <nav class="navbar navbar-dark bg-dark">
        <div class="container-fluid">
            <a href="/">
                <span class="navbar-brand mb-0 h1">GreatStoryteller</span>
            </a>
            <#if user.email??>
                <div class="dropdown text-end">
                    <a href="/profile">
                        go to profile
                    </a>
                </div>
            <#else>
                <button class="btn btn-light my-2 my-sm-0" type="button"
                        onclick="window.location.href = '/login'">
                    Log in
                </button>
            </#if>
        </div>
    </nav>
    <div class="container mt-4">
        <p><h1 style="text-align: center">${work.getTitle()}</h1></p>
        <p><h2 style="text-align: center">${authorWork}</h2></p>
        <p><h2 style="text-align: center">${work.getDescription()}</h2></p>
        <!--сюда вписать как-то части работы (заголовки + ссылки на часть)
        есть проблема со списком, надо каким-то образом вставить thymeleaf без html-тэга или переписать страницу
        без шаблона-->
        <h1>Content</h1>
        <ul>
            <li th:each="part : ${parts}">
                <span th:text="${part.getName_part()}"></span>
            </li>
        </ul>
    </div>

    <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
            integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.3/umd/popper.min.js"
            integrity="sha384-ZMP7rVo3mIykV+2+9J3UJ46jBk0WLaUAdn689aCwoqbBJiSnjAK/l8WvCWPIPm49"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"
            integrity="sha384-smHYKdLADwkXOn1EmN1qk/HfnUcbVRZyYmZ4qpPea6sjB/pTJ0euyQp0Mk8ck+5T"
            crossorigin="anonymous"></script>
    </body>
</html>