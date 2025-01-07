FROM bellsoft/liberica-openjre-alpine:21.0.5
RUN apk --no-cache add curl
COPY /build/install/awesome /app/
ENTRYPOINT /app/bin/awesome
