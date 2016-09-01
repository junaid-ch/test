<%@page import="customorm.model.BaseModel"%>
<%@page import="java.lang.reflect.Field"%>
<html>
    <head>
        <title>Custom ORM</title>
        <style>
            table, th, td {
                border: 2px solid black;               
            }
            th, td {
                padding: 5px;
            }
            td {
                font-size:120%;
            }
        </style>
    </head>
    <body>

        <% Object obj = request.getAttribute("model"); %>

        <% if (obj instanceof Integer) { %>
        <h3> rows affected: ${model} </h3>
        <% } else {%>
        <h2> ${model['class'].simpleName} Information</h2>        
        <table style="width:50%">

            <%! Field field;
                BaseModel baseModel;
            %>
            <%
                baseModel = (BaseModel) request.getAttribute("model");

                for (Class<?> c = baseModel.getClass(); c != null; c = c.getSuperclass()) {
                    Field[] fields = c.getDeclaredFields();
                    for (Field classField : fields) {
                        field = classField;
            %>
            <tr >
                <td><%= field.getName()%></td>
                <td><%= baseModel.getField(baseModel, field)%></td>

            </tr>
            <%      }
                }
            %>

        </table>
        <%}%>

    </body>
</html>