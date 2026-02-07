import de.florianreuth.baseproject.*

plugins {
    `java-library`
    id("me.champeau.jmh") version "0.7.3"
    id("de.florianreuth.baseproject")
}

setupProject()
setupPublishing()

configureTestTasks()

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.14.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    jmh("org.openjdk.jmh:jmh-core:1.37")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}
