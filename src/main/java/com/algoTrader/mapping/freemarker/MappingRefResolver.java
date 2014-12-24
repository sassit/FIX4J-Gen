package com.algoTrader.mapping.freemarker;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class MappingRefResolver implements TemplateMethodModel {

	private final static Logger LOG = LoggerFactory
			.getLogger(MappingRefResolver.class);
	private static final int CLASS = 0, REF = 1, IS_GET = 2;


	public Object exec(@SuppressWarnings("rawtypes") List args) throws TemplateModelException {
		boolean isGet = "TRUE".equalsIgnoreCase((String) args.get(IS_GET));
		String ref = StringUtils.substringAfter((String) args.get(REF), ".");
		String[] refSplit = StringUtils.split(ref, ".");
		StringBuffer resolved = new StringBuffer();
		for (int i = 0; i < refSplit.length - 1; i++) {
			resolved.append(".get" + StringUtils.capitalize(refSplit[i]) + "()");
		}
		if (isGet == true) {
			resolved.append(".get"
					+ StringUtils.capitalize(refSplit[refSplit.length - 1])+"()");
		} else {
			resolved.append(".set"
					+ StringUtils.capitalize(refSplit[refSplit.length - 1]));
		}
		return resolved.toString();
	}
}
