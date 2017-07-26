package priceComparison.services;

import java.util.Map;

public class MapGetter {
	public static Integer getKeyFromValue(Map<Integer, String> generalMap, String value) {
	    for (Integer o : generalMap.keySet()) 
	    {
	    	if (generalMap.get(o).equals(value)) { return o; }
	    }
	    return null;
	  }
}
