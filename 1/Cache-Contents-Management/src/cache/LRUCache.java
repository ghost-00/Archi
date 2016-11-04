package cache;

import java.util.LinkedList;

import models.Request;

/* This class implements a LRU cache limited with a number of slots and one resource = one slot, because the size of the resource is not important.
 * This implementation support two main operations : set and get. 
 */

public class LRUCache extends Cache {

    private LinkedList<Request> linkedlist;

    /* Constructor
     * @argument : capacity is the maximum number of slots available in the LRU cache.
     */
    public LRUCache(int capacity) {
        this.linkedlist = new LinkedList<Request>();
        this.capacity = capacity;
    }
    
    /* This method inserts the Requested object in data structure representing the cache. 
     * When the cache reached its capacity, it should remove the least recently used item if needed.
     */
    public void set(Request requestObject) {
        if(this.linkedlist.size()>= this.getCapacity()) {
        	//remove the least recently used item 
            linkedlist.removeFirst();
        }
        //inserting a new item
        linkedlist.addLast(requestObject);
    }

    /* This method returns true if the Requested object exists in the
     * cache, otherwise it returns false and put the Requested object in the
     * LRU cache.
     */
    public boolean get(Request requestObject) {
        boolean result = false;
        if(this.linkedlist.contains(requestObject)) {
            result = true;
            this.linkedlist.remove(requestObject);
        }
      	set(requestObject);
        return result;
    }

    public String printCache() {
        String cache = "";
        for (int i = 0 ; i < this.linkedlist.size() ; i++) {
             cache += this.linkedlist.get(i).getName() + "\n";
        }
        return cache;
    }
}
