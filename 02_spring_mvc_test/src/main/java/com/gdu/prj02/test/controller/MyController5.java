package com.gdu.prj02.test.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class MyController5 {

  /*
   * redirect 방법
   * 
   * 1. return "redirect:요청주소";
   * 2. HttpServletResponse response 를 이용한 응답 만들기
   */
  
  // Spring 4 이후 GetMapping(조회) / PostMapping(생성) / PutMapping(수정) / DeleteMapping(삭제) 등 사용 가능
  
  @RequestMapping("/faq/add.do")
  public String add(RedirectAttributes redirectAttributes) {
    
    // add 결과
    int addResult = Math.random() < 0.5 ? 1 : 0;
    
    // add 결과를 flash attribute 로 저장하면 redirect 경로에서 확인이 가능하다.
    // 성공 : "/fag/list.do" 요청으로 이동하는 fag/list.jsp 에서 addResult 값을 확인할 수 있다.
    // 실패 : "/main.do" 요청으로 이동하는 index.jsp에서 addResult 값을 확인할 수 있다.
    // redirectAttributes.addFlashAttribute 일회성으로 사용해야할때 사용해야합니다.
    redirectAttributes.addFlashAttribute("addResult", addResult);
    
    // add 결과에 따른 이동
    String path = addResult == 1 ? "/faq/list.do" : "/main.do" ;
    
    // 이동
    return "redirect:" + path;
  }
  
  @GetMapping("/faq/list.do")
  public String list() {
    return "faq/list";
  }
  
  @GetMapping("/fag/modify.do")
  public void modify(HttpServletRequest request, HttpServletResponse response) {
    
    // 
  }
  
}

