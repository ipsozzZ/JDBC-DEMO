package com.ipso.jdbc.test;

import com.ipso.jdbc.util.JDBCUtil;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 事务操作demo
 * @author ipso
 */
public class TransactionTest {

   @Test
   /**
    * 测试入口
    * @throws SQLException
    */
   public void test() throws SQLException {
      this.ordinary();
      this.transaction();
   }

   /**
    * 常规不开启事务，即每条语句都是一个事务时，银行转账问题
    * @throws SQLException
    */
   void ordinary() throws SQLException {

      // 连接数据库
      Connection conn = JDBCUtil.getConnection();
      // 1. 检查zs账户余额
      String sql = "select * from account where name = ? and money > ?";
      PreparedStatement ps = conn.prepareStatement(sql);
      ps.setString(1, "zs");
      ps.setInt(2, 1000);
      ResultSet res = ps.executeQuery();
      if (!res.next()){
         throw new RuntimeException("余额不足");
      }

      // 开启事务，将自动提交事务关闭就是开启事务，默认是一句sql语句就是一个事务，
      // 手动提交后遇到connection.commit()时为一个事务。
      // conn.setAutoCommit(false);

      // 2. 减少zs账户1000
      sql = "update account set money = money - ? where name = ?";
      ps = conn.prepareStatement(sql);
      ps.setInt(1, 1000);
      ps.setString(2, "zs");
      ps.executeUpdate();

      // 这里让程序抛出一个算术异常，如果没有开启事务，上面减钱的操作正常执行，
      // 而下面加钱操作将不会执行，开启事务后上面执行的语句将被回滚。
      int a = 1/0;

      // 3. 增加ls账户1000
      sql = "update account set money = money + ? where name = ?";
      ps = conn.prepareStatement(sql);
      ps.setInt(1, 1000);
      ps.setString(2, "ls");
      ps.executeUpdate();
      // conn.commit(); // 手动提交事务，从conn.setAutoCommit(false);开始到此处为一个事务。
      // 释放资源
      JDBCUtil.close(conn, ps, res);
   }

   /**
    * 事务方式处理转账问题
    * @throws SQLException
    */
   void transaction() throws SQLException {

      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet res = null;

      // 连接数据库
      conn = JDBCUtil.getConnection();

      // 1. 检查zs账户余额
      String sql = "select * from account where name = ? and money > ?";
      ps = conn.prepareStatement(sql);
      ps.setString(1, "zs");
      ps.setInt(2, 1000);
      res = ps.executeQuery();
      if (!res.next()){
         throw new RuntimeException("余额不足");
      }

      try{

         // 开启事务，将自动提交事务关闭就是开启事务，默认是一句sql语句就是一个事务，
         // 手动提交后遇到connection.commit()时为一个事务。
         conn.setAutoCommit(false);

         // 2. 减少zs账户1000
         sql = "update account set money = money - ? where name = ?";
         ps = conn.prepareStatement(sql);
         ps.setInt(1, 1000);
         ps.setString(2, "zs");
         ps.executeUpdate();

         // 这里让程序抛出一个算术异常，如果没有开启事务，上面减钱的操作正常执行，
         // 而下面加钱操作将不会执行，开启事务后上面执行的语句将被回滚。
         int a = 1/0;

         // 3. 增加ls账户1000
         sql = "update account set money = money + ? where name = ?";
         ps = conn.prepareStatement(sql);
         ps.setInt(1, 1000);
         ps.setString(2, "ls");
         ps.executeUpdate();
         conn.commit(); // 提交事务

      }catch (Exception e){
         e.printStackTrace();
         conn.rollback(); // 出现异常时回滚
      }finally {
         JDBCUtil.close(conn, ps, res);
      }
   }
}
