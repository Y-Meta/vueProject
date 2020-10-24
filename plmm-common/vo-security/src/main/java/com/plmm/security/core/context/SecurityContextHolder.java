package com.plmm.security.core.context;

import java.lang.reflect.Constructor;

import org.springframework.util.ReflectionUtils;

public class SecurityContextHolder {
	public static final String MODE_THREADLOCAL = "MODE_THREADLOCAL";
	public static final String MODE_INHERITABLETHREADLOCAL = "MODE_INHERITABLETHREADLOCAL";
	public static final String MODE_GLOBAL = "MODE_GLOBAL";
	public static final String SYSTEM_PROPERTY = "spring.security.strategy";
	private static String strategyName = System.getProperty("spring.security.strategy");
	private static SecurityContextHolderStrategy strategy;
	private static int initializeCount = 0;

	public static void clearContext() {
		strategy.clearContext();
	}

	public static SecurityContext getContext() {
		return strategy.getContext();
	}

	public static int getInitializeCount() {
		return initializeCount;
	}

	private static void initialize() {
		if ((strategyName == null) || ("".equals(strategyName))) {
			strategyName = "MODE_THREADLOCAL";
		}

		if (strategyName.equals("MODE_THREADLOCAL")) {
			strategy = new ThreadLocalSecurityContextHolderStrategy();
		} else {
			try {
				Class clazz = Class.forName(strategyName);
				Constructor customStrategy = clazz.getConstructor(new Class[0]);
				strategy = (SecurityContextHolderStrategy) customStrategy.newInstance(new Object[0]);
			} catch (Exception ex) {
				ReflectionUtils.handleReflectionException(ex);
			}
		}

		initializeCount += 1;
	}

	public static void setContext(SecurityContext context) {
		strategy.setContext(context);
	}

	public static void setStrategyName(String strategyName) {
		strategyName = strategyName;
		initialize();
	}

	public static SecurityContextHolderStrategy getContextHolderStrategy() {
		return strategy;
	}

	public static SecurityContext createEmptyContext() {
		return strategy.createEmptyContext();
	}

	public String toString() {
		return "SecurityContextHolder[strategy='" + strategyName + "'; initializeCount=" + initializeCount + "]";
	}

	static {
		initialize();
	}
}