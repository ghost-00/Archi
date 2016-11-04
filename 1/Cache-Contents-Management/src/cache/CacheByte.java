package cache;

import models.Request;

public abstract class CacheByte extends Cache {
	private long hittedbytes = 0;
	private long totalBytes = 0;
	protected long usedBytes = 0;
	private boolean isWarm = false;

	public void setWarmUp() {
		this.isWarm=true;
	}
	
	public boolean get(Request requestObject) {
		//check if the cache is warm
		if(isWarm){
			this.totalBytes += requestObject.getSize();
		}

		boolean result = false;
	
		if (requestObject.getSize() > this.getCapacity()) {
			removeByte(requestObject);
		} else {
			result = getByte(requestObject);
			if (result && isWarm) {
				hittedbytes += requestObject.getSize();
			}

		}
		
		return result;
	}

	abstract boolean getByte(Request requestObject);
	abstract void removeByte(Request requestObject);

	public long getTotalBytes() {
		return this.totalBytes;
	}

	public long getHittedBytes() {
		return this.hittedbytes;
	}
}
