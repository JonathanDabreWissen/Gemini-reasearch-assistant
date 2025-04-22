# Use a base JDK image
FROM openjdk:17-jdk-slim

# Set working directory
WORKDIR /app

# Copy the jar file (adjust the .jar name if needed)
COPY target/*.jar app.jar

# Expose port (Render will override this with $PORT)
EXPOSE 8080

# Run the jar file
CMD ["java", "-jar", "app.jar"]
