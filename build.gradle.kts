plugins {
    java
    id("org.springframework.boot") version "3.3.3" // Adjust Spring Boot version if needed
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_22

repositories {
    mavenCentral()
}

dependencies {
    // Spring Boot Starter Dependencies
    implementation("org.springframework.boot:spring-boot-starter")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")

    implementation("org.springframework.boot:spring-boot-starter-actuator")
    implementation("org.springframework.boot:spring-boot-starter-cache")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-reactor-netty")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("com.fasterxml.jackson.core:jackson-core:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-annotations:2.14.2")
    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jdk8:2.14.2")
    implementation("com.fasterxml.jackson.datatype:jackson-datatype-jsr310:2.14.2")
    implementation("com.fasterxml.jackson.module:jackson-module-parameter-names:2.14.2")
    // implementation("io.projectreactor.tools:blockhound")
    implementation("org.postgresql:postgresql")
    implementation("org.postgresql:r2dbc-postgresql")
    implementation("io.r2dbc:r2dbc-h2")
    implementation("org.springframework.boot:spring-boot-devtools")
    implementation("org.springframework.security:spring-security-oauth2-authorization-server")
    runtimeOnly("com.h2database:h2")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("org.springframework.security:spring-security-test")
    // Database Driver (choose one based on your database)
    // runtimeOnly("com.h2database:h2") // For in-memory H2 database
    // runtimeOnly("org.postgresql:postgresql") // Uncomment if using PostgreSQL
    // runtimeOnly("mysql:mysql-connector-java") // Uncomment if using MySQL

    // Testing Dependencies
    // testImplementation("org.springframework.boot:spring-boot-starter-test")

    // Optional: Lombok for reducing boilerplate code
    // compileOnly("org.projectlombok:lombok")
    // annotationProcessor("org.projectlombok:lombok")
    // testCompileOnly("org.projectlombok:lombok")
    // testAnnotationProcessor("org.projectlombok:lombok")
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

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(22)) // Set Java 22 toolchain
    }
}

