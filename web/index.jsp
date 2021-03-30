<%@page import="java.util.ArrayList"%>
<%@page import="com.emergentes.modelo.Nota"%>
<%
    if (session.getAttribute("listanot") == null){
        ArrayList<Nota> la = new ArrayList<Nota>();
        session.setAttribute("listanot", la);
    }
    ArrayList<Nota> lista = (ArrayList<Nota>) session.getAttribute("listanot");
%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Listado de personas</h1>
        <a href="MainServlet?op=nuevo">Nuevo registro</a>
        <table border="1">
            <tr>
                <th>Id</th>
                <th>Hora</th>
                <th>Actividad</th>
                <th>Completado</th>
                <th></th>
                <th></th>
            </tr>
            <%
            if (lista != null){
                for (Nota item : lista){            
            %>
            <tr>
                <td><%= item.getId() %></td>
                <td><%= item.getHora() %></td>
                <td><%= item.getActividad() %></td>
                <td><%= item.getCompletado() %></td>
                <td><a href="MainServlet?op=editar&id=<%= item.getId() %>">Editar</a></td>
                <td><a href="MainServlet?op=eliminar&id=<%= item.getId() %>"
                       onclick="return(confirm('Esta seguro de eliminar??'))"
                       >Eliminar</a></td>
            </tr>
            <%
                }
            }
            %>
        </table>
    </body>
</html>