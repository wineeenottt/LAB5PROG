plugins {
    id("java")
    application
}

group = "org.wineeenottt"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
}

application {
    mainClass.set("org.wineeenottt.Main")
}

tasks.jar {
    manifest {
        attributes["Main-Class"] = "org.wineeenottt.Main"
    }
}

tasks.javadoc {
    source = sourceSets["main"].allJava
    classpath = sourceSets["main"].compileClasspath
    setDestinationDir(file("${projectDir}/docs/javadoc"))
    options {
        encoding = "UTF-8"
        (options as StandardJavadocDocletOptions).links("https://docs.oracle.com/en/java/javase/17/docs/api/")
    }
    doLast {
        exec {
            commandLine("open", "${projectDir}/docs/javadoc/index.html")
        }
    }
}

tasks.clean {
    delete("docs/javadoc")
}