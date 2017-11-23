package com.gta.chapter2.controller;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by kui.lv on 2017/11/9.
 * 创建客户
 */
public class CustomerCreateServlet extends HttpServlet {


    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     * 进入 创建客户 界面
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }


    /**
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     *
     * 处理 创建客户 请求
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
