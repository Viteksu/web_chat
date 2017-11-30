package com.viteksu.kursach.web.frontEnd.controllers;

import com.viteksu.kursach.core.messageSystem.addressService.AddressService;
import com.viteksu.kursach.web.frontEnd.FrontEndService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class WebSocketController extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setStatus(HttpServletResponse.SC_OK);
        req.getRequestDispatcher("/chat.jsp").forward(req, resp);
    }
}
