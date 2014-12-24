package com.algoTrader.mapping.resolver;

import java.lang.reflect.Method;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ObjectGraphResolver {

	private final static Logger LOG = LoggerFactory
			.getLogger(ObjectGraphResolver.class);
	private String clazzPath, propertyRef;
	private StringBuffer position, accessors;
	private boolean isGetter;

	public ObjectGraphResolver(String clazzPath, String propertyRef,
			boolean isGetter) {
		this.clazzPath = clazzPath;
		this.propertyRef = propertyRef;
		this.isGetter = isGetter;
		init();
	}

	private void init() {
		position = new StringBuffer();
		accessors = new StringBuffer();
	}

	public void resolvePath() {
		String[] splitted = StringUtils.split(propertyRef, ".");
		Class clazz = null;
		Method method = null;
		try {
			clazz = Class.forName(clazzPath);
		} catch (ClassNotFoundException e) {
			LOG.error("Class {} not found, make sure it's on the classpath.",
					clazzPath, e);
		}
		for (int i = 0; i < splitted.length; i++) {
			try {
				method = clazz.getMethod(
						"get" + StringUtils.capitalize(splitted[i]), null);
			} catch (SecurityException e) {
				LOG.error("Current resolved path: {}", position.toString(), e);
			} catch (NoSuchMethodException e) {
				LOG.error("Current resolved path: {}, method not found: {}",
						new Object[] { position.toString(), splitted[i], e });
				determineSetterCall(clazz, splitted[i]);
			}
			clazz = buildTraversalResult(method, splitted[i]);
			if (clazz == null)
				break;
		}
		LOG.debug("Accessor graph resolved to: {}", accessors.toString());
	}

	private void determineSetterCall(Class clazz, String methodName) {
		if (isGetter == true)
			return;
		else {
			Method method;
			try {
				method = clazz.getMethod(
						"set" + StringUtils.capitalize(methodName), null);
				buildTraversalResult(method, methodName);
			} catch (SecurityException e) {
				LOG.error("Current resolved path: {}", position.toString(), e);
			} catch (NoSuchMethodException e) {
				LOG.error("Current resolved path: {}, method not found: {}",
						new Object[] { position.toString(), methodName, e });
			}
		}
	}

	private Class buildTraversalResult(Method method, String methodName) {
		position.append(methodName + ".");
		accessors.append("." + method.getName() + "()");
		LOG.debug("Current path position: {}", position.toString());
		return method.getReturnType();
	}

	public static boolean isGetter(Method method) {
		if (method.getParameterTypes().length != 0)
			return false;
		if (void.class.equals(method.getReturnType()))
			return false;
		return true;
	}

	public static boolean isSetter(Method method) {
		if (method.getParameterTypes().length != 1)
			return false;
		return true;
	}
}
