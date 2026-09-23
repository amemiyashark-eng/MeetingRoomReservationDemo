# MeetingRoomReservationDemo

Spring Boot と PostgreSQL で構築した会議室予約システムです。
AWS (EC2 / RDS) 上にデプロイし、動作確認を完了しています。（※現在はコスト抑制のためAWS環境を停止中）

## 動作デモ

<img width="1376" height="728" alt="Animation" src="https://github.com/user-attachments/assets/3d116d11-4749-4e4a-8558-3e617a4e9ad3" />
## 使用技術
- **言語 / FW:** Java 21, Spring Boot 3.1.1, Spring Security, Thymeleaf
- **データベース:** PostgreSQL
- **インフラ / クラウド:** AWS (EC2, RDS)
- **ビルドツール:** Maven

## 主な機能
- **ユーザー認証・認可:** Spring Securityによるログイン制御
- **会議室一覧・予約機能:** 日付ごとの空き状況確認、新規予約登録
- **バリデーション:** 過去日選択時の予約エラー制御

## 技術的な工夫・苦労した点
- **AWSインフラ構築:** EC2とRDSを組み合わせ、RDSへのアクセスはEC2からのセキュリティグループのみ許可する安全な構成を構築。
- **トラブルシューティング:** Spring Security設定時におけるデータベース照合エラーの解明、およびHibernateのSQLログ解析によるパフォーマンス最適化を実施。
