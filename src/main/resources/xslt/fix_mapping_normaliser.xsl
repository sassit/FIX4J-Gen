<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:fix="http://www.algotrader.org/fix">
	<xsl:output name="xml" method="xml" encoding="UTF-8" indent="yes" />
	<xsl:param name="outputDir" required="yes" />
	<xsl:param name="namespace" required="yes" />

	<xsl:template match="/fix">
		<xsl:variable name="uri" select="concat($outputDir,'Message.xml')" />
		<xsl:result-document format="xml" href="{$uri}">
			<xsl:element name="fix:message" namespace="{$namespace}">
				<xsl:attribute name="version"><xsl:value-of select="/fix/@major" />.<xsl:value-of
					select="/fix/@minor" /></xsl:attribute>
				<xsl:element name="scope">
					<xsl:attribute name="ref">[DEFINE_ME]</xsl:attribute>
					<xsl:attribute name="instanceof">[DEFINE_ME]</xsl:attribute>
				</xsl:element>
				<xsl:apply-templates select="header" />
				<xsl:apply-templates select="trailer" />
			</xsl:element>
		</xsl:result-document>
		<xsl:apply-templates select="messages" />
	</xsl:template>

	<xsl:template match="messages">
		<xsl:apply-templates select="message" />
	</xsl:template>

	<xsl:template match="header">
		<xsl:variable name="name" select="local-name()" />
		<xsl:element name="fix:{$name}">
			<xsl:for-each select="field">
				<xsl:call-template name="field" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template match="trailer">
		<xsl:variable name="name" select="local-name()" />
		<xsl:element name="fix:{$name}">
			<xsl:for-each select="field">
				<xsl:call-template name="field" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template match="message">
		<xsl:variable name="name" select="current()/@name" />
		<xsl:variable name="uri" select="concat($outputDir,$name,'.xml')" />
		<xsl:result-document format="xml" href="{$uri}">
			<xsl:element name="fix:message" namespace="{$namespace}">
				<xsl:attribute name="version"><xsl:value-of select="/fix/@major" />.<xsl:value-of
					select="/fix/@minor" /></xsl:attribute>
				<xsl:element name="scope">
					<xsl:attribute name="ref">[DEFINE_ME]</xsl:attribute>
					<xsl:attribute name="instanceof">[DEFINE_ME]</xsl:attribute>
				</xsl:element>
				<xsl:element name="header" />
				<xsl:element name="fix:{$name}">
					<xsl:copy-of select="@*[name(.)!='name']" />
					<xsl:for-each select="field">
						<xsl:call-template name="field" />
					</xsl:for-each>
					<xsl:for-each select="group">
						<xsl:call-template name="group" />
					</xsl:for-each>
					<xsl:for-each select="component">
						<xsl:call-template name="component" />
					</xsl:for-each>
				</xsl:element>
				<xsl:element name="trailer" />
			</xsl:element>
		</xsl:result-document>
	</xsl:template>

	<xsl:template name="field">
		<xsl:variable name="name" select="current()/@name" />
		<xsl:element name="fix:{$name}">
			<xsl:copy-of select="@*[name(.)!='name']" />
			<xsl:copy-of select="/fix/fields/field[@name=$name]/@*[name(.)!='name']" />
			<xsl:element name="mapping">
				<xsl:attribute name="ref">[DEFINE_ME]</xsl:attribute>
			</xsl:element>
		</xsl:element>
	</xsl:template>

	<xsl:template name="component">
		<xsl:variable name="name" select="current()/@name" />
		<xsl:element name="fix:{$name}">
			<xsl:attribute name="isComponent">true</xsl:attribute>
			<xsl:for-each select="/fix/components/component[@name=$name]/*">
				<xsl:call-template name="field" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>

	<xsl:template name="group">
		<xsl:variable name="name" select="current()/@name" />
		<xsl:element name="fix:{$name}">
			<xsl:attribute name="isGroup">true</xsl:attribute>
			<xsl:copy-of select="@*[name(.)!='name']" />
			<xsl:copy-of select="/fix/fields/field[@name=$name]/@*" />
			<xsl:element name="mapping">
				<xsl:attribute name="ref">[DEFINE_ME]</xsl:attribute>
			</xsl:element>
			<xsl:for-each select="field">
				<xsl:call-template name="field" />
			</xsl:for-each>
		</xsl:element>
	</xsl:template>
</xsl:stylesheet>