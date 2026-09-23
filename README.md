# MeetingRoomReservationDemo

Spring Boot と PostgreSQL で構築した会議室予約システムです。
AWS (EC2 / RDS) 上にデプロイし、動作確認を完了しています。（※現在はコスト抑制のためAWS環境を停止中）

## 動作デモ

<img width="1376" height="728" alt="Animation" src="https://github.com/user-attachments/assets/3d116d11-4749-4e4a-8558-3e617a4e9ad3" />(https://github.com/user-attachments/assets/3d116d11-4749-4e4a-8558-3e617a4e9ad3)" />

## 主な機能・バリデーション制御
- **ユーザー認証・認可:** Spring Securityによるログイン制御（一般ユーザー / 管理者権限）
- **会議室一覧・予約機能:** 日付ごとの空き状況確認、新規予約登録
- **入力チェック（バリデーション機能）:**
  - **過去日制御:** 過去の日付は選択不可
  - **時間整合性チェック:** 予約の「開始時間」が「終了時間」より後になっている場合はエラー表示
  - **重複予約防止:** 同一会議室で既存の予約時間帯と重なる申請はエラー表示し、DBの整合性を保護

## 使用技術
- **言語 / FW:** Java 21, Spring Boot 3.1.1, Spring Security, Thymeleaf
- **データベース:** PostgreSQL (RDS)
- **インフラ / クラウド:** AWS (EC2, RDS)
- **ビルドツール:** Maven

## システム構成図 (AWS)

```mermaid
graph TD
    Client[クライアント / ブラウザ] -->|HTTP / Port:8080| EC2[AWS EC2 <br/> Spring Boot Web App]
    EC2 -->|PostgreSQL / Port:5432| RDS[(AWS RDS <br/> PostgreSQL)]
    
    subgraph VPC[VPC Security Group]
        EC2
        RDS
    end
```

## データベース設計 (ER図)
```mermaid
erDiagram
    mt_user ||--o{ reservation : "予約を作成"
    mt_room ||--o{ reservable_room : "予約可能日を設定"
    reservable_room ||--o{ reservation : "対象日に予約"

    mt_room {
        serial room_id PK "会議室ID"
        varchar room_name "会議室名"
    }

    reservable_room {
        date reserved_date PK "予約日"
        integer room_id PK,FK "会議室ID"
    }

    mt_user {
        varchar user_id PK "ユーザーID"
        varchar user_name "ユーザー名"
        varchar password "パスワード"
        varchar role_name "役割"
    }

    reservation {
        serial reservation_id PK "予約ID"
        time start_time "開始時間"
        time end_time "終了時間"
        date reserved_date FK "予約日"
        integer room_id FK "会議室ID"
        varchar user_id FK "ユーザーID"
    }
```
## インフラ・開発の工夫
- セキュリティ構成: EC2（アプリ層）とRDS（DB層）を分離し、RDSへはEC2からのセキュリティグループ経由のみアクセスを許可する安全なネットワーク構成を構築。
- データ保護: アプリケーション側での時間重複チェックおよびDB制御により、不正なデータの登録を未然に防ぐ設計。
