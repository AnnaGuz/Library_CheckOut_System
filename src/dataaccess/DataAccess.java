package dataaccess;

import java.util.HashMap;

import business.Author;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccessFacade.StorageType;

public interface DataAccess { 
	public HashMap<String,Book> readBooksMap();
	public HashMap<String,User> readUserMap();
	public HashMap<String, LibraryMember> readMemberMap();
	public void saveNewMember(LibraryMember member); 
	public void saveNewAuthor(Author author); 
	public void RemoveMember(LibraryMember member);
	void saveNewAuthor1(HashMap<String, Author> author);
	public void RemoveBook(Book selectedbook);
	public void saveNewBook1(HashMap<String, Book> bk);
}
