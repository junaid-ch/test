<%@page import="java.lang.reflect.Field"%>
<%@page import="customorm.model.BaseModel"%>

<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
    <head>
        <title>
            <% out.print(request
                        .getAttribute("baseModel")
                        .getClass()
                        .getSimpleName());
            %> - Custom ORM
        </title>
    </head>
    <body>
        <form:form method="POST" action="" modelAttribute="baseModel" style="text-align:center">
            <table>
                <%! Field field;%>
                <%
                    BaseModel baseModel = (BaseModel) request.getAttribute("baseModel");

                    for (Class<?> c = baseModel.getClass(); c != null; c = c.getSuperclass()) {
                        Field[] fields = c.getDeclaredFields();
                        for (Field classField : fields) {
                            field = classField;
                %>

                <tr>
                    <td><form:label path='<%=field.getName()%>'>
                            <%= field.getName().toUpperCase()%>
                        </form:label></td>
                    <td><form:input path="<%=field.getName()%>" /></td>
                </tr>
                <%      }
                    }
                %>
                <tr>
                    <td colspan="2">
                        <input type="submit" value="Add" onclick="this.form.action = '/CustomORM/add';">
                        <input type="submit" value="update" onclick="this.form.action = '/CustomORM/update';">
                        <input type="submit" value="delete" onclick="this.form.action = '/CustomORM/delete';">
                        <input type="submit" value="view" onclick="this.form.action = '/CustomORM/view';">
                    </td>
                </tr>
            </table>  
        </form:form>
    </body>
</html>