## 도커 허브에서 이미지를 가져온다.
#FROM openjdk:17
#
## 컨테이너 내부에 /app 디렉토리를 Working Direcotry로 설정하는데,
## 이후의 명령들이 실행될 위치를 설정한다고 생각하면 된다.
#WORKDIR /app
#
## COPY {복사할 파일} {저장 디렉토리} => 이미지 파일을 빌드하면 현재 프로젝트 디렉토리를 기준으로 jar파일을 찾아서
## 컨테이너 내부의 /app/app.jar에 복사할 수 있도록 한다.
#COPY /build/libs/artx.jar /app/artx.jar
#
#
## 설정 파일을 복사
#COPY src/main/resources/application-prod.yml application-prod.yml
#
## 컨테이너가 시작되는 시점에 실행되는 기본 명령어이다.
## WORKDIR랑은 별개이며, 독립적인 명령어라고 생각하면 된다.
#CMD ["java", "-jar", "/app/artx.jar"]# 도커 허브에서 이미지를 가져온다.

# gradle:7.3.1-jdk17 이미지를 기반으로 함

FROM openjdk:17-slim

# 작업 디렉토리 설정
WORKDIR /app

# Spring 소스 코드를 이미지에 복사
COPY . .

RUN mkdir -p /root/.gradle

RUN echo "systemProp.http.proxyHost=krmp-proxy.9rum.cc\nsystemProp.http.proxyPort=3128\nsystemProp.https.proxyHost=krmp-proxy.9rum.cc\nsystemProp.https.proxyPort=3128" > /root/.gradle/gradle.properties

# gradlew 실행 권한 부여
RUN chmod +x gradlew

# gradlew를 이용한 프로젝트 빌드
RUN ./gradlew clean build

# gradle 빌드 시 proxy 설정을 gradle.properties에 추가
RUN echo "distributionBase=GRADLE_USER_HOME\distributionPath=wrapper/dists\distributionUrl=https\://services.gradle.org/distributions/gradle-7.5.1-bin.zip\zipStoreBase=GRADLE_USER_HOME\zipStorePath=wrapper/dists" > /root/.gradle/gradle.properties

ENV DATABASE_URL=jdbc:mariadb://mariadb/krampoline

# 빌드 결과 jar 파일을 실행
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app/build/libs/artx.jar"]