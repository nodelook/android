import groovy.json.JsonSlurper

plugins {
    alias(libs.plugins.com.android.application)
}

operator fun File.div(child: String) = File(this, child)
val generatedAppSrcDir =
    layout.buildDirectory.get().asFile / "generated" / "source" / "appsrc" / "main"

android {
    namespace = "ir.ammari.nodelook"
    compileSdk = 37

    defaultConfig {
        applicationId = "ir.ammari.nodelook"
        minSdk = 1
        targetSdk = 37
        versionCode = 2
        versionName = "1.1"

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

    sourceSets { getByName("main") { java.directories += generatedAppSrcDir.toString() } }
}

dependencies {}

val generateAppSrcTask by tasks.registering {
    val dataDir = projectDir / ".." / "data"
    if (!dataDir.exists()) error("Please make a rescursive clone in order to have the data/ folder")
    val jsonFiles = dataDir.listFiles { file ->
        !file.name.startsWith(".") && file.extension == "json"
    } ?: emptyArray()
    jsonFiles.sortBy { it.name }
    inputs.files(jsonFiles)
    val generateDir = generatedAppSrcDir / "ir" / "ammari" / "nodelook"
    val dataOutput = generateDir / "Data.java"
    outputs.files(dataOutput)
    doLast {
        generateDir.mkdirs()
        val jsonSlurper = JsonSlurper()
        val source = jsonFiles.joinToString(",\n") { file ->
            println("Parsing $file")
            val root = jsonSlurper.parse(file) as Map<*, *>
            val items = root["items"] as List<*>
            val title = (root["name"] as Map<*, *>)["en"] as String
            println("Title: $title")
            val description = (root["description"] as Map<*, *>)["en"] as String
            println("Description: $description")
            "            new Category(\"$title\", \"$description\", new SiteInfo[]{\n" + items.joinToString(
                ",\n"
            ) {
                val item = it as Map<*, *>
                val name = (item["name"] as Map<*, *>)["en"] as String
                println("Adding $name")
                val url = item["url"] as String
                val shouldContain = item["shouldContain"] as String
                """                    new SiteInfo("$name", "$url", "$shouldContain")"""
            } + "\n            })"
        }
        dataOutput.writeText(
            """package ${android.defaultConfig.applicationId};

import androidx.annotation.NonNull;

class Data {
    static final @NonNull Category[] categories = {
$source
    };
}
"""
        )
    }
}
tasks.named("preBuild").configure { dependsOn(generateAppSrcTask) }
