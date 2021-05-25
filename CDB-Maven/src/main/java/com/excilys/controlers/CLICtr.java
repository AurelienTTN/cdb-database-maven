package com.excilys.controlers;


public class CLICtr {
	
	private static CLICtr instance;

	
	private CLICtr() {
		
	}
	
	public static CLICtr getInstance() {
	       if (instance == null) {
	            instance = new CLICtr();
	        }
	        return instance;
	    }
	
	
	
	
	    
}
