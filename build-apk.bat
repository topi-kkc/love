@echo off
REM VIVIEN App APK Build Script for Windows
REM This script builds the complete APK package

echo ================================
echo VIVIEN App - APK Build Script
echo ================================
echo.

REM Check Java version
echo Checking Java version...
java -version

REM Check Gradle
echo.
echo Checking Gradle...

REM Clean previous builds
echo.
echo Cleaning previous builds...
call gradlew clean

REM Build Release APK
echo.
echo Building Release APK...
call gradlew assembleRelease -x lint

REM Check if build was successful
if exist "app\build\outputs\apk\release\app-release.apk" (
    echo.
    echo APK Built Successfully!
    echo.
    
    REM Create package directory
    mkdir vivien-app-apk
    
    REM Copy APK
    copy "app\build\outputs\apk\release\app-release.apk" "vivien-app-apk\VIVIEN.apk"
    
    echo Creating Installation Package...
    
    REM Create ZIP
    powershell -Command "Compress-Archive -Path 'vivien-app-apk' -DestinationPath 'VIVIEN-APK-Package.zip' -Force"
    
    echo.
    echo All Done! Your APK is ready.
    
) else (
    echo.
    echo Build Failed!
    echo Check the error messages above.
    exit /b 1
)

pause