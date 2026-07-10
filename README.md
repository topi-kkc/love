# VIVIEN - 3D Cube Animation App

🎨 تطبيق محمول يعرض مكعب هندسي ثلاثي الأبعاد متحرك يتغير كل ثانية برسم حرف من أحرف كلمة VIVIEN.

## المميزات

- 🎨 رسومات ثلاثية الأبعاد (3D Graphics)
- ⚡ أداء خفيف وسريع
- 🎭 واجهة مستخدم أنيقة
- 📱 متوافق مع جميع أجهزة Android من الإصدار 24 فما فوق
- 🌈 ألوان نيون جميلة (Cyan & Hot Pink)

## المتطلبات

- Android SDK 34+
- Kotlin 1.9.0+
- Gradle 8.1.0+
- Java 11+

## تحميل APK

انتقل إلى قسم **Releases** في المستودع وحمل آخر نسخة من ملف APK.

## البناء يدويا

```bash
# استنساخ المستودع
git clone https://github.com/topi-kkc/love.git
cd love

# بناء ملف APK
./gradlew clean build

# بناء ��لف APK للإصدار
./gradlew assembleRelease
```

## التثبيت

```bash
./gradlew installRelease
```

أو

```bash
adb install -r build/outputs/apk/release/love-release.apk
```

## الهيكل

```
love/
├── app/
│   ├── build.gradle.kts
│   ├── AndroidManifest.xml
│   ├── src/
│   │   └── main/
│   │       ├── kotlin/com/vivien/love/
│   │       │   ├── MainActivity.kt
│   │       │   └── ui/
│   │       │       ├── CubeAnimation.kt
│   │       │       └── theme/
│   │       │           ├── Theme.kt
│   │       │           └── Type.kt
│   │       └── res/
│   │           └── values/
│   │               ├── strings.xml
│   │               ├── colors.xml
│   │               └── themes.xml
│   └── proguard-rules.pro
├── build.gradle.kts
├── settings.gradle.kts
└── README.md
```

## المميزات التقنية

- **Jetpack Compose**: بناء واجهات مستخدم حديثة وتفاعلية
- **3D Graphics**: رسومات ثلاثية الأبعاد محسوبة رياضياً
- **Kotlin Coroutines**: معالجة غير متزامنة سلسة
- **Material Design 3**: تصميم حديث وجميل

## الأداء

- حجم التطبيق: ~15 MB
- استهلاك الذاكرة: منخفض جداً
- توافقية الأجهزة: Android 7.0+

## المطور

تم الإنشاء بواسطة: **GitHub Copilot**

## الترخيص

MIT License - انظر ملف LICENSE للتفاصيل

## الدعم

للإبلاغ عن مشاكل أو الاقتراحات، يرجى فتح issue في المستودع.
