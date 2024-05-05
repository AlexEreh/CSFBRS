# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.kts.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile
-keep class com.gargoylesoftware.** { *; }
-keep class javax.xml.** { *; }
-keep class net.sourceforge.htmlunit.** { *; }
-keep class netscape.** { *; }
-keep class org.apache.** { *; }
-keep class org.htmlunit.org.apache.** { *; }
-keep class org.w3c.dom.** { *; }
-keep class org.xml.sax.** { *; }
-keepclassmembers class org.apache.http.** { *; }
-keepclassmembers class io.ktor.** { *; }
-keepclassmembers class kotlinx.** {
    *;
    volatile <fields>;
}