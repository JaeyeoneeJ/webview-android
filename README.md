# webvifew-android

## INTRODUCE
- 이 프로젝트는 Android Studio로 제작되었습니다.
- 이 프로젝트에서 궁극적으로 목표하는 바는 다음과 같습니다.
    - Android App(*.apk)로 webview 구현(브라우저 연동)
    - webview에서 CAMERA_CAPTURE(CAMERA) 권한 허용
    - System '뒤로 가기' 키를 통한 브라우저의 history 제어
- 이 프로젝트에 연결된 webview는 다음 프로젝트와 연동되어 있습니다.
    - QR Code를 읽고 결과 값을 반환하는 App은 아래 링크를 참조하여 주십시오.
    - [https://github.com/JaeyeoneeJ/webview](https://github.com/JaeyeoneeJ/webview)


## ENV
- 이 프로젝트는 아래와 같은 환경에서 제작되었습니다.

### Plugins
id 'com.android.application' version '7.0.2'
id 'com.android.library' version '8.0.2' apply false
id 'org.jetbrains.kotlin.android' version '1.7.20' apply false

### Android
- compileSdk 33
- minSdk 24
- targetSdk 33
