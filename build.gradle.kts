plugins {
    kotlin("jvm") version "1.9.21"
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val buildPath = File("C:/Users/blugo/Desktop")
val kotlinVersion = kotlin.coreLibrariesVersion

repositories {
    mavenCentral()
    maven("https://repo.blugon.kr/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
//    implementation("kr.blugon:asdf:latest.release")
}

extra.apply {
    set("ProjectName", project.name)
    set("ProjectVersion", project.version)
    set("KotlinVersion", kotlinVersion)
}

tasks {
    compileKotlin {
        kotlinOptions.jvmTarget = JavaVersion.VERSION_21.toString()
    }

    processResources {
        filesMatching("*.yml") {
            expand(project.properties)
            expand(extra.properties)
        }
    }

    jar { this.build() }
    shadowJar { this.build() }
}

fun Jar.build() {
    val file = File("./build/libs/${project.name}.jar")
    if(file.exists()) file.delete()
    archiveBaseName.set(project.name) //Project Name
    archiveFileName.set("${project.name}.jar") //Build File Name
    archiveVersion.set(project.version.toString()) //Version
    from(sourceSets["main"].output)

    doLast {
        copy {
            from(archiveFile) //Copy from
            into(buildPath) //Copy to
        }
    }

    manifest {
        attributes["Main-Class"] = "${project.group}.${project.name.lowercase()}.${project.name}Kt" //Main File
    }
}