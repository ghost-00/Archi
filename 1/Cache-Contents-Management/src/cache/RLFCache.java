package cache;

import java.util.HashMap;

import models.Request;

/* This class implements a size-based cache Removing the Largest File in
 * the cache first.
 */

public class RLFCache extends CacheByte {
    private HashMap<String, Request> hashMap; 

    public RLFCache(int capacity) {
    	this.hashMap = new HashMap<String, Request>();
    	this.capacity = capacity;
    }

    /*This method add an element to the cache, if there is no space
     *the largest file will be remove.
     */
    public void set(Request requestObject) {
    	while (requestObject.getSize() + usedBytes > capacity) {
    		int maxSize = Integer.MIN_VALUE;
    		Request ro = null;
            for(Request request : this.hashMap.values()) {
                if(request.getSize() > maxSize) {
                    maxSize = request.getSize();
                    ro = request;
                }
            }
            hashMap.remove(ro.getName());
            usedBytes -= ro.getSize();
    	}
    	hashMap.put(requestObject.getName(), requestObject);
    	usedBytes += requestObject.getSize();
    }

    /*This method remove an element already present in the cache
     * but which is requested with a new size.
     */
    public void removeByte(Request requestObject){
      if(this.hashMap.containsKey(requestObject.getName())) {
        this.hashMap.remove(requestObject.getName());
        this.usedBytes -= requestObject.getSize();
      }
   }

     /*This method is used to get an element in the cache and if it's not 
     * in the cache, it will be added
     */
    public boolean getByte(Request requestObject) {
        boolean result = false;
    	Request ro = hashMap.get(requestObject.getName());
    	if (ro == null) {
 			set(requestObject);
    	} else {
    		if (ro.getSize() != requestObject.getSize()) {
    			hashMap.remove(ro);
    			this.usedBytes -= ro.getSize();
    			set(requestObject);
    		} else {
	    		result = true;
	    	}
    	}
    	return result;
    }

    public String printCache() {
         String res = "";
         for (Request ro : this.hashMap.values()) {
              res += ro.getName() + "\n";
         }
         return res;
    }
}
