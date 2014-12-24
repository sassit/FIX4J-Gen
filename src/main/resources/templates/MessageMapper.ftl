<#ftl ns_prefixes={"fix":"http://www.algotrader.org/fix"}>

<#assign message = doc["fix:message"]>
<#assign scopeRef = "fixMessage">
<#assign instanceof = message.scope.@instanceof>

<#include "MapperMacros.ftl" >

package com.algoTrader.mapping;

import quickfix.${message.@version?replace(".","")}.*;

abstract class MessageMapper {
	
	protected Message fixMessage;
	protected AlgoTrader baseObject;
	<@fields "fix:header" />
	<@fields "fix:trailer" />
	
	public MessageMapper(Message fixMessage, AlgoTrader baseObject){
		this.fixMessage = fixMessage;
		this.baseObject = baseObject;
	} 
	
	public void marshall(){
	<@marshal "fix:header" />
	<@marshal "fix:trailer" />
		doMarshal();
	}
	
	public void unMarshall(){
	<@unMarshal "fix:header" />
	<@unMarshal "fix:trailer" />
		doUnMarshal();
	}
	
	<@getSetters "fix:header" />
	<@getSetters "fix:trailer" />
}