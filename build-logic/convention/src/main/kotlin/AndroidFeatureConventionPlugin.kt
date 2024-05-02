import com.android.build.gradle.LibraryExtension
import com.alexereh.csfbrs.configureGradleManagedDevices
import com.alexereh.csfbrs.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("csfbrs.android.library")
            }
            extensions.configure<LibraryExtension> {
                testOptions.animationsDisabled = true
                configureGradleManagedDevices(this)
            }

            dependencies {
                add("implementation", project(":common:ui"))
            }
        }
    }
}