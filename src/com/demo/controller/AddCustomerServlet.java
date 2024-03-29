package com.demo.controller;
import java.io.*;

import javax.servlet.*;

import javax.servlet.http.*;

import com.demo.model.Customer;

import com.demo.dao.CustomerDao;

import javax.servlet.annotation.WebServlet;



@WebServlet("/addCustomer.do")

public class AddCustomerServlet extends HttpServlet{

    @Override
    public void doPost(HttpServletRequest request,

                       HttpServletResponse response)

            throws ServletException,IOException{

        CustomerDao dao = new CustomerDao();

        Customer customer = new Customer();

        String message = null;

        try{

            customer.setCust_id(request.getParameter("cust_id"));

// 将传递来的字符串重新使用utf-8编码，以免产生乱码

            customer.setCname(new String(request.getParameter("cname")

                    .getBytes("iso-8859-1"),"UTF-8"));

            customer.setEmail(new String(request.getParameter("email")

                    .getBytes("iso-8859-1"),"UTF-8"));

            customer.setBalance(

                    Double.parseDouble(request.getParameter("balance")));

            boolean success = dao.addCustomer(customer);

            if(success){

                message = "<li>成功插入一条记录！</li>";

            }else{

                message = "<li>插入记录错误！</li>";

            }

        }catch(Exception e){

            message = "<li>插入记录错误！</li>";

        }

        request.setAttribute("result", message);

        RequestDispatcher rd =

                getServletContext().getRequestDispatcher("/addCustomer.jsp");

        rd.forward (request,response);

    }

}