package com.argility.master.service;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * @author marko.salic Spring context factory is the entry point into our spring
 *         application context, the context is a singleton and will ony be
 *         instantiated once
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
		log.info("\n\n****** Creating a new spring application context ******\n\n");

		ApplicationContext context = new ClassPathXmlApplicationContext(
				new String[] { SPRING_CONTEXT_PATH });

		log.info("\n\n****** Spring application context initialized ******\n\n");

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
