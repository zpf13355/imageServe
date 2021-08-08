package org.example.servlet;

import org.apache.commons.codec.digest.DigestUtils;
import org.example.dao.ImageDAO;
import org.example.exception.AppException;
import org.example.model.Image;
import org.example.util.Util;
import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("application/json");
        Map<String,Object> map=new HashMap<>();
        try {
            String username=req.getParameter("username");
            String password=req.getParameter("password");
            List<String> list= Arrays.asList("a","b","c");
            if (!list.contains(username)){
                throw new AppException("用户不存在");
            }else if (!password.equals("123")){
                throw new AppException("用户名或密码错误");
            }
            HttpSession session=req.getSession();
            session.setAttribute("username",username);
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
        String json= Util.serialize(map);
        resp.getWriter().println(json);
    }
}
