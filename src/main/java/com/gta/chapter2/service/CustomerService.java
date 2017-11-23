package com.gta.chapter2.service;

import com.gta.chapter2.helper.DatabaseHelper;
import com.gta.chapter2.model.Customer;

import java.util.List;
import java.util.Map;

/**
 * Created by kui.lv on 2017/11/9.
 * 提供客户数据服务
 */
public class CustomerService {

    //private static final Logger LOGGER= LoggerFactory.getLogger(CustomerService.class);

    /*private static final String DRIVER;

    private static final String URL;

    private static final String USERNAME;

    private static final String PASSWORD;



    static {
        Properties conf = PropsUtil.loadProps("config.properties");
        DRIVER = conf.getProperty("jdbc.driver");
        URL =conf.getProperty("jdbc.url");
        USERNAME = conf.getProperty("jdbc.userName");
        PASSWORD = conf.getProperty("jdbc.passWord");

        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException e) {
            LOGGER.error("can not load jdbc driver",e);
        }
    }*/


    /**
     * 获取客户列表
     * @return
     */
    public List<Customer> getCustomerList(){
        //Connection conn = null;
       // try {
            //List<Customer> customerList = new ArrayList<Customer>();
            String sql = "SELECT * FROM customer";
            /*conn = DatabaseHelper.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setName(rs.getString("name"));
                customer.setContact(rs.getString("contact"));
                customer.setTelephone(rs.getString("telephone"));
                customer.setRemark(rs.getString("remark"));
                customerList.add(customer);
            }
            return customerList;*/
            return DatabaseHelper.queryEntityList(Customer.class,sql);
       /* } catch (SQLException e) {
            LOGGER.error("excute sql failure",e);*/
      /*  }finally {
           DatabaseHelper.closeConnection();
        }*/

        //return null;
    }

    /**
     * 获取客户信息
     * @param id
     * @return
     */
    public Customer getCustomer(long id){
        String sql = "SELECT * FROM CUSTOMER";
        return DatabaseHelper.queryEntity(Customer.class,sql);
    }

    /**
     * 新建客户
     * @param fieldMap
     * @return
     */
    public boolean createCustomer(Map<String,Object> fieldMap){

        return  DatabaseHelper.insertEntity(Customer.class,fieldMap);
    }

    /**
     * 更新客户
     * @param id
     * @param fieldMap
     * @return
     */
    public boolean updateCustomer(long id,Map<String,Object>fieldMap){

        return DatabaseHelper.updateEntity(Customer.class,id,fieldMap);
    }

    /**
     * 删除用户
     * @param id
     * @return
     */
    public boolean deleteCustomer(long id ){

        return DatabaseHelper.deleteEntity(Customer.class,id);
    }
}
