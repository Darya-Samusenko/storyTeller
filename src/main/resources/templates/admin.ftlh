<#import "blocks/template.ftlh" as t>
<@t.template user>
    `<h1 style="text-align: center">Administrator panel</h1><hr>
    <table class="table table-hover table-dark">
        <tr>
            <th scope="col">Email</th>
            <th scope="col">Activity</th>
            <th scope="col">Roles</th>
            <th scope="col">Banned</th>
            <th scope="col">Redact information</th>
            <th scope="col">More info</th>
        </tr>
        <#list users as user>
            <tr>
                <th>${user.email}</th>
                <th><#if user.active>true<#else>false</#if></th>
                <th><#list user.roles as role>${role}</#list></th>
                <th>
                    <form action="/admin/user/ban/${user.id}" method="post">
                        <input type="hidden" name="_csrf" value="${_csrf.token}">
                        <input type="submit" class="btn btn-light" value="Бан">
                    </form>
                </th>
                <th><a href="/admin/user/edit/${user.id}">Redact</a></th>
                <th><a href="/user/${user.id}">More...</a></th>
            </tr>
        <#else>
            <h3>No users found</h3>
        </#list>
    </table>
</@t.template>