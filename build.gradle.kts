plugins {
    id("fabric-loom") version Versions.Plugins.loom
    kotlin("jvm") version Versions.kotlin
}

repositories {
}

loom {
    splitEnvironmentSourceSets()

    mods.create(Constants.Mod.id) {
        sourceSet(sourceSets["main"])
    }

    runs {
        named("client") {
            // Use JetBrains Runtime (JCEF) version 17.
            vmArg("-XX:+AllowEnhancedClassRedefinition")
        }
    }
}

dependencies {
    minecraft("com.mojang:minecraft:${Versions.minecraft}")
    mappings("net.fabricmc:yarn:${Versions.Dependencies.yarn}:v2")
    modImplementation("net.fabricmc:fabric-loader:${Versions.Dependencies.loader}")

    modImplementation("net.fabricmc.fabric-api:fabric-api:${Versions.Libraries.api}")
    modImplementation("net.fabricmc:fabric-language-kotlin:${Versions.Libraries.kotlin}")
}

kotlin {
    jvmToolchain(17)
}

java {
    withSourcesJar()

    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

base {
    project.version = "${Versions.minecraft}_v${Constants.Mod.version}"
    project.group = Constants.Mod.group
}

tasks {
    jar {
        from("LICENSE") {
            rename { "${it}_${project.name}" }
        }
    }

    processResources {
        inputs.property("version", Constants.Mod.version)
        filteringCharset = "UTF-8"

        filesMatching("fabric.mod.json") {
            expand(
                "version" to Constants.Mod.version,
                "mcVersion" to Versions.minecraft,
                "kotlinVersion" to Versions.kotlin,
                "loaderVersion" to Versions.Dependencies.loader,
                "apiVersion" to Versions.Libraries.api,
            )
        }
    }

    withType<JavaCompile> {
        options.release.set(17)
    }
}
