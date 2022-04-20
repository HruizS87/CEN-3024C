package cen3024C;

import java.net.*;
import java.io.*;

public class PrimeNumberServer {
	
	/* 
	 * Function that determines whether or 
	 * not the number entered is a 
	 * prime number. 
	 */
	
	public static String determinePrimeNumber(int userNumberEntered) {
	      if(userNumberEntered < 2) {
	           return "IS NOT";
	       }
	       int i = 2;
	       while(i < userNumberEntered) {
	           if(userNumberEntered % i==0) {
	               return "IS NOT";
	           }
	           i++;
	       }
	       return "IS";      
	   }
	  
	public static void main(String[] args){
	      try{
	    	   // Used to establish connection between client and server 
	           
	    	   ServerSocket serverSocket = new ServerSocket(8080);
	           Socket clientSocket = serverSocket.accept();
	           DataInputStream inputData = new DataInputStream(clientSocket.getInputStream());
	           DataOutputStream outputData = new DataOutputStream(clientSocket.getOutputStream());
	           
	           /*
	            * This calls the function and returns the
	            * result to the client side.
	            */
	           
	           int userNumberEntered = (int)inputData.readInt();
	           outputData.writeUTF(determinePrimeNumber(userNumberEntered));
	           outputData.flush();

	           outputData.close();
	           serverSocket.close();
	       }
	       catch(Exception e){
	           System.out.println(e);
	       }
	   }

}