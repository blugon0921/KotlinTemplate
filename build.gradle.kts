import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    kotlin("jvm") version "2.0.0"
    id("com.gradleup.shadow") version "8.3.0"
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
//    maven("https://repo.blugon.kr/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))
//    implementation("kr.blugon:asdf:latest.release")
}

//extra.apply {
//    set("ProjectName", project.name)
//    set("ProjectVersion", project.version)
//    set("KotlinVersion", kotlinVersion)
//}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

//    processResources {
//        filesMatching("*.yml") {
//            expand(project.properties)
//            expand(extra.properties)
//        }
//    }

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
//        attributes["Main-Class"] = "${project.group}.${project.name.lowercase()}.${project.name}Kt" //Main File
        attributes["Main-Class"] = "${project.group}.${project.name.lowercase()}.${project.name}" //Main File
    }
}