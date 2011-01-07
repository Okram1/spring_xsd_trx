package com.argility.master.context;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.argility.master.util.MemoryMonitor;

/**
 * 
 * Spring context factory is the entry point into our spring
 * application context, the context is a singleton and will only be
 * instantiated once per JVM
 */
public class SpringContextFactory {

	static Logger log = Logger.getLogger(SpringContextFactory.class.getName());

	private static String SPRING_CONTEXT_PATH = "applicationContext.xml";

	private static ApplicationContext context;
	private static SpringContextFactory instance;

	private SpringContextFactory(ApplicationContext context) {
		SpringContextFactory.context = context;
	}

	private static ApplicationContext getNewApplicationContext() {
		
		// This will add a shutdown hook to the jvm, on jvm shutdown memory statistics will be printed
		new MemoryMonitor();
		
		log.info("\n\n****** Creating a new spring application context ******\n\n");

		Date startDate = new Date();
		
		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { SPRING_CONTEXT_PATH });

		Date endDate = new Date();
		double diff = endDate.getTime() - startDate.getTime();
		double seconds = diff/1000;
		
		log.info("\n\n****** Spring application context initialized in "+ seconds + " seconds ******\n\n");

		return context;

	}

	private static void validateContext() {
		if (context == null || instance == null) {
			instance = new SpringContextFactory(getNewApplicationContext());
		}
	}

	public static ApplicationContext getApplicationContext() {
		validateContext();
		return context;
	}

}
