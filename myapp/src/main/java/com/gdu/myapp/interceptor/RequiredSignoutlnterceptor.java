package com.gdu.myapp.interceptor;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

public class RequiredSignoutlnterceptor implements HandlerInterceptor {

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
   
    HttpSession session = request.getSession();
    if(session.getAttribute("user") != null) {
      response.setContentType("text/html; charset=UTF-8");
      PrintWriter out = response.getWriter();
      out.println("<script>");
      out.println("alert('로그아웃하고 오세욥.');");
      out.println("history.back();");
      out.println("</script>");
      out.flush();
      out.close();
      return false;
    }
    return true;
  }
}
