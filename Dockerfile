FROM bellsoft/liberica-openjre-alpine:21.0.5
RUN apk --no-cache add curl
COPY /app-backend/build/install/awesome /app/backend/
COPY /app-frontend/dist /app/frontend/
RUN chmod u+x /app/backend/bin/awesome
ENTRYPOINT /app/backend/bin/awesome
