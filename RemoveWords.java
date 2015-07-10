package default_package;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class RemoveWords {
	public HashMap<String, Integer> rem(HashMap<String,Integer> map){
		for(Iterator<Entry<String,Integer>> it = map.entrySet().iterator();it.hasNext();){
			Entry<String,Integer> e = it.next(); 
			if(e.getValue()<100)
				it.remove();
		}
		return map;
	}
}
