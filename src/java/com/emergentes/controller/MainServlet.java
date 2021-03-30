package com.emergentes.controller;

import com.emergentes.modelo.Nota;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "MainServlet", urlPatterns = {"/MainServlet"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String opcion = request.getParameter("op");
        Nota objper = new Nota();
        int id, pos;
        HttpSession ses = request.getSession();
        List<Nota> lista = (List<Nota>) ses.getAttribute("listanot");
        switch (opcion){
            case "nuevo":
                request.setAttribute("miobjper", objper);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "editar":
                id = Integer.parseInt(request.getParameter("id"));
                pos = buscarPorIndice(request, id);
                objper = lista.get(pos);
                request.setAttribute("miobjper", objper);
                request.getRequestDispatcher("editar.jsp").forward(request, response);
                break;
            case "eliminar":
                id = Integer.parseInt(request.getParameter("id"));
                pos = buscarPorIndice(request, id);
                if (pos >= 0){
                    lista.remove(pos);
                }
                request.setAttribute("listanot", lista);
                response.sendRedirect("index.jsp");
            break;
            
            default:
                request.setAttribute("listanot", lista);
                response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        HttpSession ses = request.getSession();
        ArrayList<Nota> lista = (ArrayList<Nota>) ses.getAttribute("listanot");
        Nota objper = new Nota();
        objper.setId(id);
        objper.setHora(request.getParameter("hora"));
        objper.setActividad(request.getParameter("actividad"));
        objper.setCompletado(request.getParameter("completado"));
        System.out.println("El ID es " + id);
        if (id == 0){
            int idNuevo = obtenerId(request);
            objper.setId(idNuevo);
            
            lista.add(objper);
            System.out.println(objper.toString());
        }else{
            int pos = buscarPorIndice(request, id);
            lista.set(pos, objper);
            System.out.println(objper.toString());
        }
        System.out.println("Enviando al index");
        request.setAttribute("listanot", lista);
        response.sendRedirect("index.jsp");
    }
    
    public int buscarPorIndice(HttpServletRequest request, int id){
        HttpSession ses = request.getSession();
        List<Nota> lista = (List<Nota>) ses.getAttribute("listanot");
        
        int pos = -1;
        
        if(lista != null){
            for (Nota ele : lista){
                ++pos;
                if (ele.getId() == id){
                    break;
                }
            }
        }
        return pos;
    }
    
    public int obtenerId(HttpServletRequest request){
        HttpSession ses = request.getSession();
        ArrayList<Nota> lista = (ArrayList<Nota>) ses.getAttribute("listanot");
        
        int idn = 0;
        for (Nota ele : lista){
            idn = ele.getId();
        }
        return idn + 1;
    }

}
