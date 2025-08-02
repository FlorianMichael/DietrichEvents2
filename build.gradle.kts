import de.florianmichael.baseproject.*

plugins {
    `java-library`
    id("me.champeau.jmh") version "0.7.3"
    id("de.florianmichael.baseproject.BaseProject")
}

setupProject()
setupPublishing()

configureTestTasks()

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    jmh("org.openjdk.jmh:jmh-core:1.37")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}
