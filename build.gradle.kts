plugins {
    `java-library`
    `maven-publish`
    signing
    id("me.champeau.jmh") version "0.7.3"
}

group = property("maven_group") as String
version = property("maven_version") as String
description = property("maven_description") as String

base {
    archivesName.set(property("maven_name") as String)
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.11.4"))
    testImplementation("org.junit.jupiter:junit-jupiter")

    jmh("org.openjdk.jmh:jmh-core:1.37")
    jmh("org.openjdk.jmh:jmh-generator-annprocess:1.37")
    jmhAnnotationProcessor("org.openjdk.jmh:jmh-generator-annprocess:1.37")
}

java {
    withSourcesJar()
    withJavadocJar()

    toolchain {
        languageVersion.set(JavaLanguageVersion.of(8))
    }

    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
}

tasks {
    jar {
        val projectName = project.name

        // Rename the project's license file to LICENSE_<project_name> to avoid conflicts
        from("LICENSE") {
            rename { "LICENSE_${projectName}" }
        }
    }

    withType<PublishToMavenRepository>().configureEach {
        dependsOn(withType<Sign>())
    }

    test {
        useJUnitPlatform()
        testLogging {
            events("passed", "skipped", "failed")
        }
        maxParallelForks = Runtime.getRuntime().availableProcessors()
    }
}

publishing {
    repositories {
        maven {
            name = "reposilite"
            url = uri("https://maven.lenni0451.net/" + if (version.toString().endsWith("SNAPSHOT")) "snapshots" else "releases")
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
        maven {
            name = "ossrh"
            val releasesUrl = "https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/"
            val snapshotsUrl = "https://s01.oss.sonatype.org/content/repositories/snapshots/"
            url = uri(if (version.toString().endsWith("SNAPSHOT")) snapshotsUrl else releasesUrl)
            credentials(PasswordCredentials::class)
            authentication {
                create<BasicAuthentication>("basic")
            }
        }
    }
    publications {
        create<MavenPublication>("maven") {
            groupId = project.group.toString()
            artifactId = project.name
            version = project.version.toString()

            from(components["java"])

            pom {
                name.set(artifactId)
                description.set(project.description)
                url.set("https://github.com/FlorianMichael/DietrichEvents2")
                licenses {
                    license {
                        name.set("Apache-2.0 license")
                        url.set("https://github.com/FlorianMichael/DietrichEvents2/blob/main/LICENSE")
                    }
                }
                developers {
                    developer {
                        id.set("FlorianMichael")
                        name.set("EnZaXD")
                        email.set("florian.michael07@gmail.com")
                    }
                }
                scm {
                    connection.set("scm:git:git://github.com/FlorianMichael/DietrichEvents2.git")
                    developerConnection.set("scm:git:ssh://github.com/FlorianMichael/DietrichEvents2.git")
                    url.set("https://github.com/FlorianMichael/DietrichEvents2")
                }
            }
        }
    }
}

signing {
    isRequired = false
    sign(publishing.publications["maven"])
}
