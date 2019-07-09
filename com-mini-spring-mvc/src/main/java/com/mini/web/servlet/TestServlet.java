package com.mini.web.servlet;

import com.mini.web.view.ViewFreemarker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@WebServlet(name = "TestServlet", urlPatterns = "/test")
public class TestServlet extends HttpServlet {
    private static final long serialVersionUID = -7609207829977577129L;

    @Autowired
    private ViewFreemarker viewFreemarker;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("----------------doGet-----------------------------------");
        System.out.println("viewFreemarker: " + viewFreemarker);
        System.out.println(this);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("----------------doPost-----------------------------------");
        System.out.println("viewFreemarker: " + viewFreemarker);
        System.out.println(this);
    }
}
