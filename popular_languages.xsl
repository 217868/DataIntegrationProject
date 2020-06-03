<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

	<xsl:template match = "/"> 
	<xsl:variable name="temp_root" select="film_base"/>
	 <xsl:for-each select="distinct-values(film_base/film/languages/language)">
	 <xsl:sort select="."/>
	 <xsl:variable name="language_variable" select="."/>
		<xsl:text>Language: </xsl:text> <xsl:value-of select="$language_variable"/> 
		<xsl:text> Number of films: </xsl:text> <xsl:value-of select="count($temp_root/film[languages/language = $language_variable])"/>
		<xsl:text>&#xa;</xsl:text>		
	 </xsl:for-each>
	</xsl:template>
</xsl:stylesheet>