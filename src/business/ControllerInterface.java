package business;

import java.util.List;

import business.Book;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;

public interface ControllerInterface {
	public void login() throws LoginException;
	public List<String> allMemberIds();
	public List<String> allBookIds();
	
}
