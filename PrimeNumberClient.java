package cen3024C;


import java.util.*;
import java.io.*;
import java.net.*;

public class PrimeNumberClient {
	public static void main(String[] args) {
	       
		Scanner scan = new Scanner(System.in);
	       
		try{
	           Socket clientSocket = new Socket("localhost", 8080);
	           DataInputStream inputData = new DataInputStream(clientSocket.getInputStream());
	           DataOutputStream outputData = new DataOutputStream(clientSocket.getOutputStream());
	           
	           /*
	            * This is used for the user to enter
	            * the number entry.
	            */
	           System.out.println("Learn if the number you're thinking about is a prime number!");
	           System.out.print("Enter your number here: ");
	           int userNumberEntered = scan.nextInt(); 
	           
	           /*
	            * This writes the information over on the 
	            * server side. 
	            */
	           outputData.writeInt(userNumberEntered);
	           String result = (String)inputData.readUTF();
	           System.out.println();
	           System.out.println("The number "+ userNumberEntered + " " + result + " a Prime Number.");
	           
	           outputData.flush();
	           outputData.close();
	           clientSocket.close();
	       }
	       catch(Exception e){
	           System.out.println(e);
	       }
	       scan.close();
	   }

}