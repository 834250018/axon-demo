plugins {
    id 'java'
}


sourceCompatibility = 1.8

repositories {
    mavenCentral()
}

dependencies {
    compile project(":${module}-api")
}
