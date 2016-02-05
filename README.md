[![Build Status](https://travis-ci.org/nhl/bootique-bom.svg)](https://travis-ci.org/nhl/bootique-bom)

A "Bill of Materials" (BOM) project for [Bootique](https://github.com/nhl/bootique) users. Helps projects using Bootique to maintain consistent versions of Bootique and its standard extensions. Usage - ```pom.xml```:

```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>com.nhl.bootique.bom</groupId>
			<artifactId>bootique-bom</artifactId>
			<version>0.12</version>
			<type>pom</type>

			<!-- Using special "import" scope
			     This will declare a certain version of Bootique and 
			     compatible versions of its standard extension Modules.
			-->
			<scope>import</scope>
		</dependency>
	</dependencies>
</dependencyManagement>
...
<dependencies>
	<!-- Here declare only the  Modules you app requires, and without version -->
	<dependency>
		<groupId>com.nhl.bootique</groupId>
		<artifactId>bootique</artifactId>
		<scope>compile</scope>
	</dependency>
	<dependency>
		<groupId>com.nhl.bootique.cayenne</groupId>
		<artifactId>bootique-cayenne</artifactId>
	</dependency>
	<dependency>
		<groupId>com.nhl.bootique.jersey</groupId>
		<artifactId>bootique-jersey</artifactId>
		<scope>compile</scope>
	</dependency>
	<dependency>
		<groupId>com.nhl.bootique.logback</groupId>
		<artifactId>bootique-logback</artifactId>
		<scope>compile</scope>
	</dependency>
</dependencies>
```