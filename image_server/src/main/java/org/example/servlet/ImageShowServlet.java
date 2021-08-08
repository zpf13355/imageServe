package org.example.servlet;

import org.example.dao.ImageDAO;
import org.example.model.Image;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;

@WebServlet("/imageShow")
public class ImageShowServlet extends HttpServlet {
    private static final Set<String> whileList=new HashSet<>();
    static{
        whileList.add("http://localhost:8080/image_server/");
        whileList.add("http://localhost:8080/image_server/index.html");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer=req.getHeader("Referer");
        if (!whileList.contains(referer)){
            resp.setStatus(403);
            return;
        }
        String id=req.getParameter("imageId");
        Image image= ImageDAO.queryId(Integer.parseInt(id));
        resp.setContentType(image.getContentType());
        String path=ImageServlet.IMG_DIR+image.getPath();
        FileInputStream is=new FileInputStream(path);
        OutputStream os=resp.getOutputStream();
        byte[] bytes=new byte[1024*8];
        int len=0;
        while ((len=is.read(bytes))!=-1){
            os.write(bytes,0,len);
        }
        os.flush();
        is.close();
        os.close();
    }
}
