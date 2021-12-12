package util;

import dataaccess.DataAccessFacade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import business.Address;
import business.Author;
import business.Book;
import dataaccess.DataAccessFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PopulateBook {
	private DataAccessFacade dataaccess;
	private ObservableList<Book> personData = FXCollections.observableArrayList();
	private HashMap<String, Book> bk = new HashMap<>();
	

	public PopulateBook() {
		// Add some sample data
		Address ad= new Address();
		ad.setCity("FF");
		ad.setZip("262353");
		ad.setState("IA");
		Author a=new Author("Fasil","Habtegiorgis","2024646252",ad,"fasil is a student at mum");
		List <Author> aut = new ArrayList<>();
		aut.add(a);
		Book b=new Book("34-84937","Harry Potter",7,aut);
		for(Book temp:personData) {
			String id=temp.getIsbn();
			bk.put(id, temp);
			
			
		}
		personData.add(b);
		dataaccess=	new DataAccessFacade();
		dataaccess.saveNewBook1(bk);
	}
	
	


	
	public static void main(String args[]) {
		new PopulateBook();
	}

}