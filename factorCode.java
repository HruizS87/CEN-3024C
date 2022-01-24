package cop2805;
import java.util.*;

public class factorCode {

public static ArrayList<Integer> Factors(int number){   // Creating ArrayList call method for Factors
	ArrayList<Integer> factors = new ArrayList<>();
	for(int i=1; i<=number/2; i++) {					//Applying for loop to factor down number entered
		if(number% i==0) {								//Applying if statement for factoring conditions
			factors.add(i);
		}
	}
	return factors;
}
	
	
public static ArrayList<Integer> sFactors(ArrayList<Integer> factors, int n){   // Creating ArrayList call method for finding smallest factors
	ArrayList<Integer> sfactors = new ArrayList<>();
	int i =1;
	
	while(n !=1) {
		
		boolean nextNum = false;                                                // Applying boolean operator 
		while(nextNum == false) {												// Applying while loop to go through the factored numbers
			if(n%factors.get(i)==0) {
				nextNum = true;
				sfactors.add(factors.get(i));                                   
				n = n/factors.get(i);
			}
			else {
				i++;
			}
		}
	}
	
	return sfactors;
}

public static void main(String[] args) {
	
	Scanner input = new Scanner(System.in);
	int n = 0;
	ArrayList<Integer> factors, sfactors;
    System.out.print("Enter number to be factored: ");
    n = input.nextInt();
    System.out.println("");
    factors = Factors(n);
    System.out.println("Factors: " + factors);
    sfactors = sFactors(factors, n);
    System.out.println("Smallest Factor: " + sfactors);
    
	


}
}