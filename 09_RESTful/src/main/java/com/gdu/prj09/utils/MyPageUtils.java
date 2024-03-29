package com.gdu.prj09.utils;

import lombok.Data;

@Data
public class MyPageUtils {

  private int total;     // 전체 게시글 개수                      (DB에서 구한다.)
  private int display;   // 한 페이지에 표시할 게시글 개수        (요청 파라미터로 받는다.)
  private int page;      // 현재 페이지 번호                      (요청 파라미터로 받는다.)
  private int begin;     // 한 페이지에 표시할 게시글의 시작 번호 (계산한다.)
  private int end;       // 한 페이지에 표시할 게시글의 종료 번호 (계산한다.)

  private int pagePerBlock = 10;  // 한 블록에 표시할 페이지 링크의 개수      (임의로 결정한다.)
  private int totalPage;          // 전체 페이지 개수                         (계산한다.)
  private int beginPage;          // 한 블록에 표시할 페이지 링크의 시작 번호 (계산한다.)
  private int endPage;            // 한 블록에 표시할 페이지 링크의 종료 번호 (계산한다.)
  
  public void setPaging(int total, int display, int page) {
    
    this.total = total;
    this.display = display;
    this.page = page;
    
    begin = (page - 1) * display + 1;
    end = begin + display - 1;
    
    totalPage = (int) Math.ceil((double)total / display);
    beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
    endPage = Math.min(totalPage, beginPage + pagePerBlock - 1);
    
  }
  
  public String getAsyncPaging() {
   
    StringBuilder builder = new StringBuilder();
    
    // <
    if(beginPage == 1) {
      builder.append("<a>&lt;</a>");
    } else {
      builder.append("<a href=\"javascript:fnPaging(" + (beginPage - 1) + ")\">&lt;</a>");
    }
    
    // 1 2 3 4 5 6 7 8 9 10
    for(int p = beginPage; p <= endPage; p++) {
      if(p == page) {
        builder.append("<a>" + p + "</a>");
      } else {        
        builder.append("<a href=\"javascript:fnPaging(" + p + ")\">" + p + "</a>");
      }
    }
    
    // >
    if(endPage == totalPage) {
      builder.append("<a>&gt;</a>");
    } else {   
      builder.append("<a href=\"javascript:fnPaging(" + (endPage + 1) + ")\">&gt;</a>");
    }
    
    return builder.toString();
    
  }
  
}