node("Jenkins-Slave-Centos7-VM") {
  def centosImage
  def Registry="https://registry.hub.docker.com"
  def Imagename="stenyjames/centos7-springboot"
  def JobDetails
  
stage("Git checkout from git repository") {
        checkout([$class: 'GitSCM',
        branches: [[name: "origin/master"]],
		gitTool: 'git-slave', 
		userRemoteConfigs: [[
		url: 'https://github.com/stenyjames/openshift-springboot.git' ]]
		])
		
}

stage("Build Docker Image") {
   #   def rootDir = pwd()
    #def example = load "${rootDir}@script/Example.Groovy "
    #example.exampleMethod()
	
    echo "Building docker image ${Imagename}:${env.BUILD_NUMBER}"
    centosImage = docker.build("${Imagename}:${env.BUILD_NUMBER}")
 
}

stage("Test Docker Image") {
  echo "Testing the deloyed application "
  centosImage.inside {
         sh "curl http://localhost:8080"
      }
  
}


stage("Push the image to Dockerhub repository") {
 echo "Pushing newly created docker image"
	retry(3) {
		docker.withRegistry("${Registry}","docker-hub-credentials") {
		centosImage.push()
		JobDetails="Job ${env.JOB_NAME} created image ${env.BUILD_NUMBER} successfully"
		
	}
      }

}

stage("Cleanup docker images from workspace") {
      echo "Removing the newly created docker image"
      sh "docker rmi ${Imagename}:${env.BUILD_NUMBER}"
}
