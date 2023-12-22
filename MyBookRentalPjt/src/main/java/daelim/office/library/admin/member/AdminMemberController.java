package daelim.office.library.admin.member;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import daelim.office.library.admin.member.AdminMemberService;
import daelim.office.library.admin.member.AdminMemberVo;

@Controller
@RequestMapping("/admin/member")
public class AdminMemberController {
	
	@Autowired
	AdminMemberService adminMemberService;
	
//ȸ������
	   //@RequestMapping(value = "/createAccountForm", method = RequestMethod.GET)
	   @GetMapping("/createAccountForm")
	   public String createAccountForm() 
	   {
	      System.out.println("[AdminMemberController] createAccountForm()");
	      
	      String nextPage = "admin/member/create_account_form";
	      
	      return nextPage;
	      
	   }
	   
	   //ȸ������ Ȯ��
	   //@RequestMapping(value = "/createAccountConfirm", method = RequestMethod.POST)
	   @PostMapping("/createAccountConfirm")
	   public String createAccountConfirm(AdminMemberVo adminMemberVo) {
			  System.out.println("");
	      System.out.println("[AdminMemberController] createAccountConfirm()");
	      
	      String nextPage = "admin/member/create_account_ok";
	      
	      int result = adminMemberService.createAccountConfirm(adminMemberVo);
	      
	      if (result <= 0)
	         nextPage = "admin/member/create_account_ng";
	      
	      return nextPage;
	      
	   }
	   
	   /*    * �α���    */
	   //@RequestMapping(value = "/loginForm", method = RequestMethod.GET)
	   @GetMapping("/loginForm")
	   public String loginForm() {
			  System.out.println("");
	      System.out.println("[AdminMemberController] loginForm()");
	      
	      String nextPage = "admin/member/login_form";
	      
	      return nextPage;
	      
	   }
	   
	   /*    * �α��� Ȯ��    */
	   //@RequestMapping(value = "/loginConfirm", method = RequestMethod.POST)
	   @PostMapping("/loginConfirm")
	   public String loginConfirm(AdminMemberVo adminMemberVo, HttpSession session)
	   {
			  System.out.println("");
	      System.out.println("[AdminMemberController] loginConfirm()");
	      
	      String nextPage = "admin/member/login_ok";
	      
	      AdminMemberVo loginedAdminMemberVo = adminMemberService.loginConfirm(adminMemberVo);
	      
	      if (loginedAdminMemberVo == null) {
	         nextPage = "admin/member/login_ng";
	         
	      } 
	      else {
	         session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
	         session.setMaxInactiveInterval(60 * 30);         
	      }	
	      
	      return nextPage;
	      
	   }
	   
	   /* * �α׾ƿ� Ȯ��    */
	   //@RequestMapping(value = "/logoutConfirm", method = RequestMethod.GET)
	   @GetMapping("/logoutConfirm")
	   public String logoutConfirm(HttpSession session) {
		  System.out.println("");
	      System.out.println("[AdminMemberController] logoutConfirm()");
	      
	      String nextPage = "redirect:/admin";
	      
	      session.removeAttribute("loginedAdminMemberVo");
	      //invalidate()�Լ��� ������ ��ȿȭ��Ű�� ������ ���ǿ� 
	      //����� ������(loginedAdminMemberVo)�� ����� �� ���� �ǹǷ� �α��� ���´�
	      //�����ǰ� /admin���� �����̷�Ʈ ��.
	      session.invalidate();
	      
	      return nextPage;
	      
	   }
	   
	   
	   /*
	    * ������ ���(Model ���)
	    */
//	   @RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
//	   public String listupAdmin(Model model) {
//	      System.out.println("[AdminMemberController] listupAdmin()");
//	   
//	      String nextPage = "admin/member/listup_admins";
//	      
//	      List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
//	      
//	      model.addAttribute("adminMemberVos", adminMemberVos);
//	      
//	      return nextPage;
//	      
//	   }
//	   
	   
	   /* * ������ ���(ModelAndView ���) */
	   @RequestMapping(value = "/listupAdmin", method = RequestMethod.GET)
	   public ModelAndView listupAdmin() {	      
		  System.out.println("");
	      System.out.println("[AdminMemberController] modifyAccountConfirm()");
	      String nextPage = "admin/member/listup_admins";   
	      List<AdminMemberVo> adminMemberVos = adminMemberService.listupAdmin();
	      
	      ModelAndView modelAndView = new ModelAndView();      // �� ModelAndView ��ü�� �����Ѵ�.
	      modelAndView.setViewName(nextPage);               // �� ModelAndView�� �並 �����Ѵ�.
	       //�� ModelAndView�� �����͸� �߰��Ѵ�.
	      modelAndView.addObject("adminMemberVos", adminMemberVos);   
	      
	      return modelAndView;   // �� ModelAndView�� ��ȯ�Ѵ�.      
	   }
	   
	  
	   /* * ������ ����  */
	   @RequestMapping(value = "/setAdminApproval", method = RequestMethod.GET)
	   public String setAdminApproval(@RequestParam("a_m_no") int a_m_no) {
	      System.out.println("[AdminMemberController] setAdminApproval()");
	      
	      String nextPage = "redirect:/admin/member/listupAdmin";
	      
	      adminMemberService.setAdminApproval(a_m_no);
	      
	      return nextPage;      
	   }
	   
	   /*    * ȸ������ ����    */
	   //   @RequestMapping(value = "/modifyAccountForm", method = RequestMethod.GET)
	   @GetMapping("/modifyAccountForm")
	   //�Ű������� HttpSession Ÿ���� session��ü�� 
	   //�Ű������� �޴µ� �̰��� ������ ������ �����ϱ� ���� ���� �����ڰ�
	   //�α��εǾ� �ִ��� Ȯ���ϴ� �뵵�� ����.
	   public String modifyAccountForm(HttpSession session) {
	      System.out.println("[AdminMemberController] modifyAccountForm()");
	      
	      String nextPage = "admin/member/modify_account_form";
	      
	      AdminMemberVo loginedAdminMemberVo = (AdminMemberVo) session.getAttribute("loginedAdminMemberVo");
	      if (loginedAdminMemberVo == null) nextPage = "redirect:/admin/member/loginForm";
	      
	      return nextPage;
	   }
	   
	   /*    * ȸ������ ���� Ȯ�� */
	//   @RequestMapping(value = "/modifyAccountConfirm", method = RequestMethod.POST)
	   @PostMapping("/modifyAccountConfirm")
	   //������ ������ ������ ����Ǿ� �ִ� AdminMemberVo��
	   //session ��ü�� �Ű������� �޽��ϴ�.
	   public String modifyAccountConfirm(AdminMemberVo adminMemberVo, 
	         HttpSession session) {
	      System.out.println("[AdminMemberController] modifyAccountConfirm()");
	      
	      String nextPage = "admin/member/modify_account_ok";
	      
	      int result = adminMemberService.modifyAccountConfirm(adminMemberVo);
	      
	      if (result > 0) {
	         AdminMemberVo loginedAdminMemberVo = adminMemberService.getLoginedAdminMemberVo(adminMemberVo.getA_m_no());
	         
	         session.setAttribute("loginedAdminMemberVo", loginedAdminMemberVo);
	         session.setMaxInactiveInterval(60 * 30);
	         
	      } else {
	         nextPage = "admin/member/modify_account_ng";
	         
	      }
	      
	      return nextPage;
	      
	   }
	   
	   /*
	    * ��й�ȣ ã��
	    */
	//   @RequestMapping(value = "/findPasswordForm", method = RequestMethod.GET)
	   @GetMapping("/findPasswordForm")
	   public String findePasswordForm() {
	      System.out.println("[AdminMemberController] findPasswordForm()");
	      
	      String nextPage = "admin/member/find_password_form";
	      
	      return nextPage;
	      
	   }
	   
	   /*
	    * ��й�ȣ ã�� Ȯ��
	    */
	//   @RequestMapping(value = "/findPasswordConfirm", method = RequestMethod.POST)
	   @PostMapping("/findPasswordConfirm")
	   public String findPasswordConfirm(AdminMemberVo adminMemberVo) {
	      System.out.println("[AdminMemberController] findPasswordConfirm()");
	      
	      String nextPage = "admin/member/find_password_ok";
	      
	      int result = adminMemberService.findPasswordConfirm(adminMemberVo);
	      
	      if (result <= 0)
	         nextPage = "admin/member/find_password_ng";
	      
	      return nextPage;
	      
	   }
}
