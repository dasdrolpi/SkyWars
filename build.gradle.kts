plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "de.drolpi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.thesimplecloud.eu/artifactory/list/gradle-release-local/")
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://repo.velocitypowered.com/snapshots/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("org.github.paperspigot:paperspigot-api:1.8.8-R0.1-SNAPSHOT")
    compileOnly("org.projectlombok:lombok:1.18.16")
    annotationProcessor("org.projectlombok:lombok:1.18.16")
    implementation("org.jetbrains:annotations:20.1.0")
}

tasks {
    compileJava {
        options.encoding = "UTF-8"
    }

    shadowJar {
        //Set the Name of the Output File
        archiveFileName.set("${project.name}.jar")
    }
}