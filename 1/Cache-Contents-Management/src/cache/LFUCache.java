package cache;

import java.util.HashMap;
import models.Request;

/* This class contains the implementation of a LFU cache limited with a number of slots and one resource = one slot, because the size of the resource is not important.
 * This implementation support two main operations : set and get.
 */

public class LFUCache extends Cache {
	
	private HashMap<String,Request> hashmap;

    /* Constructor
     * @argument : capacity is the maximum number of slots available in the LFU cache.
     */
    public LFUCache(int capacity) {
        this.hashmap = new HashMap<String,Request>(capacity);
        this.capacity = capacity;
    }

    /* This method inserts a Requested object in the data structure,
     * When the cache reached its capacity, it should remove the least frequently used item if needed.
     */
	public void set(Request requestObject) {
		if(this.hashmap.size()>= this.getCapacity()) {
			//remove the least frequently used item
			this.remove();
		}
		//inserting a new item
        hashmap.put(requestObject.getName(),requestObject);
	}

    /* This method removes the element with the least frequency usage
     */
    private void remove() {
        Request r = null;
        //the maximum value that an int can have
        int minFrequency = Integer.MAX_VALUE;
        //find the Request with the least frequency usage
        for(Request requestObject : this.hashmap.values()) {
            if(requestObject.getAccessFrequency() < minFrequency) {
            	minFrequency = requestObject.getAccessFrequency();
                r = requestObject;
            }
        }
        //remove the Request with the least frequency usage
        hashmap.remove(r.getName());
	}

    /* This method returns true if the Requested object was already in the cache,
     * otherwise it returns false and set the Requested object in the cache.
     */
	public boolean get(Request requestObject){
        boolean result = true;
        Request r = this.hashmap.get(requestObject.getName());
        if(r==null) {
        	set(requestObject);
            result = false;
		} else {
          	r.incrementAccessFrequency();
        }
        return result;
	}

    public String printCache() {
         String res = "";
         for (Request ro : this.hashmap.values()) {
             res += ro.getName() + "\n";
         }
         return res;
    }
}

