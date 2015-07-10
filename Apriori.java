package default_package;

import java.io.FileReader;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeMap;
import java.util.Map.Entry;

public class Apriori {

	/**
	 * @param args
	 */
	/*	Custom comparator. Sorting the tree map values wise in descending order	*/
	static class ValueComparator implements Comparator<String> {

		HashMap<String, Double> base;
		public ValueComparator(HashMap<String, Double> conf) {
			this.base = conf;
		}

		// Note: this comparator imposes orderings that are inconsistent with equals.    
		public int compare(String a, String b) {
			if (base.get(a) >= base.get(b)) {
				return -1;
			}
			
			else {
				return 1;
			} // returning 0 would merge keys
		}
	}
	public static void main(String[] args) throws Exception {


		/* Hashmap 'prods' stores the individual word count. Hashmap 'pairs' stores the pair word count.
		  TreeSet is used for keeping item pairs  */
		Scanner file = new Scanner(new FileReader("browsing.txt"));

		/* Individual word count	*/
		SingleProducts sp = new SingleProducts();
		HashMap<String,Integer> prods = sp.createMap(file);

		/* Re-initializing the Scanner object */
		file.close();
		file = null;
		file = new Scanner(new FileReader("browsing.txt"));

		/* Finding pairs and storing their count */
		PairOfProducts pp = new PairOfProducts();
		HashMap<String,Integer> prodPairs = pp.createMap(file, prods);	// Contains product pairs and their count
		HashMap<String,Double> pairConf = pp.getConfidence(prodPairs, prods);	//Contains confidence values of the pairs
		ValueComparator cmp = new ValueComparator(pairConf);	//defined in this class. Custom comparator used for sorting TreeMap by values
		TreeMap<String,Double> conf2 = new TreeMap<String,Double>(cmp);	//Sorting the key,value pair sorted
		conf2.putAll(pairConf);

		/* Prints top 10 Item Pairs and their confidence value sorted in descending order	*/
		int i=0;
		System.out.println("Top 30 two itemset with confidence sorted in descending order\n");
		for(Entry<String,Double> e : conf2.entrySet()){
			System.out.println(e.getKey().toString()+"  "+e.getValue());
			i++;
			if(i>=30)
				break;
		}
		/* Re-initializing the Scanner object */
		file.close();
		file = null;
		file = new Scanner(new FileReader("browsing.txt"));
		
		/*	Same as above. Getting the count then confidence and sorting it value wise
		 * in descending order	*/
		ProductTriplets pt = new ProductTriplets();
		HashMap<String, Integer> prodTriplets = pt.createMap(prodPairs, prods,file);
		HashMap<String,Double> tripConf = pt.getConfidence(prodTriplets, prodPairs);
		ValueComparator vc = new ValueComparator(tripConf);
		TreeMap<String,Double> conf3 = new TreeMap<String, Double>(vc);
		conf3.putAll(tripConf);
		
		i=0;
		
		System.out.println("\n\nTop 30 three itemset with confidence sorted in descending order\n");
		for(Entry<String,Double> e: conf3.entrySet()) {
			System.out.println(e.getKey().toString()+"  "+e.getValue());
			i++;
			if(i>=30)
				break;
		}
	
	}

}
