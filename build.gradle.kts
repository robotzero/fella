plugins {
    java
    id("org.springframework.boot") version "3.4.0-RC1" // Adjust Spring Boot version if needed
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.queen"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_23

repositories {
    mavenCentral()
    maven {
        url = uri("https://repo.spring.io/milestone")
    }
    maven {
        url = uri("https://repo.spring.io/snapshot")
    }
}

configurations {
    implementation {
//        exclude(module = "spring-boot-starter-tomcat")
        //exclude(group = "org.springframework", module = "spring-webmvc")
        exclude(module = "spring-boot-starter-websocket")
//        exclude(module = "spring-web")
    }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
//    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-client")
    implementation("org.springframework.boot:spring-boot-starter-actuator")
//    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")
    implementation("com.fasterxml.jackson.core:jackson-core")
    implementation("com.fasterxml.jackson.core:jackson-annotations")
    implementation("com.fasterxml.jackson.core:jackson-databind")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names")
//     implementation("io.projectreactor.tools:blockhound")
    implementation("org.postgresql:postgresql")
    implementation("org.postgresql:r2dbc-postgresql")
//    implementation("io.r2dbc:r2dbc-h2")
    implementation("org.springframework.boot:spring-boot-devtools")
//    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
}

tasks.test {
    useJUnitPlatform()
}

tasks.withType<JavaCompile> {
    options.compilerArgs.add("--enable-preview")
}

tasks.withType<Test> {
    jvmArgs("--enable-preview")
}

// tasks.named<JavaExec>("run") {
//     jvmArgs("--enable-preview")
// }

//java {
//    toolchain {
//        languageVersion.set(JavaLanguageVersion.of(23)) // Set Java 22 toolchain
//    }
//}

