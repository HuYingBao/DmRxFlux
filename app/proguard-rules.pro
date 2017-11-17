# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in E:\Android\Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
#retrolambda
-dontwarn java.lang.invoke.*

#Hotfix
#基线包使用，生成mapping.txt
-printmapping mapping.txt
#生成的mapping.txt在app/buidl/outputs/mapping/release路径下，移动到/app路径下
#修复后的项目使用，保证混淆结果一致
#-applymapping mapping.txt
-keep class com.taobao.sophix.**{*;}
-keep class com.ta.utdid2.device.**{*;}
#防止inline
-dontoptimize

#ANALYTICS
-keep class com.alibaba.sdk.android.**{*;}
-keep class com.ut.**{*;}
-keep class com.ta.**{*;}

#蒲公英
-libraryjars libs/pgyer_sdk_x.x.jar
-dontwarn com.pgyersdk.**
-keep class com.pgyersdk.** { *; }

#DBFlow
-keep class * extends com.raizlabs.android.dbflow.config.DatabaseHolder { *; }

#防止混淆BaseRecyclerViewAdapterHelper
-keep class com.chad.library.adapter.** { *; }

#防止混淆retrofit2
-dontwarn retrofit2.**
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

#Glide
#防止混淆Glide的OkHttpGlideModule
-keep class com.bumptech.glide.integration.okhttp.OkHttpGlideModule
#防止混淆glide的GlideModule
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}
# for DexGuard only
-keepresourcexmlelements manifest/application/meta-data@value=GlideModule
