# Centos based container with Java and Tomcat
FROM centos:centos7
MAINTAINER steny.james

# Install prepare infrastructure
#RUN yum -y update && \
RUN yum -y install wget && \
 yum -y install tar

# Prepare environment 
#ENV JAVA_HOME /opt/java
ENV JAVA_HOME /usr/lib/jvm/java-1.8.0-openjdk/
ENV CATALINA_HOME /opt/tomcat 
ENV PATH $PATH:$JAVA_HOME/bin:$CATALINA_HOME/bin:$CATALINA_HOME/scripts

# Install Oracle Java8
ENV JAVA_VERSION 8u191
ENV JAVA_BUILD 8u191-b12
ENV JAVA_DL_HASH 2787e4a523244c269598db4e85c51e0c

#RUN wget --no-check-certificate --no-cookies --header "Cookie: oraclelicense=accept-securebackup-cookie" \
# http://download.oracle.com/otn-pub/java/jdk/${JAVA_BUILD}/${JAVA_DL_HASH}/jdk-${JAVA_VERSION}-linux-x64.tar.gz && \
# tar -xvf jdk-${JAVA_VERSION}-linux-x64.tar.gz && \
# rm jdk*.tar.gz && \
# mv jdk* ${JAVA_HOME}

RUN yum install -y \
   java-1.8.0-openjdk \
   java-1.8.0-openjdk-devel

# Install Tomcat
ENV TOMCAT_MAJOR 8
ENV TOMCAT_VERSION 8.5.35

RUN wget https://archive.apache.org/dist/tomcat/tomcat-${TOMCAT_MAJOR}/v${TOMCAT_VERSION}/bin/apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
 tar -xvf apache-tomcat-${TOMCAT_VERSION}.tar.gz && \
 rm apache-tomcat*.tar.gz && \
 mv apache-tomcat* ${CATALINA_HOME}

RUN chmod +x ${CATALINA_HOME}/bin/*sh

# Create Tomcat admin user
ADD create_admin_user.sh $CATALINA_HOME/scripts/create_admin_user.sh
ADD tomcat.sh $CATALINA_HOME/scripts/tomcat.sh
RUN chmod +x $CATALINA_HOME/scripts/*.sh
RUN chmod +x $CATALINA_HOME/bin/catalina.sh

# Create tomcat user
RUN groupadd -r tomcat && \
 useradd -g tomcat -d ${CATALINA_HOME} -s /sbin/nologin  -c "Tomcat user" tomcat && \
 chown -R tomcat:tomcat ${CATALINA_HOME}

WORKDIR /opt/tomcat/webapps
RUN pwd 
RUN rm -rf /opt/tomcat/webapps/*
#Deploy the spring boot application
#COPY spring-boot-web-jsp-1.0.war . 
COPY spring-boot-web-jsp-1.0.war /opt/tomcat/webapps/ROOT.war
RUN ls

#docker image build -t your_name/some-app-image ./


# This default user is created in the openshift/base-centos7 image
USER tomcat

EXPOSE 8080
EXPOSE 8009

CMD ["tomcat.sh"]
