# DriveOS GitHub 完整部署

## A. 建立 GitHub Repository
在 GitHub 建立空白 Repository，例如：
`DriveOS`

不要勾選 Initialize with README，因為本專案已經有 README。

## B. 上傳
把本資料夾內所有檔案上傳到 repository 根目錄。

## C. 編譯 APK
進入 GitHub 的 Actions，選擇 `Build DriveOS APK`，按 `Run workflow`。
完成後到該次 workflow 的 Artifacts 下載 `DriveOS-debug-apk`。

## D. 發布版本
建立 tag，例如：
`v1.0.0`

GitHub Actions 會自動建立 release workflow 所需的 release APK artifact。

## E. 手機安裝
把 APK 傳到 Android 手機，允許該瀏覽器/檔案管理器安裝未知來源 App 後安裝。

## 注意
這個專案目前是「可安裝、可複製、可由 GitHub Actions 自動編譯」的第一版。
正式公開給大量使用者前，建議使用自己的 Android release signing key 簽署 APK，並將 key 安全放入 GitHub Actions Secrets，而不是提交到 repository。
