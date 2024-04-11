package com.gdu.myapp.service;

import java.io.File;
import java.nio.file.Files;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.gdu.myapp.dto.AttachDto;
import com.gdu.myapp.dto.UploadDto;
import com.gdu.myapp.dto.UserDto;
import com.gdu.myapp.mapper.UploadMapper;
import com.gdu.myapp.utils.MyFileUtils;
import com.gdu.myapp.utils.MyPageUtils;

import lombok.RequiredArgsConstructor;
import net.coobird.thumbnailator.Thumbnails;

@RequiredArgsConstructor
@Service
public class UploadServiceImpl implements UploadService {

  private final UploadMapper uploadMapper;
  private final MyPageUtils myPageUtils;
  private final MyFileUtils myFileUtils;
  
  @Override
  public boolean registerUpload(MultipartHttpServletRequest multipartRequest) {

    // UPLOAD_T 테이블에 추가하기
    String title = multipartRequest.getParameter("title");
    String contents = multipartRequest.getParameter("contents");
    int userNo = Integer.parseInt(multipartRequest.getParameter("userNo"));
    
    UserDto user = new UserDto();
    user.setUserNo(userNo);
    
    UploadDto upload = UploadDto.builder()
                          .title(title)
                          .contents(contents)
                          .user(user)
                        .build();
    
    System.out.println("INSERT 이전 : " + upload.getUploadNo());  // uploadNo 없음
    int insertUploadCount = uploadMapper.insertUpload(upload);
    System.out.println("INSERT 이후 : " + upload.getUploadNo());  // uploadNo 있음 (<selectKey> 동작에 의해서)
    
    // 첨부 파일 처리하기
    List<MultipartFile> files = multipartRequest.getFiles("files");
    
    // 첨부 파일이 없는 경우 : [MultipartFile[field="files", filename=, contentType=application/octet-stream, size=0]]
    // 첨부 파일이 있는 경우 : [MultipartFile[field="files", filename=404.jpg, contentType=image/jpeg, size=63891]]
    // System.out.println(files);

    int insertAttachCount;
    if(files.get(0).getSize() == 0) {
      insertAttachCount = 1;  // 첨부가 없어도 files.size() 는 1 이다.
    } else {
      insertAttachCount = 0;
    }
    
    for (MultipartFile multipartFile : files) {
      
      if(multipartFile != null && !multipartFile.isEmpty()) {
        
        String uploadPath = myFileUtils.getUploadPath();
        File dir = new File(uploadPath);
        if(!dir.exists()) {
          dir.mkdirs();
        }
        
        String originalFilename = multipartFile.getOriginalFilename();
        String filesystemName = myFileUtils.getFilesystemName(originalFilename);
        File file = new File(dir, filesystemName);
        
        try {

          multipartFile.transferTo(file);
          
          // 썸네일 작성
          String contentType = Files.probeContentType(file.toPath());
          int hasThumbnail = contentType != null && contentType.startsWith("image") ? 1 : 0;
          
          // 이미지의 썸네일 만들기
          if(hasThumbnail == 1) {
            File thumbnail = new File(dir, "s_" + filesystemName);
            Thumbnails.of(file)             // 원본 이미지 파일
                      .size(96, 64)         // 가로 96px, 세로 64px
                      .toFile(thumbnail);   // 썸네일 이미지 파일
          }
          
          // ATTACH_T 테이블에 추가하기
          AttachDto attach = AttachDto.builder()
                                .uploadPath(uploadPath)
                                .filesystemName(filesystemName)
                                .originalFilename(originalFilename)
                                .hasThumbnail(hasThumbnail)
                                .uploadNo(upload.getUploadNo())
                              .build();
          
          insertAttachCount += uploadMapper.insertAttach(attach);
          
        } catch (Exception e) {
          e.printStackTrace();
        }
        
      }  // if
      
    }  // for
    
    return (insertUploadCount == 1) && (insertAttachCount == files.size());
    
  }

  @Override
  public void loadUploadList(Model model) {
    
    /*
     * interface UploadService {
     *   void execute(Model model);
     * }
     * 
     * class UploadRegisterService implements UploadService {
     *   @Override
     *   void execute(Model model) {
     *   
     *   }
     * }
     * 
     * class UploadListService implements UploadService {
     *   @Override
     *   void execute(Model model) {
     *   
     *   }
     * }
     */
    
    Map<String, Object> modelMap = model.asMap();
    HttpServletRequest request = (HttpServletRequest) modelMap.get("request");
    
    int total = uploadMapper.getUploadCount();
    
    Optional<String> optDisplay = Optional.ofNullable(request.getParameter("display"));
    int display = Integer.parseInt(optDisplay.orElse("20"));
    
    Optional<String> optPage = Optional.ofNullable(request.getParameter("page"));
    int page = Integer.parseInt(optPage.orElse("1"));

    myPageUtils.setPaging(total, display, page);
    
    Optional<String> optSort = Optional.ofNullable(request.getParameter("sort"));
    String sort = optSort.orElse("DESC");
    
    Map<String, Object> map = Map.of("begin", myPageUtils.getBegin()
                                   , "end", myPageUtils.getEnd()
                                   , "sort", sort);
    
    /*
     * total = 100, display = 20
     * 
     * page  beginNo
     * 1     100
     * 2     80
     * 3     60
     * 4     40
     * 5     20
     */
    model.addAttribute("beginNo", total - (page - 1) * display);
    model.addAttribute("uploadList", uploadMapper.getUploadList(map));
    model.addAttribute("paging", myPageUtils.getPaging(request.getContextPath() + "/upload/list.do", sort, display));
    model.addAttribute("display", display);
    model.addAttribute("sort", sort);
    model.addAttribute("page", page);
  }
  
  // 상세보기
  @Override
  public void loadUploadByNo(int uploadNo, Model model) {
    model.addAttribute("upload", uploadMapper.getUploadByNo(uploadNo));
  }

}