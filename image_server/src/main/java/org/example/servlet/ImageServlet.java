package org.example.servlet;
import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.ImageDAO;
import org.example.exception.AppException;
import org.example.model.Image;
import org.example.util.Util;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@MultipartConfig

@WebServlet("/image")
public class ImageServlet extends HttpServlet {
    public static final String IMG_DIR="F://IMG";
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Map<String,Object> map=new HashMap<>();
        try {
            Part part=req.getPart("uploadImage");
            long size=part.getSize();
            String contentType=part.getContentType();
            String filename=part.getSubmittedFileName();
            Date date=new Date();
            DateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm");
            String updateTime=df.format(date);
            InputStream is=part.getInputStream();
            String md5= DigestUtils.md5Hex(is);
            int query= ImageDAO.queryCount(md5);
            if (query>=1){
                throw new RuntimeException("上传图片重复");
            }
            part.write(IMG_DIR+"/"+md5);
            Image image=new Image();
            image.setImageName(filename);
            image.setSize(size);
            image.setUpload_time(updateTime);
            image.setMd5(md5);
            image.setContentType(contentType);
            image.setPath("/"+md5);
            ImageDAO.insert(image);
            map.put("ok",true);

        }catch (Exception e){
            e.printStackTrace();
            map.put("ok",false);

            if(e instanceof AppException){
                map.put("msg", e.getMessage());
            }else{
                map.put("msg", "未知错误，请联系管理员");
            }
        }
        String json=Util.serialize(map);
        resp.getWriter().println(json);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        String id=req.getParameter("imageId");
        Object o=null;

        if (id==null){
            o=ImageDAO.queryAll();
        }else{
            o= ImageDAO.queryId(Integer.parseInt(id));
        }

        String json= Util.serialize(o);

        resp.getWriter().println(json);

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");

        //1.获取删除id
        String id=req.getParameter("imageId");
        Image image=ImageDAO.queryId(Integer.parseInt(id));
        int n=ImageDAO.delete(Integer.parseInt(id));
       ok(resp);

    }

    public static void ok(HttpServletResponse resp) throws IOException {
        resp.getWriter().println("{\"ok\":true}");
    }
}
