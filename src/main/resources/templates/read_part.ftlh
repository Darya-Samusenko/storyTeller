<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1>${work}</h1>
    <h2>${part_name}</h2>
    <p>${part_content}</p>
    <p><a href="/work/${back_ref}">back to work</a></p>
</@t.template>