plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
}

android {
    namespace = "com.example.filmania"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.filmania"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }

    viewBinding{
        enable = true
    }
}

dependencies {

    val lifecycleVersion = "2.6.2"
    val glideVersion = "4.12.0"
    val coroutinesVersion = "1.6.4"
    val livedataVersion = "2.4.0"
    val retrofit_version = "2.9.0"
    val lifecycleruntimektx_version = "2.5.1"
    val caverrock_version = "1.4"

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    // Retrofit
    implementation ("com.squareup.retrofit2:retrofit:$retrofit_version")
    // Gson
    implementation ("com.squareup.retrofit2:converter-gson:$retrofit_version")

    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$livedataVersion")
    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:$coroutinesVersion")

    //Lifecycle RunTime
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:$lifecycleruntimektx_version")

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycleVersion")

    // Glide
    implementation("com.github.bumptech.glide:glide:$glideVersion")
    kapt("com.github.bumptech.glide:compiler:$glideVersion")

    // CircleImageView
    implementation ("de.hdodenhof:circleimageview:3.1.0")

    // Picasso
    implementation("com.squareup.picasso:picasso:2.71828")


    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}