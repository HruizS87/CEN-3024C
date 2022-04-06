package cen3024C;

import java.util.*;

class Aggregate extends Thread {

private int[] array;

private int highPoint, lowPoint, partial;

public Aggregate(int[] array, int lowPoint, int highPoint)

{

this.highPoint = Math.min(highPoint, array.length);

this.array = array;

this.lowPoint = lowPoint;



}

public int getPartialSum()

{

return partial;

}

public void run()

{

partial = sum(array, lowPoint, highPoint);

}

public static int sum(int[] array)

{

return sum(array, 0, array.length);

}

public static int sum(int[] array, int lowPoint, int highPoint)

{

int total = 0;

for (int i = lowPoint; i < highPoint; i++) {

total += array[i];

}

return total;

}

public static int parallelSum(int[] array)

{

return parallelSum(array, Runtime.getRuntime().availableProcessors());

}

public static int parallelSum(int[] array, int threads)

{

int size = (int) Math.ceil(array.length * 1.0 / threads);

Aggregate[] sums = new Aggregate[threads];

for (int i = 0; i < threads; i++) {

sums[i] = new Aggregate(array, i * size, (i + 1) * size);

sums[i].start();

}

try {

for (Aggregate sum : sums) {

sum.join();

}

} catch (InterruptedException e) { }

int total = 0;

for (Aggregate sum : sums) {

total += sum.getPartialSum();

}

return total;

}

}

public class JavaConcurrency {

public static void main(String[] args)

{

Random random = new Random();

int[] array = new int[200000000];

for (int i = 0; i < array.length; i++) {

	array[i] = random.nextInt(10) + 1;

}

long start = System.currentTimeMillis();

System.out.println(Aggregate.sum(array));

System.out.println("Single: " + (System.currentTimeMillis() - start));
System.out.println("");

start = System.currentTimeMillis();

System.out.println(Aggregate.parallelSum(array));

System.out.println("Parallel: " + (System.currentTimeMillis() - start));

}

}