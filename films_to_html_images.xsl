<?xml version = "1.0" encoding = "UTF-8"?>
<xsl:stylesheet version = "1.0" 
xmlns:xsl = "http://www.w3.org/1999/XSL/Transform">  
	<xsl:template match = "/"> 
	 <html>
		<body>
			<table>
				<tr>
					<th> film name </th>
					<th> photo </th>
				</tr>
				<xsl:for-each select="film_base/film">
				<tr>
					<td><xsl:value-of select="title"/></td>
					<td> 
					<img>
						 <xsl:attribute name="src">
								<xsl:value-of select="@image_link"/>
						</xsl:attribute>
					</img>
					</td>
				
				</tr>
					
				</xsl:for-each>
			</table>
		</body>
	 </html>
	</xsl:template>
</xsl:stylesheet>