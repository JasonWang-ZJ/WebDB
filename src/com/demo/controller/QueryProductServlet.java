package com.demo.controller;

import com.demo.model.Product;

import java.io.*;

import java.sql.*;

import java.util.*;

import javax.servlet.*;

import javax.servlet.http.*;

import javax.servlet.annotation.WebServlet;

 

@WebServlet("/queryproduct.do")

public class QueryProductServlet extends HttpServlet{

   private static final long serialVersionUID = 1L;

   Connection dbconn = null;

   @Override
   public void init() {

      String driver = "org.mariadb.jdbc.Driver";

      String dburl = "jdbc:mariadb://localhost:3306/test";

      String username = "root";

      String password = "905452451qweQWE";

      try{

Class.forName(driver); // 加载驱动程序

          // 创建连接对象
          System.out.println("1234567");

dbconn = DriverManager.getConnection(

                             dburl,username,password);
          System.out.println("success");

      }catch(ClassNotFoundException e1){

          System.out.println(e1);

      }catch(SQLException e2){}

   }

   @Override
   public void doPost(HttpServletRequest request,

                       HttpServletResponse response)

                 throws ServletException,IOException{

      String productid = request.getParameter("productid");

      try{

        String sql="SELECT * FROM products WHERE prod_id = ?";

        PreparedStatement pstmt = dbconn.prepareStatement(sql);

        pstmt.setString(1,productid);

        ResultSet rst = pstmt.executeQuery();

        if(rst.next()){

           Product product = new Product();

           product.setProd_id(rst.getString("prod_id"));

           product.setPname(rst.getString("pname"));

           product.setPrice(rst.getDouble("price"));

           product.setStock(rst.getInt("stock"));

           request.getSession().setAttribute("product", product);

           response.sendRedirect("/webDB/displayProduct.jsp");

        }else{

           response.sendRedirect("/webDB/error.jsp");

        } 

      }catch(SQLException e){

        e.printStackTrace();

      }

   }

 

   @Override
   public void doGet(HttpServletRequest request,

                        HttpServletResponse response)

                        throws ServletException,IOException{

        ArrayList<Product> productList = null;

        productList = new ArrayList<Product>();

        try{

         String sql="SELECT * FROM products";

         PreparedStatement pstmt = dbconn.prepareStatement(sql);

         ResultSet result = pstmt.executeQuery();

         while(result.next()){

            Product product = new Product();

            product.setProd_id(result.getString("prod_id"));

            product.setPname(result.getString("pname"));

            product.setPrice(result.getDouble("price"));

            product.setStock(result.getInt("stock"));

            productList.add(product);

         }

         if(!productList.isEmpty()){

           request.getSession().setAttribute("productList",productList);

            response.sendRedirect("/webDB/displayAssAndCourses.jsp");

         }else{

            response.sendRedirect("/webDB/error.jsp");

         }

       }catch(SQLException e){

         e.printStackTrace();

       }

   }

   @Override
   public void destroy(){

         try {

            dbconn.close();

         }catch(Exception e){

          e.printStackTrace();

      }

   }

}