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
    implementation(group = "org.openjfx", name = "javafx-base", version = "21.0.1", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-graphics", version = "21.0.1", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-controls", version = "21.0.1", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-fxml", version = "21.0.1", classifier = platform)
    implementation(group = "org.openjfx", name = "javafx-web", version = "21.0.1", classifier = platform)

    testImplementation(group = "org.junit.jupiter", name = "junit-jupiter", version = "5.9.3")
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(21))
    }
}

application {
    mainClass.set("restaurantmanagementapp.App")
}

tasks.named<Test>("test") {
    useJUnitPlatform()
}
