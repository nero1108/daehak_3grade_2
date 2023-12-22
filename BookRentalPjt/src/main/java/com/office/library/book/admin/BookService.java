package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVo;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;   // 이미 등록된 도서
	final static public int BOOK_REGISTER_SUCCESS = 1;   // 신규 도서 등록 성공
	final static public int BOOK_REGISTER_FAIL = -1;   // 신규 도서 등록 실패
	
	//도서 등록 확인
	public int registerBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] registerBookConfirm()");
	      //책중복 확인
	      boolean isISBN = bookDao.isISBN(bookVo.getB_isbn());
	      
	      if (!isISBN) 
	      {
	         int result = bookDao.insertBook(bookVo);
	         
	         if (result > 0)
	            return BOOK_REGISTER_SUCCESS;
	         
	         else
	            return BOOK_REGISTER_FAIL;
	         
	      } else 
	      {
	         return BOOK_ISBN_ALREADY_EXIST;
	         
	      }      
	   }
	//도서 검색
	public List<BookVo> searchBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] searchBookConfirm()");
	      
	      return bookDao.selectBooksBySearch(bookVo);      
	   }
	//도서 상세정보 확인
	public BookVo bookDetail(int b_no) {
	      System.out.println("[BookService] bookDetail()");
	      
	      return bookDao.selectBook(b_no);      
	   }
	//도서 수정을 위한 수정 도서 호출
	public BookVo modifyBookForm(int b_no) {
	      System.out.println("[BookService] modifyBookForm()");
	      
	      return bookDao.selectBook(b_no);      
	   }
	//도서 수정
	public int modifyBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] modifyBookConfirm()");
	      
	      return bookDao.updateBook(bookVo);      
	   }
	//도서 삭제
	public int deleteBookConfirm(int b_no) {
	      System.out.println("[BookService] deleteBookConfirm()");
	      
	      return bookDao.deleteBook(b_no);
	      
	   }
}
