import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.bundling.BootJar

buildscript {
    repositories {
        gradlePluginPortal()
    }
    dependencies {
        classpath("gradle.plugin.com.palantir.gradle.docker:gradle-docker:0.13.0")
    }
}

plugins {
    id("org.springframework.boot") version "2.1.7.RELEASE"
    id("io.spring.dependency-management") version "1.0.7.RELEASE"
    id("com.palantir.docker") version "0.13.0" /*apply false*/
    kotlin("plugin.jpa") version "1.3.50"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

group = "com.training"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    jcenter()
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    implementation("com.graphql-java-kickstart:graphql-spring-boot-starter:5.10.0")
    implementation("com.graphql-java-kickstart:graphql-java-servlet:8.0.0")
    implementation("com.graphql-java-kickstart:graphql-java-tools:5.6.0")
    implementation("com.graphql-java:graphql-java:13.0")
    implementation("com.graphql-java:graphql-java-extended-scalars:1.0")

    runtimeOnly("com.graphql-java-kickstart:graphiql-spring-boot-starter:5.10.0") { because("Embedded Graphql IDE") }
    runtimeOnly("com.graphql-java-kickstart:voyager-spring-boot-starter:5.10.0") { because("Embedded schema navigation") }
    runtimeOnly("com.h2database:h2") { because("In memory database") }
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.1.0")
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.3.1")
    testImplementation("org.junit.jupiter:junit-jupiter-params:5.3.1")
    testImplementation("org.assertj:assertj-core:3.12.2")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.3.1")
    testImplementation("com.graphql-java-kickstart:graphql-spring-boot-starter-test:5.10.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools") { because("Fast reload and H2 console") }
}

tasks {
    withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

    create<Copy>("unpack") {
        val bootJar = getByName<BootJar>("bootJar")
        dependsOn(setOf(bootJar))
        from(zipTree(bootJar.outputs.files.singleFile))
        into("build/dependency")
    }
}

docker {
    val bootJar = tasks.getByName<BootJar>("bootJar")
    val unpack = tasks.getByName<Copy>("unpack")
    val archiveBaseName = bootJar.archiveBaseName.get()
    name = "${project.group}/$archiveBaseName"
    copySpec.from(unpack.outputs).into("dependency")
    buildArgs(mapOf("DEPENDENCY" to "dependency"))
}
