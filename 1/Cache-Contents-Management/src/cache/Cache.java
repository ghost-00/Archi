package cache;

import java.util.ArrayList;
import java.util.List;

import models.Request;

public abstract class Cache {
    public int capacity = 0;
    public List<String> sizeChangingReq = new ArrayList<String>();
    
    abstract void set(Request requestObject);
    abstract boolean get(Request requestObject);
    //print the cache
    abstract String printCache();
    
    //return the capacity of the cache
    public int getCapacity() {
        return this.capacity;
    }
}