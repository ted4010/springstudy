package com.gdu.prj04.dao;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.gdu.prj04.dto.BoardDto;

import lombok.AllArgsConstructor;

@Repository // 이거 때문에  BoardDaoImpl 스프링 컨테이너에 저장됨
@AllArgsConstructor
public class BoardDaoImpl implements BoardDao {
    
  private BoardDto board1;
  private BoardDto board2;  // AppComfig에 있는 Bean의 정보가 들어가 있다. Bean과 메소드의 이름은 같아야한다.
  private BoardDto board3;
  
  @Override
  public List<BoardDto> getBoardList() {
    return Arrays.asList(board1, board2, board3);
  }

  @Override
  public BoardDto getBoardByNo(int boardNo) {
    BoardDto board = null;
    switch(boardNo) {
    case 1 : board = board1; break;
    case 2 : board = board1; break;
    case 3 : board = board1; break;
    }
    return board;
  }

}
