<#macro basename fullName>
${fullName?substring(fullName?last_index_of(".") + 1)}
</#macro>

<#macro packagename fullName>
${fullName?substring(0, fullName?last_index_of("."))}
</#macro>

<#macro imports >
	<#list message[section].* as attribute>	
import ${types[attribute.@type].java};
  	</#list>
import <@packagename instanceof/>;
</#macro>

<#macro fields section>
	<#list message[section].* as attribute>	
		<#if attribute.mapping.@ref != "[DEFINE_ME]">
		<#local type = types[attribute.@type].java>
	private ${type} ${attribute?node_name?uncap_first};
		</#if>
  	</#list>
</#macro>

<#macro marshal section>
	<#list message[section].* as attribute>
	<#local map = attribute.mapping>
	<#local nodeName = attribute?node_name>
		<#if attribute.mapping.@ref != "[DEFINE_ME]">
			<#if map.@value?size == 1>
		${scopeRef}.set${nodeName}(${map.@value});
			<#elseif map.@converter?size == 1>
		${scopeRef}${resolve(instanceof, map.@ref, scopeRef, "false")}(${converters[map.@converter]}(fixObject.get${nodeName}()));
			<#elseif map.@ref?size == 1>
		${scopeRef}${resolve(instanceof, map.@ref, scopeRef, "false")}(fixObject.get${nodeName}());
  			</#if>
  		</#if>  	
	</#list> 
</#macro>

<#macro unMarshal section>
	<#list message[section].* as attribute>
		<#local map = attribute.mapping>
		<#local nodeName = attribute?node_name>
		<#if attribute.mapping.@ref != "[DEFINE_ME]">
			<#if map.@value?size == 1>
  		fixObject.set${nodeName}(${map.@value});
			<#elseif map.@converter?size == 1>
		fixObject.set${nodeName}(${scopeRef}${resolve(instanceof, map.@ref, scopeRef, "false")});
  			<#elseif attribute.mapping.@ref?size == 1>
  		fixObject.set${nodeName}(${scopeRef}${resolve(instanceof, map.@ref, scopeRef, "false")});
  			</#if>
  		</#if>
	</#list> 
</#macro>

<#macro getSetters section>
	<#list message[section].* as attribute>		
		<#if attribute.mapping.@ref != "[DEFINE_ME]">	
  	public ${types[attribute.@type].java} get${attribute?node_name}(){
  		return this.${attribute?node_name?uncap_first};
  	}
  	
  	public set${attribute?node_name}(${types[attribute.@type].java} ${attribute?node_name?uncap_first}){
  		this.${attribute?node_name?uncap_first} = ${attribute?node_name?uncap_first};
  	} 	
  		</#if>
	</#list>  
</#macro>