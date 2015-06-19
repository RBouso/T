<%-- 
    Document   : index
    Created on : 09-jun-2015, 9:37:25
    Author     : raquel
--%>

<%@page import="Transportes.Controlador.Dominio.Controlador"%>
<%@page import="Transportes.Controlador.Dominio.ControladorExtraccion"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Servidor de transportes</title>
    </head>
    <body>
        <h1>Servidor conectado</h1>
        <%
           //Extraer e integrar datos
           boolean b = true;
           while (b)  {
               Controlador c = new ControladorExtraccion();
               c.executar();
           }
        %>
    </body>
</html>
