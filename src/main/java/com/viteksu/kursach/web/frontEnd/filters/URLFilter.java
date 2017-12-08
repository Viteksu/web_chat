package com.viteksu.kursach.web.frontEnd.filters;

import com.viteksu.kursach.core.Loader;
import com.viteksu.kursach.core.parameters.PropertyChecker;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class URLFilter extends HttpFilter {
    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain) throws IOException, ServletException {
        PropertyChecker propertyChecker = Loader.getInstance().getPropertyChecker(getServletContext());
        if (propertyChecker.isURI(req.getRequestURI())) {
            chain.doFilter(req, res);
        } else {
            res.sendRedirect("/OOP/error-page.html");
        }
    }
}
