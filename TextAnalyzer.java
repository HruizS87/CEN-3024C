package cen3024;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class TextAnalyzer
{
    public static void main(String[] args) throws FileNotFoundException {
        /** Reading file line by line */
        File file = new File("C:\\Users\\hruiz\\eclipse-workspace\\TextAnalyzerHW\\TextAnalyzer\\src\\cen3024\\raven.txt");
        Scanner scan = new Scanner(file);
        /**
         * map to store key value pair
         * key : word
         * value: frequency of the word
         */
    	List<String> al = new ArrayList<String>();
        Map<String,Integer> map = new HashMap<String, Integer>(); 
        while (scan.hasNextLine())
        {
            String val = scan.nextLine(); // reading line by line
        	String str[] = val.split(" ");
        	for(String word: str)
        		if(word != null && word != " " && word.length() > 1)
        			al.add(word);
        	for(String value: al) {
            if(map.containsKey(value) == false) // if the string is not inserted in the map yet then insert by setting the frequency as 1
                map.put(value,1);
            else // otherwise remove the entry from map and again insert by adding 1 in the frequency
            {
                int count = (int)(map.get(value)); // finding the current frequency of the word
                map.remove(value);  // removing the entry from the map
                map.put(value,count+1); // reinserting the word and increase frequncy by 1
            }
        }
        }
        map.remove(" ");
        Set<Map.Entry<String, Integer>> set = map.entrySet(); // retrieving the map contents
        List<Map.Entry<String, Integer>> sortedList = new ArrayList<Map.Entry<String, Integer>>(set); // make an array list 
        Collections.sort( sortedList, new Comparator<Map.Entry<String, Integer>>() // sorting the array list
        {
            public int compare( Map.Entry<String, Integer> a, Map.Entry<String, Integer> b ) // comparator function for sorting
            {
                return (b.getValue()).compareTo( a.getValue() ); // for descending order 
//                return (a.getValue()).compareTo( b.getValue() ); // for ascending order 
            }
        } );
        // printing the list
        System.out.println("Print list");
        System.out.println();
        
        for(Map.Entry<String, Integer> i:sortedList){
        	
            System.out.println(i.getKey()+" -> "+i.getValue());
        }
        
        System.out.println();
        System.out.println("Print top 20 words");
      

        int j=0;
        for(Map.Entry<String, Integer> i:sortedList){
        	if(j==20)
        		break;
            System.out.println(i.getKey()+" "+i.getValue());
            j++;
        }
       
    }
}