# 株式会社ゆめみ Android エンジニアコードチェック課題

## 概要

本プロジェクトは株式会社ゆめみ（以下弊社）が、弊社に Android エンジニアを希望する方に出す課題のベースプロジェクトです。本課題が与えられた方は、下記の概要を詳しく読んだ上で課題を取り組んでください。

## アプリ仕様

本アプリは GitHub のリポジトリを検索するアプリです。

<img src="docs/app.gif" width="320">

### 環境

- IDE：Android Studio Arctic Fox | 2020.3.1 Patch 1
- Kotlin：1.6.10
- Java：11
- Gradle：7.0.3
- minSdk：23
- targetSdk：31


### 動作

1. 何かしらのキーワードを入力
2. GitHub API（`search/repositories`）でリポジトリを検索し、結果一覧を概要（リポジトリ名）で表示
3. 特定の結果を選択したら、該当リポジトリの詳細（リポジトリ名、オーナーアイコン、プロジェクト言語、Star 数、Watcher 数、Fork 数、Issue 数）を表示

## 変更点
画面上の変更点として、検索中にアニメーションを追加した点と、最近更新されているかいないか、スターやコメントをどれだけもらえているかといった内容でソートを行えるようにした点があります。

<img src = "https://user-images.githubusercontent.com/76822642/150087969-ddd0cbe9-876e-4487-9885-4d277a7345f5.jpg" width = "200dp">
<img src = "https://user-images.githubusercontent.com/76822642/150087992-850f3e3b-a457-4d05-a625-91fcbaffac9d.jpg" width = "200dp">


