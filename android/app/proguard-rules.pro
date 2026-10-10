# Methods called from the page through window.NexAndroid must keep their names.
-keepclassmembers class com.nexelder.cuidador.MainActivity$Bridge {
    @android.webkit.JavascriptInterface <methods>;
}
-keepattributes JavascriptInterface
