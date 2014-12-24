package com.algoTrader.mapping.xslt;

import java.io.File;
import java.io.IOException;

import javax.xml.transform.Source;
import javax.xml.transform.SourceLocator;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;

public class FIXPreprocessor {

	private final String xmlFileName, xsltFileName;
	private File xmlFile, xsltFile;
	private String templatesDir, outputDir, fixDir;

	public FIXPreprocessor(String xmlFileName, String xsltFileName) {
		this.xmlFileName = xmlFileName;
		this.xsltFileName = xsltFileName;
	}

	public void init() {
		templatesDir = ClassLoader.getSystemResource("xslt").getPath();
		fixDir = ClassLoader.getSystemResource("fix").getPath();
		xmlFile = new File(FilenameUtils.concat(fixDir, xmlFileName));
		xsltFile = new File(FilenameUtils.concat(templatesDir, xsltFileName));
		String xmlFileNameBaseName = StringUtils.substringBefore(xmlFileName,
				".");
		outputDir = FilenameUtils.concat("output",
				StringUtils.lowerCase(xmlFileNameBaseName));
		try {
			FileUtils.deleteDirectory(new File(outputDir));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void process() {
		// JAXP reads data using the Source interface
		Source xmlSource = new StreamSource(xmlFile);
		Source xsltSource = new StreamSource(xsltFile);
		// the factory pattern supports different XSLT processors
		TransformerFactory transFact = TransformerFactory.newInstance();
		Transformer trans;
		try {
			trans = transFact.newTransformer(xsltSource);
			trans.setParameter("outputDir", outputDir + "/");
			trans.setParameter("namespace", "http://www.algotrader.org/fix");
			trans.transform(xmlSource, new StreamResult(System.out));
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}
}
