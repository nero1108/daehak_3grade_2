package daelim.office.library.book.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import daelim.office.library.book.BookVo;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;   // �̹� ��ϵ� ����
	final static public int BOOK_REGISTER_SUCCESS = 1;   // �ű� ���� ��� ����
	final static public int BOOK_REGISTER_FAIL = -1;   // �ű� ���� ��� ����
	
	public int registerBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] registerBookConfirm()");
	      
	      boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());
	      
	      if (!isISBN) {
	         int result = bookDao.insertBook(bookVo);
	         
	         if (result > 0)
	            return BOOK_REGISTER_SUCCESS;
	         
	         else
	            return BOOK_REGISTER_FAIL;
	         
	      } else {
	         return BOOK_ISBN_ALREADY_EXIST;
	         
	      }      
	   }
}
