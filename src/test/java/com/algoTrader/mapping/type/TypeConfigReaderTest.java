package com.algoTrader.mapping.type;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TypeConfigReaderTest {

	@Test
	public void testConstructor() {
		TypeReader reader = TypeConfigReader.createTypeConfigReader("other", "type_mapper.xml");
		assertNotNull(reader);
	}

	@Test
	public void testTypeListReads() throws Exception {
		TypeReader reader = TypeConfigReader.createTypeConfigReader("other", "type_mapper.xml");
		reader.init();
		List<Type> types = reader.getTypeMappingAsList();
		assertTrue(types.size() > 0);
	}

	@Test
	public void testTypeMapReads() throws Exception {
		TypeReader reader = TypeConfigReader.createTypeConfigReader("other", "type_mapper.xml");
		reader.init();
		Map<String, Type> types = reader.getTypeMappingAsMap();
		assertTrue(types.size() > 0);
	}
	
	@Test
	public void testTypeMapEntry() throws Exception {
		TypeReader reader = TypeConfigReader.createTypeConfigReader("other", "type_mapper.xml");
		reader.init();
		Map<String, Type> types = reader.getTypeMappingAsMap();
		assertTrue(types.keySet().contains("STRING"));
	}
}
