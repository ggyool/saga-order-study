plugins {
    kotlin("jvm") version "1.9.25"
}

group = "com.ggyool"
version = "0.0.1-SNAPSHOT"
val springCloudStreamBinderKafkaVersion: String by project

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    compileOnly("org.springframework.cloud:spring-cloud-stream-binder-kafka-reactive:$springCloudStreamBinderKafkaVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict")
    }
}

