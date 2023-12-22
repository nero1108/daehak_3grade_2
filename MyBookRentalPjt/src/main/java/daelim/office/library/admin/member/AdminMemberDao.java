package daelim.office.library.admin.member;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import daelim.office.library.admin.member.AdminMemberVo;


@Component
public class AdminMemberDao {
	@Autowired
	JdbcTemplate jdbcTemplate;
	@Autowired
	PasswordEncoder passwordEncoder;
	
	public boolean isAdminMember(String a_m_id) {
		System.out.println("[AdminMemberDao] isAdminMember()");
		
		String sql =  "SELECT COUNT(*) FROM tbl_admin_member2 "+ "WHERE a_m_id = ?";
	
		int result = jdbcTemplate.queryForObject(sql, Integer.class, a_m_id);
		//sql : SQL문 , Integer.class : 쿼리 실행후 반환되는 데이터 타입,
		//a_m_id : 관리자가 입력한 아이디로서 매개변수에 있는 String a_m_id를 가져옴.
		//queryForObject()가 1을 반환한다면 관리자가 입력한 아이디는 이미 사용중인
		//아이디로 회원가입을 할 수없고, 0이면 사용중인 아이디가 아니므로 회원가입이 가능합니다.
		
		/*
		if (result > 0)
			return true;
		else
			return false;
		*/
		
		return result > 0 ? true : false;
	}
	
	public int insertAdminAccount(AdminMemberVo adminMemberVo) {
	      System.out.println("[AdminMemberDao] insertAdminAccount()");
	      
	      List<String> args = new ArrayList<String>();
	      
	      String sql =  "INSERT INTO tbl_admin_member2(";
	      
	            if (adminMemberVo.getA_m_id().equals("super admin")) {
	               sql += "a_m_approval, ";
	               args.add("1");
	            }
	            
	            sql += "a_m_id, ";
	            args.add(adminMemberVo.getA_m_id());
	            
	            sql += "a_m_pw, ";
	            //args.add(adminMemberVo.getA_m_pw() );
	            args.add(passwordEncoder.encode(adminMemberVo.getA_m_pw()));
	            
	            sql += "a_m_name, ";
	            args.add(adminMemberVo.getA_m_name());
	            
	            sql += "a_m_gender, ";
	            args.add(adminMemberVo.getA_m_gender());
	            
	            sql += "a_m_part, ";
	            args.add(adminMemberVo.getA_m_part());
	            
	            sql += "a_m_position, ";
	            args.add(adminMemberVo.getA_m_position());
	            
	            sql += "a_m_mail, ";
	            args.add(adminMemberVo.getA_m_mail());
	            
	            sql += "a_m_phone, ";
	            args.add(adminMemberVo.getA_m_phone());
	            
	            sql += "a_m_reg_date, a_m_mod_date) ";
	            
	            if (adminMemberVo.getA_m_id().equals("super admin")) 
	               sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
	            else 
	               sql += "VALUES(?, ?, ?, ?, ?, ?, ?, ?, NOW(), NOW())";
	            
	      int result = -1;
	      
	      try {
	         
	         result = jdbcTemplate.update(sql, args.toArray());
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	      
	      return result;
	      
	   }
	
	public AdminMemberVo selectAdmin(AdminMemberVo adminMemberVo) {
	      System.out.println("[AdminMemberDao] selectAdmin()");
	      
	      String sql =  "SELECT * FROM tbl_admin_member2 " + "WHERE a_m_id = ? AND a_m_approval > 0";
	   
	      List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
	      
	      try {
	         
	         adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
	            
	            @Override
	            public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
	               
	               AdminMemberVo adminMemberVo = new AdminMemberVo();
	               
	               adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
	               adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
	               adminMemberVo.setA_m_id(rs.getString("a_m_id"));
	               adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
	               adminMemberVo.setA_m_name(rs.getString("a_m_name"));
	               adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
	               adminMemberVo.setA_m_part(rs.getString("a_m_part"));
	               adminMemberVo.setA_m_position(rs.getString("a_m_position"));
	               adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
	               adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
	               adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
	               adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
	               
	               return adminMemberVo;
	               
	            }//end of  mapRow(ResultSet rs, int rowNum)
	            //end of new RowMapper<AdminMemberVo>() {...
	         }, adminMemberVo.getA_m_id());//end of jdbcTemplate.query()
	         
	         if (!passwordEncoder.matches(adminMemberVo.getA_m_pw(), 
	               adminMemberVos.get(0).getA_m_pw()))
	            adminMemberVos.clear();
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	      
	      return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
	   
	   }
	
	public List<AdminMemberVo> selectAdmins() {
	      System.out.println("[AdminMemberDao] selectAdmins()");
	      
	      String sql =  "SELECT * FROM tbl_admin_member2";
	   
	      List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
	      
	      try {
	         
	         adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
	            
	            @Override
	            public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
	               
	               AdminMemberVo adminMemberVo = new AdminMemberVo();
	               
	               adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
	               adminMemberVo.setA_m_approval(rs.getInt("a_m_approval"));
	               adminMemberVo.setA_m_id(rs.getString("a_m_id"));
	               adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
	               adminMemberVo.setA_m_name(rs.getString("a_m_name"));
	               adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
	               adminMemberVo.setA_m_part(rs.getString("a_m_part"));
	               adminMemberVo.setA_m_position(rs.getString("a_m_position"));
	               adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
	               adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
	               adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
	               adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
	               
	               return adminMemberVo;
	               
	            }
	            
	         });
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	      
	      return adminMemberVos;
	      
	   }
	
	public int updateAdminAccount(int a_m_no) {
	      System.out.println("[AdminMemberDao] updateAdminAccount()");
	      
	      String sql =  "UPDATE tbl_admin_member2 SET " + "a_m_approval = 1 " + "WHERE a_m_no = ?";
	      
	      int result = -1;
	      
	      try {
	         
	         result = jdbcTemplate.update(sql, a_m_no);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	            
	      return result;      
	   }
	
	
	public int updateAdminAccount(AdminMemberVo adminMemberVo) {
	      System.out.println("[AdminMemberDao] updateAdminAccount(AdminMemberVo adminMemberVo)");
	      
	      String sql =  "UPDATE tbl_admin_member2 SET "
	               + "a_m_name = ?, "
	               + "a_m_gender = ?, "
	               + "a_m_part = ?, "
	               + "a_m_position = ?, "
	               + "a_m_mail = ?, "
	               + "a_m_phone = ?, "
	               + "a_m_mod_date = NOW() "
	               + "WHERE a_m_no = ?";
	      
	      int result = -1;
	      
	      try {
	         
	         result = jdbcTemplate.update(sql, 
	                               adminMemberVo.getA_m_name(), 
	                               adminMemberVo.getA_m_gender(), 
	                               adminMemberVo.getA_m_part(), 
	                               adminMemberVo.getA_m_position(), 
	                               adminMemberVo.getA_m_mail(), 
	                               adminMemberVo.getA_m_phone(), 
	                               adminMemberVo.getA_m_no());
	         
	      } catch (Exception e) {
	         e.printStackTrace();         
	      }
	            
	      return result;      
	   }
	
	public AdminMemberVo selectAdmin(int a_m_no) {
	      System.out.println("[AdminMemberDao] selectAdmin(int a_m_no)");
	      
	      String sql =  "SELECT * FROM tbl_admin_member2 "
	               + "WHERE a_m_no = ?";
	   
	      List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
	      
	      try {
	         
	         adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
	            
	            @Override
	            public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
	               
	               AdminMemberVo adminMemberVo = new AdminMemberVo();
	               
	               adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
	               adminMemberVo.setA_m_id(rs.getString("a_m_id"));
	               adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
	               adminMemberVo.setA_m_name(rs.getString("a_m_name"));
	               adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
	               adminMemberVo.setA_m_part(rs.getString("a_m_part"));
	               adminMemberVo.setA_m_position(rs.getString("a_m_position"));
	               adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
	               adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
	               adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
	               adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
	               
	               return adminMemberVo;
	               
	            }
	            
	         }, a_m_no);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	      
	      return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
	      
	   }
	
	public AdminMemberVo selectAdmin(String a_m_id, 
		      String a_m_name, String a_m_mail) {
		      System.out.println("[AdminMemberDao] selectAdmin()");
		      
		      String sql =  "SELECT * FROM tbl_admin_member2 " + "WHERE a_m_id = ? AND a_m_name = ? AND a_m_mail = ?";
		   
		      List<AdminMemberVo> adminMemberVos = new ArrayList<AdminMemberVo>();
		      
		      try {
		         
		         adminMemberVos = jdbcTemplate.query(sql, new RowMapper<AdminMemberVo>() {
		            
		            @Override
		            public AdminMemberVo mapRow(ResultSet rs, int rowNum) throws SQLException {
		               
		               AdminMemberVo adminMemberVo = new AdminMemberVo();
		               
		               adminMemberVo.setA_m_no(rs.getInt("a_m_no"));
		               adminMemberVo.setA_m_id(rs.getString("a_m_id"));
		               adminMemberVo.setA_m_pw(rs.getString("a_m_pw"));
		               adminMemberVo.setA_m_name(rs.getString("a_m_name"));
		               adminMemberVo.setA_m_gender(rs.getString("a_m_gender"));
		               adminMemberVo.setA_m_part(rs.getString("a_m_part"));
		               adminMemberVo.setA_m_position(rs.getString("a_m_position"));
		               adminMemberVo.setA_m_mail(rs.getString("a_m_mail"));
		               adminMemberVo.setA_m_phone(rs.getString("a_m_phone"));
		               adminMemberVo.setA_m_reg_date(rs.getString("a_m_reg_date"));
		               adminMemberVo.setA_m_mod_date(rs.getString("a_m_mod_date"));
		               
		               return adminMemberVo;
		               
		            }
		            
		         }, a_m_id, a_m_name, a_m_mail);
		         
		      } catch (Exception e) {
		         e.printStackTrace();
		         
		      }
		      
		      return adminMemberVos.size() > 0 ? adminMemberVos.get(0) : null;
		      
		   }
	
	public int updatePassword(String a_m_id, String newPassword) {
	      System.out.println("[AdminMemberDao] updatePassword()");
	      
	      String sql =  "UPDATE tbl_admin_member2 SET "
	               + "a_m_pw = ?,  a_m_mod_date = NOW() "
	               + "WHERE a_m_id = ?";
	      
	      int result = -1;
	      
	      try {
	         
	         result = jdbcTemplate.update(sql, passwordEncoder.encode(newPassword), a_m_id);
	         
	      } catch (Exception e) {
	         e.printStackTrace();
	         
	      }
	            
	      return result;
	   }
}
