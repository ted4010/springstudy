package com.gdu.prj02.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.gdu.prj02.test.dto.UserDto;

@SessionAttributes(names="user") // Model 에 user 가 저장되면 session 에 같은 값을 저장한다.
@Controller
public class MyController6 {
  
  @GetMapping("/user/login1.do")
  public String login1(HttpServletRequest request) {
    
    
    // HttpSession 구하기 Session = 방문자가 웹 서버에 접속해 있는 상태
    HttpSession session = request.getSession();
    
    // session 에 저장할 객체 
    UserDto user = new UserDto(1, "min@naver.com");
    
    // session 에 객체 저장하기
    // setAttribute : 지정된 요소의 속성 값을 설정합니다
    session.setAttribute("user", user);
    
    return null;
  }
  
}

