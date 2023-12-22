package daelim.office.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import daelim.office.library.book.BookVo;
import daelim.office.library.book.admin.util.UploadFileService;

@Controller
@RequestMapping("/book/admin")
public class BookController {

	@Autowired
	UploadFileService uploadFileService;
	@Autowired
	BookService bookService;
	
	@GetMapping("/registerBookForm")
	public String registerBookForm() {
	      System.out.println("[BookController] registerBookForm()");
	      
	      String nextPage = "admin/book/register_book_form";
	      
	      return nextPage;
	      
	   }
	
		/*
	    * 도서 등록 확인
	    */
	   //@RequestMapping(value = "/registerBookConfirm", method = RequestMethod.POST)
	   @PostMapping("/registerBookConfirm")
	   public String registerBookConfirm(BookVo bookVo, @RequestParam("file") MultipartFile file) {
	      System.out.println("[BookController] registerBookConfirm()");
	      
	      String nextPage = "admin/book/register_book_ok";
	      
	      // SAVE FILE
	      String savedFileName = uploadFileService.upload(file);
	      
	      if (savedFileName != null) {
	         bookVo.setB_thumbnail(savedFileName);
	         int result = bookService.registerBookConfirm(bookVo);
	         
	         if (result <= 0)
	            nextPage = "admin/book/register_book_ng";
	         
	      } else {
	         nextPage = "admin/book/register_book_ng";
	         
	      }
	      return nextPage;
	   }
}
