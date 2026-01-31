plugins {
    java
    id("com.github.johnrengelman.shadow") version "8.1.1"
    `maven-publish`
}

group = "id.naturalsmp"
version = "2.12.10"

repositories {
    mavenCentral()
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
    maven { url = uri("https://gitlab.com/api/v4/projects/19978391/packages/maven") }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.0.1")
    compileOnly(files("libs/InfiniteJump.jar"))

    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("org.slf4j:slf4j-simple:1.6.4")
    implementation("us.ajg0702:ajUtils:1.0.0")
}

tasks.withType<ProcessResources> {
    filesMatching("plugin.yml") {
        expand("VERSION" to project.version)
    }
}

tasks.shadowJar {
    relocate("us.ajg0702.utils", "us.ajg0702.parkour.utils")
    relocate("com.zaxxer.hikari", "us.ajg0702.parkour.hikari")

    archiveClassifier.set("")
    archiveFileName.set("NaturalParkour.jar")
}

tasks.build {
    dependsOn(tasks.shadowJar)
}

java {
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact(tasks["jar"])
        }
    }

    repositories {
        val mavenUrl = "https://repo.ajg0702.us/releases"
        if (!System.getenv("REPO_TOKEN").isNullOrEmpty()) {
            maven {
                url = uri(mavenUrl)
                name = "ajRepo"
                credentials {
                    username = "plugins"
                    password = System.getenv("REPO_TOKEN")
                }
            }
        }
    }
}

