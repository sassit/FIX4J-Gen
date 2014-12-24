package com.algoTrader.mapping.freemarker;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import com.algoTrader.mapping.config.MappingConfig;
import com.algoTrader.mapping.type.TypeConfigReader;
import com.algoTrader.mapping.type.TypeReader;

public class MappingProcessorTest {

	@Test
	public void testJavaMapperGeneration() {
		TypeReader reader = TypeConfigReader.createDefaultTypeConfigReader();
		MappingConfig config = MappingConfig.createMappingConfig();
		MappingProcessor processor = MappingProcessor.createMappingProcessor(
				reader, config);
		processor.processMappingFiles();
		Collection<File> files = FileUtils.listFiles(
				new File(config.getProperty("javaSourceDir")),
				new String[] { "java" }, true);
		// Check that there is a MessageMapper.java for every fix version later
		// ...
		assertTrue(files.size() > 0);
	}
}
