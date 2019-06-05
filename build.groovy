def buildApplication() {
	
	stage("Building the jar") {
	
	sh "mvn -s ./configuration/settings.xml -f pom.xml clean package"
	}
	

}

return this