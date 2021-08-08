package org.example.filter;

import org.example.util.Util;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebFilter("/*")
public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req=(HttpServletRequest) request;
        HttpServletResponse resp=(HttpServletResponse) response;
        String uri=req.getServletPath();
        if (uri.equals("/index.html")&&!isLogin(req)){
            resp.sendRedirect("login.html");
        }else if ((uri.equals("/image")||uri.equals("/imageShow"))&&!isLogin(req)){
            resp.setCharacterEncoding("UTF-8");
            resp.setContentType("application/json");
            resp.setStatus(401);
            Map<String, Object> map = new HashMap<>();
            map.put("ok", false);
            map.put("msg", "用户未登陆，不允许访问");
            String json = Util.serialize(map);
            resp.getWriter().println(json);
        }else {
            chain.doFilter(request,response);
        }
    }

    public boolean isLogin(HttpServletRequest req){
        HttpSession session=req.getSession();
        if (session!=null){
            Object username=session.getAttribute("username");
            if (username!=null){
                return true;
            }
        }
        return false;
    }

    @Override
    public void destroy() {
    }
}
