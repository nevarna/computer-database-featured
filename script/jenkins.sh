docker run -d --name jenkins -u root -v /var/run/docker.sock:/var/run/docker.sock -v $(which docker):/usr/bin/docker -v maven-jenkins:/myData -v tomcat-jenkins:/tomcat -p 8080:8080 jenkins
echo "Attente 20 secondes"
/bin/sleep 20
docker exec jenkins /bin/sh -c "cd var/jenkins_home/secrets/;cat initialAdminPassword;exit"
docker ps
