group = "it.unibo.grubclash"
version = "1.0-SNAPSHOT"

plugins {
    id("java")
    id("application")
    id("org.openjfx.javafxplugin") version "0.1.0"
}

repositories {
    mavenCentral()
}

javafx {
    version = "22"
    modules("javafx.controls", "javafx.fxml", "javafx.graphics")
}

application {
    mainClass.set("it.unibo.grubclash.Main")
}