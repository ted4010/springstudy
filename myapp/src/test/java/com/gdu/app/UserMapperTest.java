package com.gdu.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UserMapper;

// JUnit5
@ExtendWith(SpringExtension.class)

@ContextConfiguration(locations="fil:file:src/main/webapp/WEB-INF/spring/root-context.xml")

public class UserMapperTest {
  
  @Autowired
  private UserMapper userMapper;
  
  @Test
  void test() {
    assertEquals(1, userMapper.insertUser(new UserDto()));
  }
}
