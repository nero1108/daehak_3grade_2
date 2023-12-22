package com.office.library.admin.member;

import java.security.SecureRandom;
import java.util.Date;
import java.util.List;

import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;

@Service
public class AdminMemberService {
	@Autowired
	AdminMemberDao adminMemberDao;
	
	@Autowired
	JavaMailSenderImpl javaMailSenderImpl;
	
	final static public int ADMIN_ACCOUNT_ALREADY_EXIST = 0;
	final static public int ADMIN_ACCOUNT_CREATE_SUCCESS = 1;
	final static public int ADMIN_ACCOUNT_CREATE_FAIL = -1;
	
	//���̵� �ߺ� Ȯ�ΰ� ȸ������
	public int createAccountConfirm(AdminMemberVo adminMemberVo) 
	{
		System.out.println("[AdminMemberService] createAccountConfirm()");
		//�ߺ� ���̵� Ȯ���մϴ�. isMember�� true�̸� �̹� ������� ���̵��̰�,
		//false�̸� ��밡���� ���̵� �Դϴ�.
		boolean isMember = adminMemberDao.isAdminMember(adminMemberVo.getA_m_id());
		
		if (!isMember) 
		{
			int result = adminMemberDao.insertAdminAccount(adminMemberVo);
			
			if (result > 0)
				return ADMIN_ACCOUNT_CREATE_SUCCESS;
			
			else
				return ADMIN_ACCOUNT_CREATE_FAIL;
			
		} 
		else {
			return ADMIN_ACCOUNT_ALREADY_EXIST;
			
		}
	}
		//�α���
	   public AdminMemberVo loginConfirm(AdminMemberVo adminMemberVo) {
		      System.out.println("[AdminMemberService] loginConfirm()");
		      
		      AdminMemberVo loginedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo);
		      
		      if (loginedAdminMemberVo != null)
		         System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN SUCCESS!!");
		      else
		         System.out.println("[AdminMemberService] ADMIN MEMBER LOGIN FAIL!!");
		      
		      return loginedAdminMemberVo;
		      
		   }
	   //ȸ������ ��� �ҷ�����
	   public List<AdminMemberVo> listupAdmin() {
		      System.out.println("[AdminMemberService] listupAdmin()");
		      
		      return adminMemberDao.selectAdmins();
		      
		   }
	   //�⺻���� �����ڷ� ��ȯ
	   public void setAdminApproval(int a_m_no) {
		      System.out.println("[AdminMemberService] setAdminApproval()");
		      
		      int result = adminMemberDao.updateAdminAccount(a_m_no);
		      System.out.print("[AdminMemberService] setAdminApproval()���� ");
		      System.out.print("result=" + result);      
		   }
	   //ȸ����������
	   public int modifyAccountConfirm(AdminMemberVo adminMemberVo) {
		      System.out.println("[AdminMemberService] modifyAccountConfirm()");
		      
		      return adminMemberDao.updateAdminAccount(adminMemberVo);      
		   }
	   //ȸ������ ������ ��α���	
	   public AdminMemberVo getLoginedAdminMemberVo(int a_m_no) {
		      System.out.println("[AdminMemberService] getLoginedAdminMemberVo()");
		      
		      return adminMemberDao.selectAdmin(a_m_no);      
		   }
	   //��й�ȣ ã��
	   public int findPasswordConfirm(AdminMemberVo adminMemberVo) {
		      System.out.println("[AdminMemberService] findPasswordConfirm()");
		      
		      AdminMemberVo selectedAdminMemberVo = adminMemberDao.selectAdmin(adminMemberVo.getA_m_id(), adminMemberVo.getA_m_name(), adminMemberVo.getA_m_mail());
		      
		      int result = 0;
		      //���̵�� �̸� ���Ϸ� ã������
		      if (selectedAdminMemberVo != null) {
		         //���ο� ��й�ȣ ����
		         String newPassword = createNewPassword();
		         result = adminMemberDao.updatePassword(adminMemberVo.getA_m_id(), newPassword);

		         if (result > 0)
		            sendNewPasswordByMail(adminMemberVo.getA_m_mail(), newPassword);
		      }
		      
		           return result;
		      
		   }
	   //�� ��й�ȣ ��
	   //������ ȸ�����Խ� �������� �̸����϶� �̷��� �Ѵ� ������ �������� �̸����� ������ �ƴϱ⿡ �ּ�ó��
	   private void sendNewPasswordByMail(String toMailAddr, String newPassword) {
		      System.out.println("[AdminMemberService] sendNewPasswordByMail()");
		      
		      final MimeMessagePreparator mimeMessagePreparator = new MimeMessagePreparator() {
		         
		         @Override
		         public void prepare(MimeMessage mimeMessage) throws Exception {
		            final MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
		            mimeMessageHelper.setTo("nerostarin@gmail.com");
//		            mimeMessageHelper.setTo(toMailAddr);
		            mimeMessageHelper.setSubject("[�ѱ� ������] �� ��й�ȣ �ȳ��Դϴ�.");
		            mimeMessageHelper.setText("�� ��й�ȣ : " + newPassword, true);
		            
		         }
		         
		      };
		      javaMailSenderImpl.send(mimeMessagePreparator);
		      
		   }
	   //��й�ȣ ���λ���
	   private String createNewPassword() {
		      System.out.println("[AdminMemberService] createNewPassword()");
		      
		      char[] chars = new char[] {
		            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
		            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 
		            'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 
		            'u', 'v', 'w', 'x', 'y', 'z'
		            };

		      StringBuffer stringBuffer = new StringBuffer();
		      SecureRandom secureRandom = new SecureRandom();
		      secureRandom.setSeed(new Date().getTime());
		      
		      int index = 0;
		      int length = chars.length;
		      for (int i = 0; i < 8; i++) {
		         index = secureRandom.nextInt(length);
		      
		         if (index % 2 == 0) 
		            stringBuffer.append(String.valueOf(chars[index]).toUpperCase());
		         else
		            stringBuffer.append(String.valueOf(chars[index]).toLowerCase());
		      
		      }
		      
		      System.out.println("[AdminMemberService] NEW PASSWORD: " + stringBuffer.toString());
		      
		      return stringBuffer.toString();
		      
		   }
	   
}
