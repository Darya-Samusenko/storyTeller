<#import "blocks/template.ftlh" as t>
    <!----переписать под работы, а не телефоны + это главная страница---->
<@t.template user>
    <h1 style="text-align: center">Work search</h1><hr>
    <form method="get" action="/">
        <div class="form-row">
            <div class="form-group col-md-10">
                <label for="searchWord">Search:</label>
                <input type="text" name="searchWord" <#if searchWord??>value="${searchWord}"</#if> class="form-control"
                       id="searchWord">
            </div>
            <button style="width: 100%" type="submit" class="btn btn-dark">Search</button>
        </div>
    </form>
    <hr>
    <div class="card-columns">
        <#list works as product>
            <a href="/work/${product.id}" class="product-link">
                <div class="card text-white bg-dark my-3 product">
                    <div class="card-body">
                        <h5 class="card-title">${product.getTitle()}</h5>
                        <p class="card-text">Author: ${product.getAuthorName()}</p>
                    </div>
                </div>
            </a>
        <#else>
            <h1 style="text-align: center">Not found</h1>
        </#list>
    </div>
</@t.template>