# VIVIEN App - Build Instructions

## Quick APK Build

### On Linux/Mac:
```bash
chmod +x build-apk.sh
./build-apk.sh
```

### On Windows:
```cmd
build-apk.bat
```

### Manual Build:
```bash
./gradlew clean
./gradlew assembleRelease
```

## Output Files

After successful build, you'll find:
- `vivien-app-apk/VIVIEN.apk` - Main application
- `VIVIEN-APK-Package.zip` - Complete package

## Installation

1. Transfer `VIVIEN.apk` to your phone
2. Enable "Unknown Sources" in Settings
3. Open and install the APK
4. Launch VIVIEN from your apps

## Package Specs

- **Size**: ~15 MB
- **Android**: 7.0+ (API 24+)
- **Format**: APK (Android Package)
- **Build Tool**: Gradle + Kotlin
- **Signing**: Debug (for testing)

## Release Build

For production/release:
```bash
./gradlew bundleRelease
```

Enjoy! 🎮