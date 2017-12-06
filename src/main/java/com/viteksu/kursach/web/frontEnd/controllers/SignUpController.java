package com.viteksu.kursach.web.frontEnd.controllers;

import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SignUpController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);
        resp.sendRedirect("http://localhost:8080/OOP");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");

        String login = req.getParameter("login");
        String pass = req.getParameter("password");


        FrontEndService frontEndService = AddressService.getInstance().getFrontEnd();

        frontEndService.register(login, pass, req.getSession().getId());

        boolean result = frontEndService.isRegistered(login);

        if (result) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
        resp.sendRedirect("/OOP");

    }
}