package com.office.library.book.admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.office.library.book.BookVo;

@Service
public class BookService {
	
	@Autowired
	BookDao bookDao;
	final static public int BOOK_ISBN_ALREADY_EXIST = 0;   // �̹� ��ϵ� ����
	final static public int BOOK_REGISTER_SUCCESS = 1;   // �ű� ���� ��� ����
	final static public int BOOK_REGISTER_FAIL = -1;   // �ű� ���� ��� ����
	
	//���� ��� Ȯ��
	public int registerBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] registerBookConfirm()");
	      //å�ߺ� Ȯ��
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
	//���� �˻�
	public List<BookVo> searchBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] searchBookConfirm()");
	      
	      return bookDao.selectBooksBySearch(bookVo);      
	   }
	//���� ������ Ȯ��
	public BookVo bookDetail(int b_no) {
	      System.out.println("[BookService] bookDetail()");
	      
	      return bookDao.selectBook(b_no);      
	   }
	//���� ������ ���� ���� ���� ȣ��
	public BookVo modifyBookForm(int b_no) {
	      System.out.println("[BookService] modifyBookForm()");
	      
	      return bookDao.selectBook(b_no);      
	   }
	//���� ����
	public int modifyBookConfirm(BookVo bookVo) {
	      System.out.println("[BookService] modifyBookConfirm()");
	      
	      return bookDao.updateBook(bookVo);      
	   }
	//���� ����
	public int deleteBookConfirm(int b_no) {
	      System.out.println("[BookService] deleteBookConfirm()");
	      
	      return bookDao.deleteBook(b_no);
	      
	   }
}
