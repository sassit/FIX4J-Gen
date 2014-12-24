package com.algoTrader.mapping.type;

import java.util.List;
import java.util.Map;

public interface TypeReader {

	public abstract void init();

	public abstract List<Type> getTypeMappingAsList();

	public abstract Map<String, Type> getTypeMappingAsMap();

}