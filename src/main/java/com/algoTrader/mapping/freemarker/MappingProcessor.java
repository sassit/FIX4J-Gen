package com.algoTrader.mapping.freemarker;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import com.algoTrader.mapping.config.MappingConfig;
import com.algoTrader.mapping.type.Type;
import com.algoTrader.mapping.type.TypeReader;

import freemarker.core.StopException;
import freemarker.template.Configuration;
import freemarker.template.ObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

public class MappingProcessor {

	private final static Logger LOG = LoggerFactory
			.getLogger(MappingProcessor.class);

	private Map<String, Object> root;
	private File templatesDirFile, mappingDirFile;
	private MappingConfig config;
	private TypeReader reader;

	private MappingProcessor() {
	}

	private MappingProcessor(TypeReader reader, MappingConfig config) {
		this.reader = reader;
		this.config = config;
		this.root = new HashMap<String, Object>();
	}

	public static MappingProcessor createMappingProcessor(TypeReader reader,
			MappingConfig config) {
		MappingProcessor processor = new MappingProcessor(reader, config);
		processor.init();
		return processor;
	}

	public void init() {
		setupDirectories();
		setupTypeMappings();
		setupOtherParameters();
	}

	private void setupDirectories() {
		templatesDirFile = new File(ClassLoader.getSystemResource(
				config.getProperty("templatesDir")).getPath());
		mappingDirFile = new File(config.getProperty("outputDir"));
	}

	private void setupOtherParameters() {
		root.put("resolve", new MappingRefResolver());
	}

	private void setupTypeMappings() {
		Map<String, Type> types = reader.getTypeMappingAsMap();
		root.put("types", types);
	}

	public void processMappingFiles() {
		Collection<File> files = FileUtils.listFiles(mappingDirFile,
				new String[] { "xml" }, true);
		for (File file : files) {
			processMappingFile(file);
		}
	}

	public void processMappingFile(File mappingFile) {
		File file = null;
		try {
			String baseName = FilenameUtils.getBaseName(mappingFile.getName());
			setupTemplate(mappingFile, baseName);
			String template = determineTemplate(mappingFile);
			logProcessingSetup(mappingFile, template);
			Configuration cfg = configureTemplate();
			Template temp = cfg.getTemplate(template);
			file = createOutputFile(mappingFile, baseName);
			FileWriter fileWriter = new FileWriter(file);
			temp.process(root, fileWriter);
			fileWriter.flush();
			LOG.info("File: {} has been written.", file.getAbsolutePath());
		} catch (SAXException e) {
			LOG.error("SAXParser threw exception for file: {}",
					mappingFile.getAbsolutePath(), e);
		} catch (IOException e) {
			LOG.error("IO threw exception for file: {} ",
					mappingFile.getAbsolutePath(), e);
		} catch (ParserConfigurationException e) {
			LOG.error("Parser Configurer threw exception for file: {}",
					mappingFile.getAbsolutePath(), e);
		} catch (StopException e) {
			LOG.info("Processing stopped for file, no instance ref mapped: {}",
					mappingFile.getAbsolutePath());
			file.deleteOnExit();
		} catch (TemplateException e) {
			LOG.error("Template Engine threw exception for file: {}",
					mappingFile.getAbsolutePath(), e);
		}
	}

	private File createOutputFile(File mappingFile, String baseName) {
		String parent = StringUtils.substringAfterLast(mappingFile.getParent(),
				File.separator);
		String fileDirPath = FilenameUtils.concat(config
				.getProperty("javaSourceDir"), StringUtils.replace(
				config.getProperty("basePackage"), ".", File.separator));
		fileDirPath = FilenameUtils.concat(fileDirPath, parent);
		String fileName = baseName + "Mapper.java";
		File fileDirFile = new File(fileDirPath);
		fileDirFile.mkdirs();
		File file = new File(FilenameUtils.concat(fileDirPath, fileName));
		return file;
	}

	private Configuration configureTemplate() throws IOException {
		Configuration cfg = new Configuration();
		cfg.setDirectoryForTemplateLoading(templatesDirFile);
		cfg.setObjectWrapper(ObjectWrapper.DEFAULT_WRAPPER);
		cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
		return cfg;
	}

	private void setupTemplate(File mappingFile, String baseName)
			throws FileNotFoundException, SAXException, IOException,
			ParserConfigurationException {
		FileInputStream inputStream = new FileInputStream(mappingFile);
		InputSource source = new InputSource(inputStream);
		root.put("doc", freemarker.ext.dom.NodeModel.parse(source));
		root.put("objectType", baseName);
	}

	private void logProcessingSetup(File mappingFile, String template) {
		LOG.info("##########################################################");
		LOG.info("Processing mappingFile: {}", mappingFile.getAbsolutePath());
		LOG.info("Using template file: {}", template);
		LOG.debug("Root consists of: {}", root);
	}

	private String determineTemplate(File mapping) {
		if ("Message.xml".equalsIgnoreCase(mapping.getName()))
			return "MessageMapper.ftl";
		else
			return "GenericMapper.ftl";
	}
}
