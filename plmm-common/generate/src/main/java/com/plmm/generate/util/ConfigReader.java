package com.plmm.generate.util;

import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConfigReader {

	private static Logger logger = LoggerFactory.getLogger(ConfigReader.class);
	private static ConfigReader instance;
	Properties prop = new Properties();

	private ConfigReader() throws IOException {
		prop.load(this.getClass().getClassLoader().getResourceAsStream("config.properties"));
	}

	public synchronized static ConfigReader getInstance() {
		if (instance == null) {
			try {
				instance = new ConfigReader();
			} catch (IOException e) {
				logger.error("", e);
			}
		}
		return instance;
	}

	public String getProp(String key) {
		return prop.getProperty(key);
	}
}
