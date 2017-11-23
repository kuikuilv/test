package com.gta.chapter2.controller;

import com.gta.chapter2.helper.DatabaseHelper;
import com.gta.chapter2.model.Customer;
import com.gta.chapter2.service.CustomerService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by kui.lv on 2017/11/22.
 */
@WebServlet("/test")
public class TestServlet extends HttpServlet {
    private CustomerService customerService;


    @Override
    public void init() throws ServletException {
        customerService = new CustomerService();
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        //res.getWriter().print("hello");
        List<Customer> customerList = customerService.getCustomerList();
        req.setAttribute("customerList",customerList);
        req.getRequestDispatcher("/WEB-INF/view/customer.jsp").forward(req,res);
    }

}
