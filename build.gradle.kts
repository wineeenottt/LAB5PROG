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

application{
    mainClass.set("org.wineeenottt.Main")
}

tasks.jar{
    manifest{
        attributes["Main-Class"] = "org.wineeenottt.Main"
    }
}