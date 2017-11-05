<#assign base = request.contextPath /> 
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title></title>
    </head>
    <body>
        <div id="app">
            {{ message }} 
            <br/>
            ${that}
        </div>
    </body>
    <script src="${base}/js/lib/vue/vue.js"></script>
    <script>
        var exampleData = {
            message: 'Hello World! 121'
        }
        new Vue({
            el: '#app',
            data: exampleData
        })
    </script>
</html