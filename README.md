[![Build Status](https://travis-ci.org/nhl/bootique-bom.svg)](https://travis-ci.org/nhl/bootique-bom)

A "Bill of Materials" (BOM) project, that helps user projects to maintain consistent versions of various [Bootique](https://github.com/nhl/bootique)-related dependencies. It declares a certain version of Bootique plus compatible versions of all standard Bootique extension Modules. Intended to be imported in an aplication ```pom.xml``` as shown below:

```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>com.nhl.bootique.bom</groupId>
			<artifactId>bootique-bom</artifactId>
			
			<!-- This version usually matches the version of the main Bootioque Module. -->
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
	<!-- Here declare only the  Modules you app requires; omit their versions 
	     (which will be coming from the import above 
	-->
	<dependency>
		<groupId>com.nhl.bootique</groupId>
		<artifactId>bootique</artifactId>
	</dependency>
	<dependency>
		<groupId>com.nhl.bootique.cayenne</groupId>
		<artifactId>bootique-cayenne</artifactId>
	</dependency>
	<dependency>
		<groupId>com.nhl.bootique.jersey</groupId>
		<artifactId>bootique-jersey</artifactId>
	</dependency>
	<dependency>
		<groupId>com.nhl.bootique.logback</groupId>
		<artifactId>bootique-logback</artifactId>
	</dependency>
</dependencies>
```