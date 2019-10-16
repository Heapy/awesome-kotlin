# Container with application
FROM amazoncorretto:11.0.4
COPY /app-backend/build/install/awesome /awesome/
ENTRYPOINT /awesome/bin/awesome
