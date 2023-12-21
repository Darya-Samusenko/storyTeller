<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h2>Nickname:${owner.getName()}</h2>
    <p>Email: ${owner.getEmail()}</p>
</@t.template>