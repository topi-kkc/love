#!/bin/bash

# VIVIEN App APK Build Script
# This script builds the complete APK package

echo "================================"
echo "VIVIEN App - APK Build Script"
echo "================================"
echo ""

# Check Java version
echo "✓ Checking Java version..."
java -version

# Check Gradle
echo ""
echo "✓ Checking Gradle..."
chmod +x gradlew

# Clean previous builds
echo ""
echo "✓ Cleaning previous builds..."
./gradlew clean

# Build Release APK
echo ""
echo "✓ Building Release APK..."
./gradlew assembleRelease -x lint

# Check if build was successful
if [ -f "app/build/outputs/apk/release/app-release.apk" ]; then
    echo ""
    echo "✅ APK Built Successfully!"
    echo ""
    
    # Create package directory
    mkdir -p vivien-app-apk
    
    # Copy APK
    cp app/build/outputs/apk/release/app-release.apk vivien-app-apk/VIVIEN.apk
    
    echo "📦 Creating Installation Package..."
    
    # Create README
    cat > vivien-app-apk/INSTALL.txt << 'EOF'
╔════════════════════════════════════════════════════════════════╗
║          VIVIEN 3D Cube Animation - Installation              ║
╚════════════════════════════════════════════════════════════════╝

📱 QUICK INSTALLATION:

1. Transfer VIVIEN.apk to your Android phone
2. Open Settings → Security → Enable "Unknown Sources"
3. Open File Manager and tap VIVIEN.apk
4. Click "Install"
5. Done! Open VIVIEN from your apps

════════════════════════════════════════════════════════════════

✨ APP FEATURES:
✓ 3D Animated Cube
✓ VIVIEN Letters Display
✓ Beautiful Neon Colors
✓ Lightweight & Fast
✓ No Internet Required

════════════════════════════════════════════════════════════════

📊 REQUIREMENTS:
• Android 7.0 or higher
• 15 MB free storage
• Any smartphone with GPU

════════════════════════════════════════════════════════════════

🆘 TROUBLESHOOTING:

Issue: "Cannot install unknown apps"
→ Enable it in Settings > Security > Unknown Sources

Issue: "App won't open"
→ Restart phone and try again

Issue: "File corrupted"
→ Download again from official source

════════════════════════════════════════════════════════════════

Enjoy VIVIEN! 🎮
EOF
    
    # Create ZIP package
    cd vivien-app-apk
    zip -r ../VIVIEN-APK-Package.zip .
    cd ..
    
    echo ""
    echo "✅ Package Created!"
    echo ""
    echo "📁 Files ready:"
    ls -lh vivien-app-apk/VIVIEN.apk
    ls -lh VIVIEN-APK-Package.zip
    
else
    echo ""
    echo "❌ Build Failed!"
    echo "Check the error messages above."
    exit 1
fi

echo ""
echo "✅ All Done! Your APK is ready to download."
echo ""