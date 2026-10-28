plugins { id("com.android.application") }
android {
    namespace = "app.tini.android"
    compileSdk = 34
    defaultConfig {
        applicationId = "app.tini.android"
        minSdk = 26
        targetSdk = 34
        versionCode = (System.getenv("GITHUB_RUN_NUMBER") ?: "1").toInt()
        versionName = "1." + (System.getenv("GITHUB_RUN_NUMBER") ?: "0")
    }
    signingConfigs {
        create("tini") {
            storeFile = file("../tini.jks"); storePassword = "tinitini"; keyAlias = "tini"; keyPassword = "tinitini"
        }
    }
    buildTypes {
        getByName("release") { isMinifyEnabled = false; signingConfig = signingConfigs.getByName("tini") }
    }
    compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }
}
