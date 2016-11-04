package cache;

import java.util.HashMap;

import models.Request;

/* This class implement the LFU cache size-based. the capacity is expressed in bytes
 */
public class LFUCacheByte extends CacheByte {
	private HashMap<String, Request> hashMap; 

	/* Constructor
	 * @argument : capacity is the maximum number bytes available in the LFU cache.
	 */
	public LFUCacheByte(int capacity) {
		this.hashMap = new HashMap<String, Request>();
		this.capacity = capacity;
	}

	/* This method inserts a Requested object in the data structure. 
	 * When the cache reached its capacity, it will remove item until there is enough space.
	 */
	public void set(Request requestObject) {
		//remove item until there is anough space
		while (requestObject.getSize() + usedBytes > this.getCapacity()) {
			//remove the least frequently used item
			remove();
		}
		//inserting a new item
		hashMap.put(requestObject.getName(), requestObject);
		//update the bytes used
		this.usedBytes += requestObject.getSize();
	}

	/* This method removes the element with the least frequency usage
     */
	public void remove() {
		Request r = null;
		//the maximum value that an int can have
		int min = Integer.MAX_VALUE;
		//find the Request with the least frequency usage
		for (Request requestObject : this.hashMap.values()) {
			if (requestObject.getAccessFrequency() < min) {
				min = requestObject.getAccessFrequency();
				r = requestObject;
			}
		}
		//remove the Request with the least frequency usage
		hashMap.remove(r.getName());
		//update the bytes used
		this.usedBytes -= r.getSize();
	}
	
	/* This method returns true if the Requested object was already in the cache (same name and same size),
     * otherwise it returns false and set the Requested object in the cache.
     */
	public boolean getByte(Request requestObject) {
		boolean result = true;
		Request r = this.hashMap.get(requestObject.getName());
		if (r == null) {
			set(requestObject);
			result = false;
		} else {
			//if the size of the request is different
			if (r.getSize() != requestObject.getSize()) {
				hashMap.remove(r.getName());
				this.usedBytes -= r.getSize();
				set(requestObject);
				result = false;
			} else {
				r.incrementAccessFrequency();
			}
		}
		return result;
	}

	/*This method remove an element already present in the cache
	 * but which is requested with a new size.
	 */
	public void removeByte(Request requestObject) {
		if(this.hashMap.containsKey(requestObject.getName())){
			hashMap.remove(requestObject.getName());
			this.usedBytes -= requestObject.getSize();
		}
	}

	public String printCache() {
		String res = "";
		for (Request ro : this.hashMap.values()) {
			res += ro.getName() + "\n";
		}
		return res;
	}

}
