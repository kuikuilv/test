package com.gta.chapter2.test;

import com.gta.chapter2.helper.DatabaseHelper;
import com.gta.chapter2.model.Customer;
import com.gta.chapter2.service.CustomerService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * CastomerService单元测试类
 * Created by kui.lv on 2017/11/9.
 */
public class CustomerServiceTest {

    private CustomerService customerService;

    public CustomerServiceTest(){

    }

    @Before
    public void init() throws Exception {
        /*String file ="sql/customer_init.sql";
        InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream(file);
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        String sql;
        while (( sql =reader.readLine()) != null){
            DatabaseHelper.executeUpdate(sql);
        }*/
        customerService=new CustomerService();
        DatabaseHelper.exeuteSqlFile("sql/customer_init.sql");
    }

    @Test
    public void getCustomerListTest() throws Exception{
//        List<Customer> customerList=customerService.getCustomerList();
//        Assert.assertEquals(2,customerList.size());
//        System.out.print(customerList.size());


        String sql = "SELECT * FROM customer";
        List<Customer> list = DatabaseHelper.queryEntityList(Customer.class, sql);
        System.out.println(list.size());
    }

    @Test
    public void getCustomerTest() throws  Exception{
        long id = 1;
        Customer customer=customerService.getCustomer(id);
        Assert.assertNotNull(customer);
    }


    @Test
    public void  getCreateCustomerTest() throws Exception{
        Map<String,Object> filedMap = new HashMap<String,Object>();
        filedMap.put("name","custer100");
        filedMap.put("contact","mike");
        filedMap.put("telephone","139256585");
        boolean result=customerService.createCustomer(filedMap);
        Assert.assertTrue(result);
    }

    @Test
    public void updateCustomerTest() throws Exception{
        long id=1;
        Map<String,Object> feildMap =new HashMap<String,Object>();
        feildMap.put("contact","ccc");
        boolean result=customerService.updateCustomer(id,feildMap);
        Assert.assertTrue(result);
    }

    @Test
    public  void  deleteCustomerTest() throws Exception{
        long id = 1;
        boolean result = customerService.deleteCustomer(id);
        Assert.assertTrue(result);

    }

}
