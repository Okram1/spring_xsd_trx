package com.argility.master.util;

import java.text.NumberFormat;

import org.apache.log4j.Logger;

/**
 * Monitor jvm memory stats
 * @author marko.salic
 *
 */
public class MemoryMonitor {

	protected static Logger log = Logger
		.getLogger(MemoryMonitor.class);
	
	private Runtime runtime = Runtime.getRuntime();
	 
    public MemoryMonitor() {
        hook();
    }
 
    public void hook(){
    	runtime.addShutdownHook(
            new Thread(){
                public void run(){
                	buildMemoryReport();
                }
            }
        );
    }
 
    public void buildMemoryReport(){
    	NumberFormat format = NumberFormat.getInstance();

	    StringBuilder sb = new StringBuilder();
	    long maxMemory = runtime.maxMemory();
	    long allocatedMemory = runtime.totalMemory();
	    long freeMemory = runtime.freeMemory();
	    int availableProcessors = runtime.availableProcessors();

	    sb.append("\n\n");
	    sb.append("JVM MEMORY INFORMATION:\n");
	    sb.append("Used memory: " + format.format((allocatedMemory - freeMemory) / 1024) + "\n");
	    sb.append("Free memory: " + format.format(freeMemory / 1024) + "\n");
	    sb.append("Allocated memory: " + format.format(allocatedMemory / 1024) + "\n");
	    sb.append("Max memory: " + format.format(maxMemory / 1024) + "\n");
	    sb.append("Total free memory: " + format.format((freeMemory + (maxMemory - allocatedMemory)) / 1024) + "\n");
	    sb.append("Available processors: " + availableProcessors + "\n");
	    
	    log.info(sb.toString());
    }

}
