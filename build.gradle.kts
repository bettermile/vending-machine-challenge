import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.jmailen.gradle.kotlinter.tasks.LintTask

plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.kotlinter)
}

configure(subprojects) {
    group = "com.bettermile"

    apply(plugin = "kotlin")
    apply(plugin = "org.jmailen.kotlinter")

    dependencies {
        testImplementation(platform(rootProject.libs.junit.bom))
        testImplementation(rootProject.libs.junit.jupiter.api)
        testImplementation(rootProject.libs.hamkrest)
        testImplementation(rootProject.libs.bundles.mockito)
        testRuntimeOnly(rootProject.libs.junit.jupiter.engine)
        testRuntimeOnly(rootProject.libs.junit.platform.launcher)
    }

    kotlin {
        jvmToolchain(21)
        sourceSets.all {
            languageSettings {
                languageVersion = "2.0"
            }
        }
    }

    tasks {
        withType<KotlinCompile> {
            compilerOptions {
                freeCompilerArgs.add("-Xconsistent-data-class-copy-visibility")
            }
        }

        withType<Test> {
            useJUnitPlatform()
            testLogging {
                showExceptions = true
                exceptionFormat = TestExceptionFormat.FULL
                events("passed", "skipped", "failed")

                addTestListener(object : TestListener {
                    override fun beforeSuite(suite: TestDescriptor) {}
                    override fun beforeTest(testDescriptor: TestDescriptor) {}
                    override fun afterTest(testDescriptor: TestDescriptor, result: TestResult) {}
                    override fun afterSuite(suite: TestDescriptor, result: TestResult) {
                        if (suite.parent == null) {
                            println(
                                "\n${result.resultType}: ${result.testCount} tests, " +
                                    "${result.successfulTestCount} passed, " +
                                    "${result.failedTestCount} failed, " +
                                    "${result.skippedTestCount} skipped " +
                                    "in ${(result.endTime - result.startTime).toFloat() / 1000}s"
                            )
                        }
                    }
                })
            }
        }

        withType<LintTask> {
            shouldRunAfter("test")
        }

        withType<Jar> {
            shouldRunAfter("lintKotlin")
        }
    }
}
