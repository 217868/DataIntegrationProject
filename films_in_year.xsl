<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version = "1.0" 
xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">  
	<xsl:template match = "/"> 
	 <html>
		<body>
			<xsl:variable name="temp_root" select="film_base"/>
			<xsl:for-each select="film_base/film/@year">
			<xsl:sort select="."/>
				<xsl:variable name="year_variable" select="."/>
				<xsl:value-of select="$year_variable"/>
				<ul>
				<xsl:for-each select="$temp_root/film[@year=$year_variable]">
					<li>
					<xsl:value-of select="title"/>
					</li>
				</xsl:for-each>
				</ul>
			</xsl:for-each>
		</body>
	 </html>
	</xsl:template>
</xsl:stylesheet>