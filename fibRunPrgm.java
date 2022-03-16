package cop2805;

import java.util.*;
import java.lang.*;

class recFib extends Thread {  // Recursive Fibonacci
	public static int Fibonacci(int n) {
		if(n==0) return 0;
		if(n==1) return 1;
		return Fibonacci(n-1)+Fibonacci(n-2);
		
	}
	@Override
	public void run() {
		long timeTrack = System.nanoTime();
		int fibNum = Fibonacci(30);
		timeTrack = System.nanoTime() - timeTrack;
		System.out.println("Recursive result: " + fibNum + "\nRuntime: " + timeTrack);
	}
}

class IteFib extends Thread{ // Iterative Fibonacci 
	public static int Fibonacci(int n) {
		int v1=0, v2=1, v3=0;
		for (int i=2; i<=n; i++) {
			v3 = v1 + v2;
			v1 = v2;
			v2 = v3;
			
		}
		return v3;
	}
	@Override
	public void run() {
		long timeTrack = System.nanoTime();
		int fibNum = Fibonacci(30);
		timeTrack = System.nanoTime() - timeTrack;
		System.out.println("Iterative result: " + fibNum + "\nRuntime: " + timeTrack +"\n");
	
	}
}

	public class fibRunPrgm {
		public static void main (String[] args) {
			recFib rf = new recFib();
			rf.start();
			IteFib it = new IteFib();
			it.start();
			
			
		}
	}
