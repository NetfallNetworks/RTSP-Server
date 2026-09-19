pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "RTSP-Server"
// ":app" (pedroSG94's demo) is excluded from this fork's build entirely -- it depends on
// both ":rtspserver" (which transitively pulls in our patched RootEncoder fork) and
// upstream RootEncoder's "extra-sources" module directly, which pulls in unpatched
// upstream "encoder" -- both ending up on its classpath simultaneously fails JitPack's
// build with duplicate-class errors. We only need ":rtspserver" published; the demo app
// was never part of what this fork exists for.
include(":rtspserver")
