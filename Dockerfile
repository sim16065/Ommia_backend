# 1단계: 빌드 스테이지 (소스 코드 컴파일)
FROM eclipse-temurin:21-jdk-jammy AS build
WORKDIR /app
# 의존성 캐싱을 위한 파일 복사
COPY gradlew settings.gradle build.gradle ./
COPY gradle ./gradle

# 의존성 다운로드
RUN chmod +x gradlew && ./gradlew dependencies --no-daemon

# 소스 코드 복사
COPY src ./src

# 테스트 파일 없이 빌드
RUN ./gradlew clean build -x test --no-daemon

# 2단계: 실행 스테이지
FROM eclipse-temurin:21-jre-jammy AS runtime

RUN groupadd -r ommia-back && useradd -r -g ommia-back backuser
WORKDIR /app

# 유저 권한으로 jar 파일 복사
COPY --chown=backuser:ommia-back --from=build /app/build/libs/*.jar app.jar

# 유저 변경
USER backuser
ENV TZ=Asia/Seoul
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]