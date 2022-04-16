plugins {
    id("java")
    id("com.github.johnrengelman.shadow") version "6.1.0"
}

group = "de.drolpi"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://repo.dmulloy2.net/repository/public/")
    maven("https://papermc.io/repo/repository/maven-public/")
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.18.2-R0.1-SNAPSHOT")
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
