FROM quay.io/wildfly/wildfly
ADD target/unicesarapp.war /opt/jboss/wildfly/standalone/deployments/
EXPOSE 8180 8080/tcp
EXPOSE 10090 9990/tcp
CMD ["/opt/jboss/wildfly/bin/standalone.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0"]
