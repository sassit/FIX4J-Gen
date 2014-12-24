package com.algoTrader.mapping.xslt;

import static org.junit.Assert.assertNotNull;

import java.io.File;
import java.util.Collection;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

public class FIXPreprocessorTest {

	@Test
	public void testConstructor() throws Exception {
		FIXPreprocessor processor = new FIXPreprocessor("someFile", "someFile");
		assertNotNull(processor);
	}

	@Test
	public void testCanProcessFIX44() throws Exception {
		FIXPreprocessor processor = new FIXPreprocessor("FIX44.xml",
				"fix_mapping_normaliser.xsl");
		processor.init();
		processor.process();
	}

	@Test
	public void testCanProcessAllFIX() throws Exception {
		String fixDir = ClassLoader.getSystemResource("fix").getPath();
		Collection<File> files = FileUtils.listFiles(new File(fixDir),
				new String[] { "xml" }, false);
		for (File file : files) {
			FIXPreprocessor processor = new FIXPreprocessor(file.getName(),
					"fix_mapping_normaliser.xsl");
			processor.init();
			processor.process();
		}
	}
}
