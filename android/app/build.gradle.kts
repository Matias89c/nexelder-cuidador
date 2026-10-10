plugins {
    id("com.android.application")
}

// The web panel at the repo root is the single source of truth: it is copied into the APK at build time.
val webRoot = rootProject.layout.projectDirectory.dir("..")
val syncWeb by tasks.registering(Sync::class) {
    from(webRoot) {
        include("index.html", "manifest.webmanifest", "icon-180.png", "icon-512.png", "fonts/**")
    }
    into(layout.buildDirectory.dir("generated/web/www"))
}

// Release signing comes from environment variables (set by CI from repository secrets, or locally).
val keystorePath: String? = System.getenv("NEX_KEYSTORE")
val hasSigning = keystorePath != null && file(keystorePath).exists()

android {
    namespace = "com.nexelder.cuidador"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.nexelder.cuidador"
        minSdk = 24
        targetSdk = 35
        versionCode = (System.getenv("NEX_VERSION_CODE") ?: "1").toInt()
        versionName = "1.0.0"
    }

    signingConfigs {
        if (hasSigning) {
            create("release") {
                storeFile = file(keystorePath!!)
                storePassword = System.getenv("NEX_KEYSTORE_PASSWORD")
                keyAlias = System.getenv("NEX_KEY_ALIAS")
                keyPassword = System.getenv("NEX_KEY_PASSWORD")
                enableV1Signing = true
                enableV2Signing = true
                enableV3Signing = true
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
            if (hasSigning) signingConfig = signingConfigs.getByName("release")
        }
    }

    sourceSets["main"].assets.srcDir(layout.buildDirectory.dir("generated/web"))

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    packaging {
        resources.excludes += setOf("META-INF/*.version", "META-INF/**/LICENSE*", "kotlin/**", "DebugProbesKt.bin")
    }

    dependenciesInfo {
        includeInApk = false
        includeInBundle = false
    }
}

tasks.named("preBuild") { dependsOn(syncWeb) }

dependencies {
    implementation("androidx.webkit:webkit:1.12.1")
    implementation("androidx.core:core:1.13.1")
}
