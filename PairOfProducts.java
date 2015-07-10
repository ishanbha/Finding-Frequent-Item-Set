package default_package;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;

public class PairOfProducts {

	public HashMap<String,Integer> createMap(Scanner file, HashMap<String, Integer> indivProds) {
		int i,j;
		HashMap<String,Integer> pairs = new HashMap<String,Integer>();

		/*	A HashMap containing pairs as TreeSet and their counts is created	*/
		while(file.hasNext()){
			String line = file.nextLine();
			String str[] = line.split(" ");
			if(str.length>1){			//For baskets having more than 1 element
				for(i=0;i<str.length-1;i++){
					for(j=i+1;j<str.length;j++){
						if(indivProds.containsKey(str[i]) && indivProds.containsKey(str[j])){	//ensure each item in the pair is contained in the map storing the individual product count 
							String products = new String();
							if(str[i].compareTo(str[j])>0) 
								products = str[j]+" "+str[i];
							else
								products = str[i]+" "+str[j];

							if(!pairs.containsKey(products))		
								pairs.put(products, 0);
							pairs.put(products, pairs.get(products)+1);	//incrementing count for each item pair

						}
					}
				}
			}
		}

		/*	removing pairs containing pair count less than the minimum support value of 100	*/
		RemoveWords rm = new RemoveWords();
		pairs = rm.rem(pairs);
		
		return pairs;
	}

	/*	Method to compute the confidence for all the products. This method returns a Map containing
	 	Item pairs as a string and their confidence values */
	public HashMap<String,Double> getConfidence(HashMap<String, Integer> pairs, HashMap<String, Integer> indivProds){

		HashMap<String,Double> pairConf = new HashMap<String,Double>();

		for(Entry<String,Integer> e : pairs.entrySet()){
			String products[] = e.getKey().split(" ");

			String prod1 = products[0];	int prodCount1 = indivProds.get(prod1);	//looking up individual count of an item
			String prod2 = products[1];	int prodCount2 = indivProds.get(prod2);	

			double conf1 = (double) e.getValue()/prodCount1;	//Calculating confidence for prod1=>prod2
			double conf2 = (double) e.getValue()/prodCount2;	//Calculating confidence for prod2=>prod1

			pairConf.put(prod1+"=>"+prod2, conf1);	
			pairConf.put(prod2+"=>"+prod1, conf2);
		}

		return pairConf;
	}
}
