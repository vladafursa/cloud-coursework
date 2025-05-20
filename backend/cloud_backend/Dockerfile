
# Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
# Click nbfs://nbhost/SystemFileSystem/Templates/Other/Dockerfile to edit this template

# official Tomcat image
FROM tomcat:9.0

# Copy the .war file into the Tomcat webapps directory
COPY dist/cloud_backend.war /usr/local/tomcat/webapps/ROOT.war

# Expose port 8080 for the Tomcat server
EXPOSE 8080

# Start Tomcat server
CMD ["catalina.sh", "run"]
