<#import "blocks/template.ftlh" as t>
<@t.template userByPrincipal>
    <h1 style="text-align: center">User information</h1>
    <hr>
    <div style="text-align: center">
        <br>
        <h2>${user.name}</h2>
        <br>
        Email: <b>${user.getEmail()}</b>
    </div>
    <br>
    <hr>
    <h1 style="text-align: center">Works of ${user.name}</h1>
    <br>
    <div class="card-columns">
        <#list products as product>
            <a href="/work/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <div class="card-body">
                        <h5 class="card-title">${product.getTitle()}</h5>
                    </div>
                    <div class="card-footer">
                        ${product.getAuthorName()}
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Not found</h1>
        </#list>
    </div>
</@t.template>