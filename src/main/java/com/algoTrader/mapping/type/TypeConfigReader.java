package com.algoTrader.mapping.type;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.io.FilenameUtils;

public class TypeConfigReader implements TypeReader {

	private String baseDir, xmlTypeMapFile, fieldMapperXMLPath;
	private File fieldMapperFile;

	private TypeConfigReader(String baseDir, String xmlTypeMapFile) {
		this.baseDir = baseDir;
		this.xmlTypeMapFile = xmlTypeMapFile;
	}

	public static TypeConfigReader createTypeConfigReader(String baseDir,
			String xmlTypeMapFile) {
		TypeConfigReader reader = new TypeConfigReader(baseDir, xmlTypeMapFile);
		reader.init();
		return reader;
	}

	public static TypeConfigReader createDefaultTypeConfigReader() {
		TypeConfigReader reader = new TypeConfigReader("other",
				"type_mapper.xml");
		reader.init();
		return reader;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.algoTrader.mapping.type.TypeReader#init()
	 */
	public void init() {
		fieldMapperXMLPath = ClassLoader.getSystemResource(baseDir).getPath();
		fieldMapperFile = new File(FilenameUtils.concat(fieldMapperXMLPath,
				xmlTypeMapFile));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.algoTrader.mapping.type.TypeReader#getTypeMappingAsList()
	 */
	public List<Type> getTypeMappingAsList() {
		JAXBContext ctx;
		TypeMap typeMap = null;
		try {
			ctx = JAXBContext.newInstance(new Class[] { TypeMap.class });
			Unmarshaller um = ctx.createUnmarshaller();
			typeMap = (TypeMap) um.unmarshal(fieldMapperFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		return (typeMap.getType() == null) ? Collections.<Type> emptyList()
				: typeMap.getType();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.algoTrader.mapping.type.TypeReader#getTypeMappingAsMap()
	 */
	public Map<String, Type> getTypeMappingAsMap() {
		final Map<String, Type> typeMap = new HashMap<String, Type>();
		List<Type> typeList = getTypeMappingAsList();
		for (Type type : typeList) {
			typeMap.put(type.fix, type);
		}
		return typeMap;
	}
}
