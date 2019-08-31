package com.ipso.jdbc.test;

import com.ipso.jdbc.domain.Student;
import com.ipso.jdbc.util.JDBCUtil;
import com.mysql.jdbc.JDBC4PreparedStatement;
import org.junit.Test;

import java.lang.reflect.Type;
import java.sql.*;

/**
 * 数据库注入、数据库存储过程测试
 * @author ipso
 */
public class LoginTest {

   /**
    * 测试入口
    * @throws SQLException 抛出异常
    */
   @Test
   public void test() throws SQLException {

      /* sql注入测试 */
      // System.out.println(login("' OR 1=1 OR '", "123456s"));
      // 静态sql语句拼接后：select * from user where name = '' OR 1=1 OR '' and pass = '123456s'
      // 预编译sql语句：select * from user where name = '\' OR 1=1 OR \'' and pass = '123456s'

      /* jdbc调用存储过程 */
      ProcedureTest();
   }

   /**
    * sql语句测试
    * @param name 用户名
    * @param pass 密码
    * @return 返回执行结果
    * @throws SQLException 抛出异常
    */
   String login(String name, String pass) throws SQLException {
      Connection conn = JDBCUtil.getConnection();
      String sql = "select * from user where name = ? and pass = ?";

      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1,name);
      ps.setString(2, pass);
      ResultSet res = ps.executeQuery();
      // 获取并打印sql语句：((JDBC4PreparedStatement)ps).asSql()
      System.out.println(((JDBC4PreparedStatement)ps).asSql());
      if (res.next()){
         JDBCUtil.close(conn, ps, res);
         return "登录成功";
      }
      else {
         JDBCUtil.close(conn, ps, res);
         return "登录失败";
      }
   }

   /**
    * JDBC调用mysql存储过程
    * @throws SQLException
    */
   void ProcedureTest () throws SQLException {
      // 连接数据库
      Connection conn = JDBCUtil.getConnection();
      // 调用存储过程
      CallableStatement cs =  conn.prepareCall("{ call getStudent(?)}");
      // 设置参数
      cs.setString(1, "ipso1");
      // 执行存储过程
      ResultSet res = cs.executeQuery();
      if (res.next()){
         Student stu = new Student();
         stu.setId(res.getInt("id"));
         stu.setName(res.getString("name"));
         stu.setAge(res.getInt("age"));
         System.out.println(stu);
      }
      JDBCUtil.close(conn, cs, res);
   }

   /**
    * JDBC调用mysql输出参数存储过程
    * @throws SQLException
    */
   void ProcedureTest1 () throws SQLException {
      // 连接数据库
      Connection conn = JDBCUtil.getConnection();
      // 调用存储过程
      CallableStatement cs =  conn.prepareCall("{ call getName(?,?)}");
      // 设置参数
      cs.setInt(1, 6);
      cs.registerOutParameter(2, Types.VARCHAR);
      // 执行存储过程
      cs.execute();
      String name = cs.getString(2);
      System.out.println(name);

      JDBCUtil.close(conn, cs, null);
   }

}
