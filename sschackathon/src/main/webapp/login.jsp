<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.supersmashcoders.entities.UserEntity" %>
<%@ page import="com.googlecode.objectify.ObjectifyService" %>
<%@ page import="java.util.List" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <link type="text/css" rel="stylesheet" href="/stylesheets/main.css"/>
    </head>
    
    <body>
    
        <h1>Register new user!</h1>
        
        
        <%
        
        List<UserEntity> Users = ObjectifyService.ofy()
            .load()
            .type(UserEntity.class) // We want only UserEntities
            .limit(5)
            .list();

        if (Users.isEmpty()) {
            %>
            <p>There are no users.</p>
            <%
        } else {
            %>
            <p>Existing Users:</p>
            <%
            for (UserEntity user : Users) {
                pageContext.setAttribute("user_name", user.getUsername());
                pageContext.setAttribute("user_bio", user.getBio());
                %>
                <p><b>${fn:escapeXml(user_name)}</b> wrote:</p>
                <blockquote>${fn:escapeXml(user_bio)}</blockquote>
                <%
            }
        }
        %>
        
        <form action="/register" method="post">
            <div>Username: <input type="text" name="username" value="${fn:escapeXml(guestbookName)}"/></div>
            <div>Password: <input type="text" name="password" value="${fn:escapeXml(guestbookName)}"/></div>
            <div>Bio: <input type="text" name="bio" value="${fn:escapeXml(guestbookName)}"/></div>
            <div><input type="submit" value="Register"/></div>
        </form>
    
        
    </body>
</html>