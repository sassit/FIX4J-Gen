package com.algoTrader.mapping.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MappingConfig {

	private final static Logger LOG = LoggerFactory
			.getLogger(MappingConfig.class);

	private Properties properties;

	private MappingConfig() {
	}

	public static MappingConfig createMappingConfig() {
		MappingConfig config = new MappingConfig();
		config.readProperties();
		return config;

	}

	public void readProperties() {
		properties = new Properties();
		try {
			properties.load(new FileInputStream("mapping.properties"));
		} catch (IOException e) {
			LOG.error("Could not load property files.", e);
		}
	}

	public String getProperty(String key) {
		return properties.getProperty(key);
	}
}
