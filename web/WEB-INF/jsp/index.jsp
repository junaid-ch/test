<%@page import="org.springframework.beans.factory.config.BeanDefinition"%>
<%@page import="java.util.Set"%>
<%@page import="org.springframework.core.type.filter.RegexPatternTypeFilter"%>
<%@page import="java.util.regex.Pattern"%>
<%@page import="org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
    "http://www.w3.org/TR/html4/loose.dtd">

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Custom ORM</title>
    </head>

    <body>
        <form:form method="GET" action="">
            <%! String entityName;%>
            <%
                // create scanner and disable default filters (that is the 'false' argument)
                final ClassPathScanningCandidateComponentProvider provider = new ClassPathScanningCandidateComponentProvider(false);
                // add include filters which matches all the classes (or use your own)
                provider.addIncludeFilter(new RegexPatternTypeFilter(Pattern.compile(".*")));

                // get matching classes defined in the package
                final Set<BeanDefinition> classes = provider.findCandidateComponents("customorm.model");

                for (BeanDefinition bean : classes) {
                    if (!bean.getBeanClassName().contains("BaseModel")
                            && !bean.getBeanClassName().contains("ModelFactory")) {

                        entityName = bean.getBeanClassName().substring(bean
                                .getBeanClassName()
                                .lastIndexOf(".") + 1);
            %>
            <input type="submit" value="<%= entityName%>" onclick="this.form.action = '/CustomORM/<%= entityName%>';">
            <%
                    }
                }
            %> 
        </form:form>
    </body>
</html>
