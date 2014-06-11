jsi18nresourcebundle
====================

JSP Tag Library for reading from JAVA ResourceBundle properties file and generating JS constants object

To install the MAVEN project, simply run:

1. mvn clean install

Please note the project connects to a remote repository (which configures Findbugs / Checkstyle). To remove the remote repository connection, please remove the <parent> element and <repositories> element (and everything under the two elements) in the pom.xml file.

2. the build will generate a JAR artifact, link the JAR to your JSP Web project and begin using the "generateJSConstant" Tag. You may refer to the TLD for more descriptions and the WIKI page for a simple guide: https://github.com/jclalala/jsi18nresourcebundle/wiki
