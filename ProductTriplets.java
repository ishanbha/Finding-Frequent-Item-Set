package default_package;

import java.util.HashMap;
import java.util.Scanner;
import java.util.Map.Entry;


public class ProductTriplets {

	public HashMap<String,Integer> createMap(HashMap<String,Integer> pair, HashMap<String,Integer> single,Scanner file){

		int i,j,k;
		String two;
		HashMap<String, Integer> triplets = new HashMap<String, Integer>();

		while(file.hasNext()){
			String line = file.nextLine();
			String str[] = line.split(" ");
			if(str.length > 2){	//Length greater than will only have triplets
				for(i=0;i<str.length-1;i++){	
					for(j=i+1;j<str.length;j++){		// constructing pairs


						if(str[i].compareTo(str[j]) > 0)		//	storing pairs in alphabetical order to avoid multiple similar pairs
							two = str[j]+" "+str[i];
						else
							two = str[i]+" "+str[j];
						if(pair.containsKey(two)){	// ensuring pairs exist in the pair HashMap
							for(k=0;k<str.length;k++){	
								if(!str[k].equalsIgnoreCase(str[j]) && !str[k].equalsIgnoreCase(str[i]) && single.containsKey(str[k])){	// ensuring the third item is not in the pair and it has count more than minimum support
									String three = new String();
									three = two + "=>" + str[k];
									if(!triplets.containsKey(three))
										triplets.put(three, 0);			// Initializing count
									triplets.put(three, triplets.get(three)+1);
								}
							}
						}
					}
				}
			}

		}
		/*	removing pairs containing pair count less than the minimum support value of 100	*/
		RemoveWords rm = new RemoveWords();
		triplets = rm.rem(triplets);


		return triplets;

	}
	
	public HashMap<String, Double> getConfidence(HashMap<String, Integer> triplets, HashMap<String, Integer> pairs){
		
		HashMap<String,Double> tripConf = new HashMap<String,Double>();
		
		for(Entry<String,Integer> e : triplets.entrySet()){
			String products[] = e.getKey().split("=>");
			String prodPair = products[0];	int pairCount = pairs.get(prodPair);
			String item = products[1];
			
			double conf = (double) e.getValue()/pairCount;
			tripConf.put("["+prodPair+"]=>"+item, conf);
		}
	
		return tripConf;
	}
}
