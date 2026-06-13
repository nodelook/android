import groovy.json.JsonSlurper

plugins {
    alias(libs.plugins.com.android.application)
}

android {
    namespace = "ir.ammari.nodelook"
    compileSdk = 37

    defaultConfig {
        applicationId = "ir.ammari.nodelook"
        minSdk = 1
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables.generatedDensities()
    }

    // https://stackoverflow.com/a/75544119
    packaging { dex { useLegacyPackaging = true } }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildTypes {
        getByName("release") {
            signingConfig = signingConfigs["debug"]
        }
    }

    buildFeatures { resValues = false }


    lint {
        warningsAsErrors = true
        abortOnError = true
        checkAllWarnings = true
        checkReleaseBuilds = true
        checkDependencies = true
        checkTestSources = true
        checkGeneratedSources = true
        baseline = file("lint-baseline.xml") // To update: ./gradlew updateLintBaseline
    }

    sourceSets {
        getByName("main") {
            java.directories += layout.buildDirectory.dir("generated/source/appsrc/main").get()
                .toString()
        }
    }
}

dependencies {}

val generateAppSrcTask by tasks.registering {
    operator fun File.div(child: String) = File(this, child)
    val dataDir = projectDir / ".." / "data"
    val jsonFiles = dataDir.listFiles { file ->
        when (file.name) {
            "dockerregistry.json", "global.json", "mirrors.json" -> false
            else -> true
        } && file.extension == "json"
    } ?: emptyArray()
    inputs.files(jsonFiles)
    val generatedAppSrcDir =
        layout.buildDirectory.get().asFile / "generated" / "source" / "appsrc" / "main"
    val generateDir = generatedAppSrcDir / "ir" / "ammari" / "nodelook"
    val dataOutput = generateDir / "Data.java"
    outputs.files(dataOutput)
    doLast {
        generateDir.mkdirs()
        val jsonSlurper = JsonSlurper()
        val source = jsonFiles.joinToString("\n") { file ->
            val list = jsonSlurper.parse(file) as List<*>
            val entry = file.nameWithoutExtension.replaceFirstChar { it.uppercase() }
            "        put(\"$entry\", new SiteInfo[]{\n" + list.joinToString(",\n") {
                val item = it as Map<*, *>
                val name = when (val name = item["name"]) {
                    is Map<*, *> -> name["en"] as String
                    else -> name as String
                }
                val url = item["url"] as String
                val status = item["status"] as String
                """                new SiteInfo("$name", "$url", "$status")"""
            } + "\n        });"
        }
        dataOutput.writeText(
            """package ${android.defaultConfig.applicationId};

import java.util.LinkedHashMap;
import java.util.Map;

public class Data {
    public static final Map<String, SiteInfo[]> entries = new LinkedHashMap<>() {{
$source
    }};
}"""
        )
    }
}
tasks.named("preBuild").configure { dependsOn(generateAppSrcTask) }
