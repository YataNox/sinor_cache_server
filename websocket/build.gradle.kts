// https://start.spring.io/#!type=gradle-project-kotlin&language=java&platformVersion=3.2.1&packaging=jar&jvmVersion=21&groupId=com.sinor&artifactId=vote&name=vote&description=&packageName=com.sinor.vote&dependencies=devtools,lombok,configuration-processor,docker-compose,webflux,amqp,actuator,websocket,mysql,data-jpa

plugins {
    java
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.4"
}

group = "com.sinor"
version = "0.0.1-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-amqp")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-websocket")

    // https://springdoc.org/
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    // QueryDSL: https://velog.io/@juhyeon1114/Spring-QueryDsl-gradle-%EC%84%A4%EC%A0%95-Spring-boot-3.0-%EC%9D%B4%EC%83%81
    //    implementation("com.querydsl:querydsl-jpa:5.0.0:jakarta")
    //    annotationProcessor("com.querydsl:querydsl-apt:5.0.0:jakarta")
    //    annotationProcessor("jakarta.annotation:jakarta.annotation-api")
    //    annotationProcessor("jakarta.persistence:jakarta.persistence-api")

    compileOnly("org.projectlombok:lombok")
    developmentOnly("org.springframework.boot:spring-boot-devtools")
    developmentOnly("org.springframework.boot:spring-boot-docker-compose")
    runtimeOnly("com.mysql:mysql-connector-j")
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")
    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.amqp:spring-rabbit-test")
}

tasks.withType<Test> {
    useJUnitPlatform()
}
