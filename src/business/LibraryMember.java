package business;

import java.io.Serializable;
import java.time.LocalDate;


import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

final public class LibraryMember extends Person implements Serializable {
	private String memberId="MB00";
	private CheckOutRecord cr;
	private static int  count=1;
	
	public LibraryMember(String memberId, String fname, String lname, String tel,Address add) {
		super(fname,lname, tel, add);
		this.memberId = memberId;		
	}
	
	public LibraryMember(Address add,String memberId) {
		this.setAddress(add);
		this.memberId = memberId;	
	}
	public CheckOutRecord getCr() {
		return cr;
	}
	public void setCr(CheckOutRecord cr) {
		this.cr = cr;
	}
	
	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String id) {
		 memberId=id;
	}

	
	
	@Override
	public String toString() {
		return "Member Info: " + "ID: " + memberId + ", name: " + getFirstName() + " " + getLastName() + 
				", " + getTelephone() + " " + getAddress();
	}

	private static final long serialVersionUID = -2226197306790714013L;
}
