<#import "blocks/template.ftlh" as t>
<@t.template user>
    <p><h1 style="text-align: center">${product.title}</h1></p>
    <p><h2 style="text-align: center">${authorProduct}</h2></p>
    <p style="text-align: center">${product.description}</p>
    <!--сюда вписать как-то части работы (заголовки + ссылки на часть)  -->
</@t.template>