package com.ipso.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * JDBC工具类
 * @author ipso
 */
public class JDBCUtil {

   // 数据库连接信息
   public static String url  = "jdbc:mysql://localhost:3306/jdbc_db";
   public static String user = "root";
   public static String pass = "gqm1975386453";

   // mysql数据库驱动名
   public static String DriverName = "com.mysql.jdbc.Driver";

   // 当类被加载的时候就会执行静态代码块中的内容
   // 不能将静态代码块放到属性声明的前面，否则属性将不会被声明和初始化，虽然语法检查时没有报错，但是执行时程序将会报错
   static {
      try {
         // 加载数据库驱动
         Class.forName(JDBCUtil.DriverName);
      }catch (Exception e){
         e.printStackTrace();
      }
   }

   /**
    * 获取Connection对象
    * @return Connection对象
    */
   public static Connection getConnection(){
      try{
         // 连接数据库获取Connection对象
         return DriverManager.getConnection(JDBCUtil.url, JDBCUtil.user, JDBCUtil.pass);
      }catch (Exception e){
         e.printStackTrace();
      }
      return null;
   }

   /**
    * 释放资源
    * @param conn Connection数据库连接对象
    * @param st   Statement数据库执行程序连接对象
    * @param res  ResultSet数据库执行结果集对象
    */
   public static void close(Connection conn, Statement st, ResultSet res){

      // 释放资源
      if (res != null) {
         try {
            // 释放资源
            res.close();
         }catch (Exception e){
            e.printStackTrace();
         }
      }
      if (st != null){
         try {
            // 释放资源
            st.close();
         }catch (Exception e){
            e.printStackTrace();
         }
      }
      if (conn != null){
         try {
            // 释放资源
            conn.close();
         }catch (Exception e){
            e.printStackTrace();
         }
      }
   }
}
