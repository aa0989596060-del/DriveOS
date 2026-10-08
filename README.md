# DriveOS

A standalone Android driving launcher inspired by modern in-car dashboards.

## What it does
- Runs entirely on the phone; no car/head-unit connection required.
- Landscape, full-screen dashboard UI.
- Large buttons for Navigation, Phone, Music and Apps.
- App drawer automatically lists launchable apps installed on the phone.
- Navigation opens a map intent and lets Android choose the installed map app.
- Phone opens the dialer.
- Music opens a media-capable app when available.
- Includes a simple Settings screen for theme and launcher behavior.

## Build
Open this repository in Android Studio and build `app`.

Or push it to GitHub and let `.github/workflows/build-apk.yml` create a downloadable debug APK.

## Install
The GitHub Actions artifact is an APK intended for testing/sideloading. On the phone, allow installation from the browser/file manager when Android asks.

## Important
DriveOS does not pretend to be Apple CarPlay or Android Auto. It is a standalone launcher/dashboard that uses Android intents to open other apps. Third-party apps control their own screens and permissions.
