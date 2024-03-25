package com.gdu.prj09.service;

import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

import com.gdu.prj09.dto.MemberDto;

public interface MemberService {
  //싱글페이지 ResponseEntity 한개로 통일해서 반환할때 사용
  ResponseEntity<Map<String, Object>> getMembers(int page, int display);
  ResponseEntity<MemberDto> getMemberByNo(int memberNo);
  ResponseEntity<Map<String, Object>> registerMember(Map<String, Object> map, HttpServletResponse response);
  ResponseEntity<Map<String, Object>> modifyMember(MemberDto member);
  ResponseEntity<Map<String, Object>> removeMember(int memberNo);
  ResponseEntity<Map<String, Object>> removeMembers(String memberNoList);
}

