<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h2>Nickname:${owner.getName()}</h2>
    <p>Email: ${owner.getEmail()}</p>
    <br>
    <#if owner.isAdmin()>
        <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
           href="/admin">
            Control panel
        </a><br>
    </#if>
    <a class="btn btn-dark mb-3" style="text-align: center; width: 100%"
       href="/my/works">
        My works
    </a>
    <form action="/logout" method="post">
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" style="text-align: center; width: 100%" class="btn btn-danger mb-2">
            Log out
        </button>
    </form>
</@t.template>