plugins {
    java
    id("com.gradleup.shadow") version "8.3.5"
    `maven-publish`
}

group = "id.naturalsmp"
version = "2.12.11"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.papermc.io/repository/maven-public/") }
    maven { url = uri("https://oss.sonatype.org/content/repositories/snapshots") }
    maven { url = uri("https://hub.spigotmc.org/nexus/content/repositories/snapshots/") }
    maven { url = uri("https://repo.extendedclip.com/content/repositories/placeholderapi/") }
    maven { url = uri("https://maven.enginehub.org/repo/") }
    maven { url = uri("https://jitpack.io") }
}

dependencies {
    compileOnly("io.papermc.paper:paper-api:1.21.3-R0.1-SNAPSHOT")
    compileOnly("me.clip:placeholderapi:2.11.6")
    compileOnly("com.sk89q.worldedit:worldedit-bukkit:7.0.1")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly("com.github.MilkBowl:VaultAPI:1.7")
    compileOnly(files("libs/InfiniteJump.jar"))

    implementation("com.zaxxer:HikariCP:3.4.5")
    implementation("org.slf4j:slf4j-simple:1.6.4")
    implementation("net.kyori:adventure-platform-bukkit:4.3.4")
    implementation("net.kyori:adventure-text-minimessage:4.17.0")
}

tasks.withType<ProcessResources> {
    filesMatching("plugin.yml") {
        expand("VERSION" to project.version)
    }
}

tasks.jar {
    enabled = false
}

tasks.shadowJar {
    archiveClassifier.set("")
    archiveFileName.set("NaturalParkour.jar")
    
    
    relocate("com.zaxxer.hikari", "id.naturalsmp.naturalparkour.hikari")

    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
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
            artifact(tasks.shadowJar)
        }
    }

    repositories {
        val mavenUrl = "https://repo.naturalsmp.my.id/releases"
        if (!System.getenv("REPO_TOKEN").isNullOrEmpty()) {
            maven {
                url = uri(mavenUrl)
                name = "naturalRepo"
                credentials {
                    username = "plugins"
                    password = System.getenv("REPO_TOKEN")
                }
            }
        }
    }
}

