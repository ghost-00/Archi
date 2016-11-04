package cache;

import java.util.LinkedList;

import models.Request;

/* This class implement the LRU cache size-based. the capacity is expressed in bytes
 */
public class LRUCacheByte extends CacheByte {

	private LinkedList<Request> linkedlist;

	/* Constructor
	 * @argument : capacity is the maximum number of slots available in the LRU cache.
	 */
	public LRUCacheByte(int capacity) {
		this.linkedlist = new LinkedList<Request>();
		this.capacity = capacity;
	}

	/* This method inserts the Requested object in data structure representing the cache. 
	 * When the cache reached its capacity, it removes the least recently used item.
	 */
	public void set(Request requestObject) {
		//until there is anough of space
		while (this.usedBytes + requestObject.getSize() > this.getCapacity()) {
			//remove the least recently used item
			Request r = linkedlist.removeFirst();
			//update the used bytes
			this.usedBytes -= r.getSize();
		}
		//inserting a new item
		linkedlist.addLast(requestObject);
		//update the used bytes
		this.usedBytes += requestObject.getSize();
	}
	
	/* This method returns true if the Requested object exists in the
     * cache with the same size, otherwise it returns false and put the Requested object in the LRU cache.
     */
	public boolean getByte(Request requestObject) {
		boolean result = false;
		if (this.linkedlist.contains(requestObject)) {
			Request r = linkedlist.get(linkedlist.indexOf(requestObject));
			//if the size of the request is different
			if (requestObject.getSize() != r.getSize()) {
				result = false;
			} else {
				result = true;
			}
			this.linkedlist.remove(r);
			this.usedBytes -= r.getSize();
		}
		set(requestObject);
		return result;
	}

	/*This method remove an element already present in the cache but which is requested with a new size.
	 */
	public void removeByte(Request requestObject){
		if(this.linkedlist.contains(requestObject)){
			linkedlist.remove(requestObject);
			this.usedBytes -= requestObject.getSize();
		}
	}

	public String printCache() {
		String res = "";
		for (int i = 0; i < this.linkedlist.size() ; i++) {
			res += this.linkedlist.get(i).getName() + "\n";
		}
		return res;
	}
}

