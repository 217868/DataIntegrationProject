<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output method="xml"/>

	<xsl:template match = "/"> 
	<xsl:variable name="temp_root" select="film_base"/>
	<actors>
	 <xsl:for-each select="distinct-values(film_base/film/cast/actor)">
	 <xsl:sort select="."/>
	 <xsl:variable name="actor_variable" select="."/>
		<actor>
			<xsl:attribute name="actor_name">
				<xsl:value-of select="$actor_variable"/>
			</xsl:attribute>
			<xsl:element name="number_of_films">
				<xsl:value-of select="count($temp_root/film[cast/actor = $actor_variable])"/>
			</xsl:element>
		</actor>
	 </xsl:for-each>
	 </actors>
	</xsl:template>
</xsl:stylesheet>