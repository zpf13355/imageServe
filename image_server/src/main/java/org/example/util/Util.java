package org.example.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

//public class Util {
//    //徐序列化包
//    private static final ObjectMapper om=new ObjectMapper();
//
//
//    //数据库连接池
//    private static final MysqlDataSource ds=new MysqlDataSource();
//
//    static {
//        ds.setUrl("jdbc:mysql://localhost:3306/");
//        ds.setUser("root");
//        ds.setPassword("zpf1314521");
//        ds.setUseSSL(false);
//        ds.setCharacterEncoding("UTF-8");
//    }
//
//    //序列化：java对象序列化为json字符串
//    public static String serialize(Object o){
//        try {
//            return om.writeValueAsString(o);
//        } catch (JsonProcessingException e) {
//            //导入的jackson包错误就会抛出异常
//            throw new RuntimeException("序列化java对象失败："+o, e);
//        }
//    }
//
//    //建立连接
//    public static Connection getConnection(){
//        try {
//            return ds.getConnection();
//        } catch (SQLException e) {
//            throw new RuntimeException("数据库连接获取失败", e);
//        }
//    }
//
//
//    //关闭
//    public static void close(Connection c, Statement s, ResultSet  r){
//
//            try {
//                if (r!=null){
//                    r.close();
//                }
//                if (s!=null){
//                    s.close();
//                }
//                if (c!=null){
//                    c.close();
//                }
//
//            } catch (SQLException e) {
//                throw new RuntimeException("释放数据库资源失败", e);
//            }
//    }
//
//    public static void close(Connection c,Statement s){
//        close(c,s,null);
//    }
//
//
//
//}

//连接池-单例模式版本
public class Util {
    //序列化包
    private static final ObjectMapper om=new ObjectMapper();

    private static Util instance;


    //数据库连接池
    private  final MysqlDataSource ds;


    //私有构造函数
    private Util(){
        ds=new MysqlDataSource();
        ds.setUrl("jdbc:mysql://localhost:3306/image_server");
        ds.setUser("root");
        ds.setPassword("zpf1314521");
        ds.setUseSSL(false);
        ds.setCharacterEncoding("UTF-8");
    }


    //静态返回实例-只有一个对象
    public static Util getInstance(){
        if (instance==null){
            synchronized (Util.class){
                instance=new Util();
            }
        }

        return instance;
    }

    //序列化：java对象序列化为json字符串
    public static String serialize(Object o){
        try {
            return om.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            //导入的jackson包错误就会抛出异常
            throw new RuntimeException("序列化java对象失败："+o, e);
        }
    }

    //建立连接
    public synchronized final Connection getConnection(){
        try {
            return ds.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException("数据库连接获取失败", e);
        }
    }

    //关闭
    public void close(Connection c, Statement s, ResultSet  r){

        try {
            if (r!=null){
                r.close();
            }
            if (s!=null){
                s.close();
            }
            if (c!=null){
                c.close();
            }

        } catch (SQLException e) {
            throw new RuntimeException("释放数据库资源失败", e);
        }
    }

    public void close(Connection c,Statement s){
        close(c,s,null);
    }



}