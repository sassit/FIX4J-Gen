<#ftl ns_prefixes={"fix":"http://www.algotrader.org/fix"}>

<#assign message = doc["fix:message"]>
<#assign scopeRef = message.scope.@ref>
<#assign instanceof = message.scope.@instanceof>
<#assign section = "fix:${objectType}">

<#include "MapperMacros.ftl">
<#if scopeRef == "[DEFINE_ME]" || instanceof == "[DEFINE_ME]">
	<#stop "No mapping reference for mapper defined, assuming not used.">
</#if>
package com.algoTrader.mapping.fix${message.@version?replace(".","")};

import quickfix.${message.@version?replace(".","")}.*;

class ${objectType}Mapper extends MessageMapper {

	private ${message.scope.@instanceof} ${message.scope.@ref};
	private ${objectType} fixMessageObject;
	<@fields section />

	public ${objectType}Mapper(${objectType} fixObject, ${message.scope.@instanceof} ${scopeRef}){
		super(fixObject, ${scopeRef});	
	}
	
	protected void doMarshall(){
	<@marshal section />
	}
	
	protected void doUnMarshall(){
	<@unMarshal section />
	}
	
	<@getSetters section/>
}