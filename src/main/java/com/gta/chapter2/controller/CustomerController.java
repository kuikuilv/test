package com.gta.chapter2.controller;

import com.gta.chapter2.model.Customer;
import com.gta.chapter2.service.CustomerService;
import org.springframework.stereotype.Controller;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.tags.Param;

import javax.inject.Inject;
import javax.xml.ws.Action;
import java.util.List;


/**
 * Created by kui.lv on 2017/11/24.
 */
@Controller
public class CustomerController {

    @Inject
    private CustomerService customerService;

    @Action("get:/customer")
    public View index(Param param){
        List<Customer> customerList = customerService.getCustomerList();
        return new View("customer.jsp")
    }



}
