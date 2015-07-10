package default_package;

import java.util.HashMap;
import java.util.Scanner;;

public class SingleProducts {

	/**
	 * @param args
	 */

	/*	Creating a HashMap with key as the words and value as their counts	*/
	public HashMap<String,Integer> createMap(Scanner file){

		HashMap<String,Integer> singleWords = new HashMap<String,Integer>();
		while(file.hasNext()){
			String str = file.next();
			if(!singleWords.containsKey(str))
				singleWords.put(str, 0);
			singleWords.put(str, singleWords.get(str)+1);	//incrementing the count for each word encountered
		}

		/*	Removing words with counts less than the minimum support value (100)	*/
		RemoveWords rm = new RemoveWords();
		singleWords = rm.rem(singleWords);
		

		return singleWords;
	}

}
