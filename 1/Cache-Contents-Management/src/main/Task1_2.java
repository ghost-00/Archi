package main;

import cache.*;
import models.Request;
import utils.TraceParser;

import java.io.FileNotFoundException;
import java.text.DecimalFormat;

public class Task1_2{
	private static int warmup;
	private static int byteSize;
	private static TraceParser traceParser = null;

	public static void main(String[] args) throws FileNotFoundException {
		//get the number of the request for the warmup
		warmup = Integer.parseInt(args[0]);
		//get the byte size of the cache
		byteSize = Integer.parseInt(args[1]);
		//parse the trace file
		traceParser = new TraceParser(System.in);
		computeHitRate();
	}
	
	/*
	 * this method compute the hit rate of each cache strategy : LFU, LRU & RLF
	 */
	public static void computeHitRate() {
		//initialization of the LRU cache
		LRUCacheByte cacheLFUbytes = new LRUCacheByte(byteSize);
		//initialization of the LFU cache 
		LFUCacheByte cacheLRUbytes = new LFUCacheByte(byteSize);
		//initialization of the RLF cache
		RLFCache cacheRLF = new RLFCache(byteSize);
		
		//warmup the cache
		for (int i = 0 ; i < warmup ; i++) {
			Request requestObject = traceParser.getRequest();
			if (requestObject == null) break;
			cacheLRUbytes.get(requestObject);
			cacheLFUbytes.get(requestObject);
			cacheRLF.get(requestObject);
		}
		
		//set warmup
		cacheLFUbytes.setWarmUp();
		cacheLRUbytes.setWarmUp();
		cacheRLF.setWarmUp();
		
		Request requestObject = null;
		int requestNumb = 0, hitsLRU = 0, hitsLFU = 0, hitsRLF = 0;

		while((requestObject = traceParser.getRequest()) != null) {
			if (cacheLRUbytes.get(requestObject)) {
				hitsLRU++;
			}
			if (cacheLFUbytes.get(requestObject)) {
				hitsLFU++;
			}
			if (cacheRLF.get(requestObject)) {
            	hitsRLF++;
           	}
			requestNumb++;
		}

		//compute the hitrate
		double hitrateLRU = (double)hitsLRU / (double)requestNumb;
		double bytesrateLRU = (double) cacheLRUbytes.getHittedBytes() / (double) cacheLRUbytes.getTotalBytes();
		double hitrateLFU = (double)hitsLFU / (double)requestNumb;
		double bytesrateLFU = (double) cacheLFUbytes.getHittedBytes() / (double) cacheLFUbytes.getTotalBytes();
		double hitrateRLF = (double)hitsRLF / (double)requestNumb;
		double bytesrateRLF = (double) cacheRLF.getHittedBytes() / (double) cacheRLF.getTotalBytes();
		
		System.out.println("");
		System.out.println("LRU Hit rate: " + roundDouble(hitrateLRU));
		System.out.println("LRU Byte hit rate: " + roundDouble(bytesrateLRU));
		System.out.println("LFU Hit rate: " + roundDouble(hitrateLFU));
		System.out.println("LFU Byte hit rate: " +  roundDouble(bytesrateLFU));
		System.out.println("Size-based hit rate: " + roundDouble(hitrateRLF));
		System.out.println("Size-based Byte hit rate: " + roundDouble(bytesrateRLF));
		System.out.println("");
		
		traceParser.print("cache_lru.txt",cacheLRUbytes.printCache());
		traceParser.print("cache_lfu.txt",cacheLFUbytes.printCache());
		traceParser.print("cache_size-based.txt", cacheRLF.printCache());
	}

	public static String roundDouble(double d){
		DecimalFormat formatter = new DecimalFormat("0.00%");
		return formatter.format(d);
	}
}
