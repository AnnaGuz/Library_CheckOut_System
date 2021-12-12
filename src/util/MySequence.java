package util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicLong;

import application.Main;
import business.Book;
import business.LibraryMember;
import dataaccess.DataAccess;
import dataaccess.DataAccessFacade;
import dataaccess.User;

public class MySequence {
    private AtomicLong currentValue = new AtomicLong(0L);
	private Main main;
    public String getNextValue() {
    	
    	main=new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, LibraryMember> map = da.readMemberMap();
		TreeSet<String> i=new TreeSet<String>();
		i.addAll(map.keySet());
		
		int x=i.size();
		String temp=i.last();
	//	String id=temp.getMemberId();
		System.out.println(temp);
		
		char lastchar=temp.charAt(temp.length()-1);
		int nextchar=Character.getNumericValue(lastchar);
		System.out.println(nextchar);
		System.out.println(temp.substring(0, temp.length()-1) + Integer.toString(nextchar+1));
		
		return 	temp.substring(0, temp.length()-1) + Integer.toString(nextchar+1);
		
    //    return currentValue.getAndIncrement();
    }
 public String getNextValueBook() {
    	
    	main=new application.Main();
		DataAccess da = new DataAccessFacade();
		HashMap<String, Book> map = da.readBooksMap();
		TreeSet<String> i=new TreeSet<String>();
		i.addAll(map.keySet());
		
		int x=i.size();
		String temp=i.last();
	//	String id=temp.getMemberId();
		System.out.println(temp);
		
		char lastchar=temp.charAt(temp.length()-1);
		int nextchar=Character.getNumericValue(lastchar);
		System.out.println(nextchar);
		System.out.println(temp.substring(0, temp.length()-1) + Integer.toString(nextchar+1));
		
		return 	temp.substring(0, temp.length()-1) + Integer.toString(nextchar+1);
		
    //    return currentValue.getAndIncrement();
    }
}
