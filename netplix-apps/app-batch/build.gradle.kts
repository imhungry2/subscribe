dependencies {
    implementation(project(":netplix-adapters:adapter-http"))
    implementation(project(":netplix-adapters:adapter-persistence"))
    implementation(project(":netplix-commons"))
    implementation(project(":netplix-core:core-domain"))
    implementation(project(":netplix-core:core-service"))
    implementation(project(":netplix-core:core-usecase"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-batch")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework:spring-tx")

    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-mysql")

    runtimeOnly("com.mysql:mysql-connector-j")
}

val appMainClassName = "com.fastcampus.netplix.NetplixBatchApplication"
tasks.getByName<org.springframework.boot.gradle.tasks.bundling.BootJar>("bootJar") {
    mainClass.set(appMainClassName)
    archiveClassifier.set("boot")
}
