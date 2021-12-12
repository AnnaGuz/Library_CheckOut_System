package util;

import java.util.HashMap;

import business.Address;
import business.Author;
import business.LibraryMember;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


public class PopulateAuthor {
	private DataAccessFacade dataaccess;
	private ObservableList<Author> personData = FXCollections.observableArrayList();
	private HashMap<String, Author> aut = new HashMap<>();
	

	public PopulateAuthor() {
		// Add some sample data
		Address ad= new Address();
		ad.setCity("FF");
		ad.setZip("262353");
		ad.setState("IA");
		Author a=new Author("Fasil","Habtegiorgis","2024646252",ad,"fasil is a student at mum");
		for(Author temp:personData) {
			String id=temp.getFirstName()+temp.getLastName();
			aut.put(id, temp);
			
			
		}
		personData.add(a);
		dataaccess=	new DataAccessFacade();
		dataaccess.saveNewAuthor1(aut);
	}
	
	

}
