<#import "blocks/template.ftlh" as t>
<@t.template user>
    <h1 style="text-align: center">Add part to work</h1>
    <form action="/work/${destWork.id_work}/add_part" method="post" enctype="multipart/form-data">
        <div class="form-group">
            <label for="partName">Title</label>
            <input type="text" required class="form-control"
                   id="partName" name="title_part">
        </div>
        <div class="form-group">
            <label for="partContent">Content of part</label>
            <textarea id="partContent" name="content" style="width:100%;height:400px;"></textarea>
        </div>
        <input type="hidden" name="_csrf" value="${_csrf.token}"/>
        <button type="submit" style="width: 100%" class="btn btn-dark">Add part</button>
    </form>
</@t.template>