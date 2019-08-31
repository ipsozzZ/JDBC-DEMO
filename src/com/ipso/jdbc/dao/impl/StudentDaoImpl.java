package com.ipso.jdbc.dao.impl;

import com.ipso.jdbc.dao.IStudentDao;
import com.ipso.jdbc.domain.Student;
import com.ipso.jdbc.util.JDBCUtil;
import com.mysql.jdbc.JDBC4PreparedStatement;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Mysql数据库接口实现类
 * @author ipso
 */
public class StudentDaoImpl implements IStudentDao {

   /**
    * 数据库插入一条数据
    * @param stu 插入的学生对象
    */
   @Override
   public void save(Student stu) {
      Connection conn = null;
      PreparedStatement ps = null;

      try {
         // 通过工具类获取Connection对象
         conn = JDBCUtil.getConnection();
         // 编写数据库语句
         String sql = "insert into stu values(?,?,?)";
         // st = conn.createStatement(); // 不推荐使用
         ps = conn.prepareStatement(sql);
         ps.setInt(1, stu.getId());
         ps.setString(2, stu.getName());
         ps.setInt(3, stu.getAge());

         // 执行数据库语句
         ps.executeUpdate();

      }catch (Exception e){
         e.printStackTrace();
      }finally {
         // 释放资源
         JDBCUtil.close(conn, ps,null);
      }
   }

   /**
    * 数据库更新一条数据
    * @param id 需要更新的学生id
    * @param stu 新的学生对象
    */
   @Override
   public void update(int id, Student stu) {
      Connection conn = null;
      PreparedStatement ps = null;

      try {
         // 通过工具类获取Connection对象
         conn = JDBCUtil.getConnection();

         // 编写数据库语句
         String sql = "update stu set name= ? , age = ? where id = ?";
         ps = conn.prepareStatement(sql);
         ps.setString(1, stu.getName());
         ps.setInt(2, stu.getAge());
         ps.setInt(3, id);
         // 执行数据库语句
         ps.executeUpdate();

      }catch (Exception e){
         e.printStackTrace();
      }finally {
         // 释放资源
         JDBCUtil.close(conn, ps,null);
      }
   }

   /**
    * 数据库删除
    * @param id 学生id
    * @return 是否删除成功
    */
   @Override
   public Boolean delete(int id) {
      Connection conn = null;
      PreparedStatement ps = null;

      try {
         // 通过工具类获取Connection对象
         conn = JDBCUtil.getConnection();

         // 编写数据库语句
         String sql = "delete from stu where id = ?";
         // st = conn.createStatement();
         ps = conn.prepareStatement(sql);
         ps.setInt(1, id);

         // 执行数据库语句
         ps.executeUpdate();

         // 查看执行的sql语句
         System.out.println(((JDBC4PreparedStatement)ps).asSql());
         return true;

      }catch (Exception e){
         e.printStackTrace();
         return false;
      }finally {
         // 释放资源因为ps的PreparedStatement对象时Statement的子接口所以可以传ps给Statement对象
         JDBCUtil.close(conn, ps,null);
      }
   }

   /**
    * 获取一条数据
    * @param id 数据id
    * @return 获取的学生对象
    */
   @Override
   public Student getOne(int id) {
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet res = null;

      try {
         // 通过工具类获取Connection对象
         conn = JDBCUtil.getConnection();

         // 编写数据库语句
         String sql = "select * from stu where id = ?";
         // st = conn.createStatement();
         ps = conn.prepareStatement(sql);
         ps.setInt(1, id);
         // 执行数据库语句
         res = ps.executeQuery();

         if (res.next()){
            Student student = new Student();
            student.setName(res.getString("name"));
            student.setId(res.getInt("id"));
            student.setAge(res.getInt("age"));
            return student;
         }

      }catch (Exception e){
         e.printStackTrace();
      }finally {
         // 释放资源
         JDBCUtil.close(conn, ps, res);
      }
      return null;
   }

   /**
    * 获取所有数据
    * @return 包含所有学生对象的list对象
    */
   @Override
   public List<Student> getAll() {
      Connection conn = null;
      PreparedStatement ps = null;
      ResultSet res = null;
      List<Student> students = new ArrayList<Student>();

      try {
         // 通过工具类获取Connection对象
         conn = JDBCUtil.getConnection();

         // 编写数据库语句
         String sql = "select * from stu ";
         ps = conn.prepareStatement(sql);

         // 执行数据库语句
         res = ps.executeQuery();

         while (res.next()){
            Student student = new Student();
            student.setName(res.getString("name"));
            student.setId(res.getInt("id"));
            student.setAge(res.getInt("age"));
            students.add(student);
         }
         return students;

      }catch (Exception e){
         e.printStackTrace();
      }finally {
         // 释放资源
         JDBCUtil.close(conn, ps, res);
      }
      return null;
   }
}
