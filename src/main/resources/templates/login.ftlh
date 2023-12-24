<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Authorization</h1>
    <hr>
    <form action="/login " method="post">
        <div class="form-group">
            <label for="email">Email</label>
            <input type="email" class="form-control" id="email" name="nickname">
            <div id="email" class="invalid-feedback">
                Invalid email
            </div>
        </div>
        <div class="form-group">
            <label for="password">Password</label>
            <!--убрала if, т.к. не RequestParameters передается в модель, надо его обратно грамотно вставить,
            чтобы при авторизации было сообщение invalid password итд-->
            <input type="password" class="form-control " id="password" name="password">
            <div id="password" class="invalid-feedback">
                Invalid password
            </div>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}">
        <button type="submit" style="width: 100%" class="btn btn-dark">Log in</button>
    </form>
    <hr>
    <h2>Don't have an account? <a href="/registration">Register here and read works for free</a></h2>
</@t.template>