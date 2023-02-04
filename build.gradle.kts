import com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar

plugins {
    `java-library`
    id("com.github.johnrengelman.shadow") version "7.0.0"
}

group = "com.vertmix"
version = "1.0-SNAPSHOT"

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

allprojects {
    apply(plugin = "java-library")
    apply(plugin = "com.github.johnrengelman.shadow")

    java {
        withSourcesJar()
        withJavadocJar()
    }

    repositories {
        mavenCentral()
    }

    tasks {
        build {
            dependsOn(shadowJar)
        }

        named<ShadowJar>("shadowJar") {
            mergeServiceFiles()

            fun reloc(vararg clazz: String) {
                clazz.forEach { relocate(it, "com.vertmix.config.libs.${it}") }
            }
        }
    }
}