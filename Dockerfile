# 도커 허브에서 이미지를 가져온다.
FROM openjdk:17

# 컨테이너 내부에 /app 디렉토리를 Working Direcotry로 설정하는데,
# 이후의 명령들이 실행될 위치를 설정한다고 생각하면 된다.
WORKDIR /app

# COPY {복사할 파일} {저장 디렉토리} => 이미지 파일을 빌드하면 현재 프로젝트 디렉토리를 기준으로 jar파일을 찾아서
# 컨테이너 내부의 /app/app.jar에 복사할 수 있도록 한다.
COPY /build/libs/stdout.jar /app/stdout.jar

# 컨테이너가 시작되는 시점에 실행되는 기본 명령어이다.
# WORKDIR랑은 별개이며, 독립적인 명령어라고 생각하면 된다.
CMD ["java", "-jar", "/app/.jar"]