package com.viteksu.kursach.web.frontEnd.controllers;

import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.web.backEnd.accounts.UserProfile;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintStream;

public class SignInController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_TEMPORARY_REDIRECT);

        resp.sendRedirect("http://localhost:8080/OOP");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String login = req.getParameter("login");
        String pass = req.getParameter("password");

        PrintStream printStream = System.err;

        printStream.println("Front end getting");
        FrontEndService frontEndService = AddressService.getInstance().getFrontEnd();

        printStream.println("Front end auth begin");
        frontEndService.authenticate(login, pass);
        printStream.println("Front end auth stop");

        printStream.println("Getting userProfik");
        UserProfile userProfile = frontEndService.isAuthenticated(login);

        System.err.println("If checking");
        if (userProfile != null && userProfile.getLogin().equals(login)) {
            System.err.println("If - true");
            resp.setStatus(HttpServletResponse.SC_OK);
            req.getSession().setAttribute("user", userProfile);
            System.err.println("Redirect to caht");
            resp.sendRedirect("/OOP/chat");
        } else {
            System.err.println("If - false");
            resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            resp.getWriter().write("UNAUTHORIZED");
        }

    }
}
