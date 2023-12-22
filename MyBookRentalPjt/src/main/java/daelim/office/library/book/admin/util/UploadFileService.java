package daelim.office.library.book.admin.util;

import java.io.File;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadFileService {
	public String upload(MultipartFile file) 
	   {
	      
	      boolean result = false;
	      
	      // File 저장
	      String fileOriName = file.getOriginalFilename();
	      String fileExtension = 
	            fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());
	      String uploadDir = "C:\\library\\upload\\";
	      
	      //UUID : 네트워크 상에서 서로 모르는 개체들을 식별하고 구별하기 위해서는 각각의 고유한 이름이 필요
	      //범용 고유 식별자를 의미하며 중복이 되지 않는 유일한 값을 구성하고자 할때 주로 사용
	      UUID uuid = UUID.randomUUID();
	      String uniqueName = uuid.toString().replaceAll("-", "");
	      
	      File saveFile = new File(uploadDir + "\\" + uniqueName + fileExtension);
	      
	      if (!saveFile.exists())
	         saveFile.mkdirs();
	      
	      try {
	         file.transferTo(saveFile);
	         result = true;
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	      
	      if (result) {
	         System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");
	         return uniqueName + fileExtension;
	         
	      } else {
	         System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");
	         return null;
	         
	      }
	   }
}
