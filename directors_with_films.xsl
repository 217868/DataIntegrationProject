<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

	<xsl:template match = "/"> 
	<xsl:variable name="temp_root" select="film_base"/>
	<directors>
	 <xsl:for-each select="distinct-values(film_base/film/directors/director)">
	 <xsl:variable name="director_variable" select="."/>
		<director>
			<name>
				<xsl:value-of select="$director_variable"/>
			</name>
			<films>
					<xsl:for-each select="$temp_root/film[directors/director = $director_variable]">
					<film>
						<xsl:value-of select="title"/>
					</film>
					</xsl:for-each>
			</films>
		</director>
	 </xsl:for-each>
	 </directors>
	</xsl:template>
</xsl:stylesheet>