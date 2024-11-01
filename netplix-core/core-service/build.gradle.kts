dependencies {
    implementation(project(":netplix-core:core-domain"))
    implementation(project(":netplix-core:core-port"))
    implementation(project(":netplix-core:core-usecase"))
    implementation(project(":netplix-commons"))

    implementation(project(":netplix-adapters:adapter-http"))
    implementation(project(":netplix-adapters:adapter-persistence"))
    implementation(project(":netplix-adapters:adapter-redis"))

    implementation("org.springframework:spring-context")
    implementation("org.springframework.data:spring-data-commons")

    implementation("io.jsonwebtoken:jjwt-api")
    implementation("io.jsonwebtoken:jjwt-impl")
    implementation("io.jsonwebtoken:jjwt-jackson")
}
