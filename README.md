[![Build Status](https://travis-ci.org/nhl/bootique-bom.svg)](https://travis-ci.org/nhl/bootique-bom)
[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.nhl.bootique.bom/bootique-bom/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.nhl.bootique.bom/bootique-bom/)

A "Bill of Materials" (BOM) project, that helps user projects to maintain consistent versions of various [Bootique](https://github.com/nhl/bootique)-related dependencies. It declares a certain version of Bootique plus compatible versions of all standard Bootique extension Modules. Intended to be imported in an application ```pom.xml``` as shown below:
 
```xml
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>com.nhl.bootique.bom</groupId>
			<artifactId>bootique-bom</artifactId>
			
			<!-- 
			     Major and minor version of the bom usually matches that of the main Bootique module.
			     Revision number (third version component) is increased with upgrades of other
			     module dependencies.
			-->
			<version>0.12.2</version>
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
		<groupId>com.nhl.bootique.logback</groupId>
		<artifactId>bootique-logback</artifactId>
	</dependency>
</dependencies>
```

## Upgrading

See the "maven-central" badge above for the current production version of bootique-bom. When upgrading, don't forget to check [upgrade notes](https://github.com/nhl/bootique-bom/blob/master/UPGRADE.md) specific to your version.