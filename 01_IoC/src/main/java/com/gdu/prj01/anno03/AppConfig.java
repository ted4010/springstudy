package com.gdu.prj01.anno03;

import java.net.URL;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
  
  @Bean
  public MyConnection myConnection() {
      MyConnection myconnection = new MyConnection();
      myconnection.setDriver("oracle.jdbc.OracleDriver");
      myconnection.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
      myconnection.setUser("GD");
      myconnection.setPassword("1111");
      return myconnection;
  }
  
  @Bean
  public MyDao myDao() {
    MyDao myDao = new MyDao();
    myDao.setMyConnection(myConnection());
    return myDao;
  }
  
  @Bean
  public MyService myService() {
    MyService myService = new MyService();
    myService.setMyDao(myDao());
    return myService;
  }
  
  @Bean
  public MyController myController() {
    MyController myController = new MyController();
    myController.setMyService(myService());
    return myController;
  }
  

}
