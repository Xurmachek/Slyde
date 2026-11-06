plugins {
    id("jfmod") version "1.10-SNAPSHOT"
}

loom {
    accessWidenerPath.set(file("src/client/resources/slyde.accesswidener"))
}

allprojects { group = "dev.jfronny.mods" }
base.archivesName = "slyde"

jfMod {
    minecraftVersion = "26.3"
    loaderVersion = "0.19.4"
    libJfVersion = "26.3.0"
    fabricApiVersion = "0.160.5+26.3"

    modrinth {
        projectId = "slyde"
        requiredDependencies.add("libjf")
        optionalDependencies.add("modmenu")
    }
    curseforge {
        projectId = "411386"
        requiredDependencies.add("libjf")
        optionalDependencies.add("modmenu")
    }
}

repositories {
    maven("https://api.modrinth.com/maven")
}

dependencies {
    modLocalRuntime(modCompileOnly("maven.modrinth:sodium:mc26.3-0.9.2-fabric")!!)

    modImplementation("dev.jfronny.libjf:libjf-config-core-v2")

    // For testing in dev environment
    modLocalRuntime("net.fabricmc.fabric-api:fabric-api")
    modLocalRuntime("dev.jfronny.libjf:libjf-devutil")
    modLocalRuntime("dev.jfronny.libjf:libjf-config-ui-tiny")
    modLocalRuntime("com.terraformersmc:modmenu:21.0.0-beta.1")
}
