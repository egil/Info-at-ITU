
@echo off
java -classpath jcaf.v15.jar;dk.itu.info.jar;org.restlet.jar;org.json.jar;org.restlet.ext.json.jar;jettison-1.0.1.jar;xstream-1.3.1.jar;jackson-core-lgpl-1.6.2.jar;jackson-mapper-lgpl-1.6.2.jar;gson-1.5.jar;jackson-mrbean-1.6.2.jar;org.restlet.ext.xml.jar -Djava.security.policy=java.policy dk.pervasive.jcaf.impl.ContextServiceImpl %1 %2
	    