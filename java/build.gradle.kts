plugins {
    id("java")
}

repositories{
    mavenCentral()
    google()
    maven { url = uri("https://jogamp.org/deployment/maven") }
}

sourceSets{
    main{
        java{
            srcDirs("src")
            exclude("processing/mode/java/preproc/**")
        }
    }
    test{
        java{
            srcDirs("test")
        }
    }
}

dependencies{
    implementation(project(":app"))
    implementation(project(":core"))
    implementation(project(":java:preprocessor"))

    implementation(libs.eclipseJDT)
    implementation(libs.eclipseJDTCompiler)
    implementation(libs.classpathExplorer)
    implementation(libs.netbeansSwing)
    implementation(libs.ant)
    implementation(libs.lsp4j)
    implementation(libs.jsoup)
    implementation(libs.antlr)

    testImplementation(libs.junit)
    testImplementation(libs.mockito)
}

tasks.compileJava{
    options.encoding = "UTF-8"
}

tasks.register<Copy>("extraResources"){
    from(".")
    include("keywords.txt")
    include("theme/**/*")
    include("application/**/*")
    into( layout.buildDirectory.dir("resources-bundled/common/modes/java"))
}

val libraries = arrayOf("dxf","io","net","pdf","serial","svg")
libraries.forEach { library ->
    tasks.register<Copy>("library-$library-extraResources"){
        dependsOn(project(":java:libraries:$library").tasks.named("build"))
        from("libraries/$library")
        include("*.properties")
        include("library/**/*")
        include("examples/**/*")
        into( layout.buildDirectory.dir("resources-bundled/common/modes/java/libraries/$library"))
    }
    tasks.named("extraResources"){ dependsOn("library-$library-extraResources") }
}
tasks.jar { dependsOn("extraResources") }
tasks.processResources{ finalizedBy("extraResources") }
tasks.compileTestJava{ finalizedBy("extraResources") }

tasks.test {
    useJUnit()
}