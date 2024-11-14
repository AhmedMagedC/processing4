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
            srcDirs("src", "generated")
        }
    }
}

dependencies{
    implementation(project(":app"))
    implementation(project(":core"))



    implementation("org.eclipse.jdt:org.eclipse.jdt.core:3.37.0")
    implementation("com.google.classpath-explorer:classpath-explorer:1.0")
    implementation("org.netbeans.api:org-netbeans-swing-outline:RELEASE210")
    implementation("org.apache.ant:ant:1.10.14")
    implementation("org.eclipse.lsp4j:org.eclipse.lsp4j:0.22.0")
    implementation("org.jsoup:jsoup:1.17.2")
    implementation("org.antlr:antlr4:4.7.2")
}

// TODO: This is a temporary workaround until the resources are properly handled
tasks.register<Copy>("extraResources"){
    from(".")
    include("keywords.txt")
    into("build/resources/main")
}
tasks.jar { dependsOn("extraResources") }
tasks.processResources{ finalizedBy("extraResources") }
