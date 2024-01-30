import org.gradle.nativeplatform.platform.internal.DefaultNativePlatform.getCurrentOperatingSystem

plugins {
    application
}

val platform = when {
    getCurrentOperatingSystem().isWindows -> "win"
    getCurrentOperatingSystem().isLinux -> "linux"
    getCurrentOperatingSystem().isMacOsX -> "mac"
    else -> throw UnsupportedOperationException("Operating system ${getCurrentOperatingSystem()} not supported yet")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(group = "org.openjfx", name = "javafx-base", version = "17.0.9", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-graphics", version = "17.0.9", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-controls", version = "17.0.9", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-fxml", version = "17.0.9", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-web", version = "17.0.9", classifier = platform)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.9.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(17))
    }
}

application {
    mainClass.set("lp.awrsp.assessment.project.gr.AppApplicationRunner")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}