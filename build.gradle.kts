import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.shadow)
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

val buildPath = File("C:/Users/blugo/Desktop")

repositories {
    mavenCentral()
//    maven("https://repo.blugon.kr/repository/maven-public/")
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(libs.coroutine)
//    implementation("kr.blugon:asdf:latest.release")
}

tasks {
    compileKotlin {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_21)
        }
    }

    jar { this.build() }
    shadowJar { this.build() }
}

fun Jar.build() {
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