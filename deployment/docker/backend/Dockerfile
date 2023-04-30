FROM eclipse-temurin:17

EXPOSE 8080

ARG VERSION

RUN mkdir /opt/app

COPY "./recipe-$VERSION.jar" "/opt/app/recipe.jar"

CMD ["java", "-jar", "/opt/app/recipe.jar"]