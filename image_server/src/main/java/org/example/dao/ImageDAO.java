package org.example.dao;
import org.example.model.Image;
import org.example.util.Util;
import javax.print.attribute.standard.NumberUp;
import javax.swing.plaf.nimbus.State;
import java.io.File;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import static org.example.servlet.ImageServlet.IMG_DIR;
public class ImageDAO {
    private static Util util=Util.getInstance();
    public static int queryCount(String md5) {
        Connection c=null;
        PreparedStatement p=null;
        ResultSet r=null;
        try {
            c=util.getConnection();
            String sql="select count(0) as c from image_table where md5=?";
            p=c.prepareStatement(sql);
            p.setString(1,md5);
            r=p.executeQuery();
            r.next();
            return r.getInt("c");
        } catch (SQLException e) {
            throw new RuntimeException("查询上传图片md5出错："+md5, e);
        }finally {
            util.close(c,p,r);
        }
    }

    public static void insert(Image image) {
        Connection c=null;
        PreparedStatement p=null;
        try {
            c=util.getConnection();
            String sql="insert into image_table values(null,?,?,?,?,?,?)";
            p=c.prepareStatement(sql);
            p.setString(1,image.getImageName());
            p.setLong(2,image.getSize());
            p.setString(3,image.getUpload_time());
            p.setString(4,image.getMd5());
            p.setString(5,image.getContentType());
            p.setString(6,image.getPath());
            p.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("新增上传图片出错：", e);
        }finally {
            util.close(c,p);
        }
    }

    public static List<Image> queryAll() {
        Connection c=null;
        PreparedStatement p=null;
        ResultSet r=null;
        try {
            c=util.getConnection();
            String sql="select * from image_table";
            p=c.prepareStatement(sql);
            r=p.executeQuery();
            List<Image> list=new ArrayList<>();
            while (r.next()){
                Image image=new Image();
                image.setImageId(r.getInt("image_id"));
                image.setImageName(r.getString("image_name") );
                image.setSize(r.getLong("size"));
                image.setUpload_time(r.getString("upload_time"));
                image.setMd5(r.getString("md5"));
                image.setContentType(r.getString("content_type"));
                image.setPath(r.getString("path"));
                list.add(image);
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("查询所有图片出错", e);
        }finally {
            util.close(c,p,r);
        }
    }

    public static Image queryId(int id) {
        Connection c=null;
        PreparedStatement p=null;
        ResultSet r=null;
        try {
            c=util.getConnection();
            String sql="select * from image_table where  image_id=?";
            p=c.prepareStatement(sql);
            p.setInt(1,id);
            r=p.executeQuery();
            Image image=null;
            while (r.next()){
                image=new Image();
                image.setImageId(r.getInt("image_id"));
                image.setImageName(r.getString("image_name") );
                image.setSize(r.getLong("size"));
                image.setUpload_time(r.getString("upload_time"));
                image.setMd5(r.getString("md5"));
                image.setContentType(r.getString("content_type"));
                image.setPath(r.getString("path"));
            }
            return image;
        } catch (SQLException e) {
            throw new RuntimeException("查询指定图片出错", e);
        }finally {
            util.close(c,p,r);
        }
    }

    public static int delete(int id) {
        Connection c=null;
        PreparedStatement p=null;
        try {
            c=util.getConnection();
            c.setAutoCommit(false);
            String sql="delete from image_table where image_id=?";
            p=c.prepareStatement(sql);
            p.setInt(1,id);
            int n=p.executeUpdate();
            Image image=ImageDAO.queryId(id);
            String path=IMG_DIR+image.getPath();
            File file=new File(path);
            file.delete();
            c.commit();
            return n;
        } catch (SQLException e) {
            try {
                c.rollback();
            } catch (SQLException es) {
                throw new RuntimeException("删除图片回滚出错:"+id, es);
            }
            throw new RuntimeException("删除图片出错:"+id, e);
        }finally {
            util.close(c,p);
        }
    }
}
