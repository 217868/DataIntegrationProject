<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

	<xsl:template match = "/"> 
	<xsl:variable name="temp_root" select="film_base"/>
	 <xsl:for-each select="distinct-values(film_base/film/countries/country)">
	 <xsl:variable name="country_variable" select="."/>
		<xsl:text>Country: </xsl:text> <xsl:value-of select="$country_variable"/>
		<xsl:text>&#xa;</xsl:text>
		<xsl:text>Films: </xsl:text>
		<xsl:text>&#xa;</xsl:text>
			<xsl:for-each select="$temp_root/film/countries[country = $country_variable]">
				<xsl:text>&#x9;</xsl:text>
				<xsl:value-of select="../title"/>
				<xsl:text>&#xa;</xsl:text>
			</xsl:for-each>
			<xsl:text>&#xa;</xsl:text>
	 </xsl:for-each>
	</xsl:template>
</xsl:stylesheet>