# MeetingRoomReservationDemo

Spring Boot と PostgreSQL で構築した会議室予約システムです。
AWS (EC2 / RDS) 上にデプロイし、動作確認を完了しています。（※現在はコスト抑制のためAWS環境を停止中）

## 動作デモ

<img width="1376" height="728" alt="Animation" src="https://github.com/user-attachments/assets/3d116d11-4749-4e4a-8558-3e617a4e9ad3" />

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
    erDiagram
    EMPLOYEES ||--o{ RESERVATIONS : "予約を作成"
    MEETING_ROOMS ||--o{ RESERVATIONS : "予約される"

    EMPLOYEES {
        bigint id PK
        varchar name "社員名"
        varchar email "メールアドレス"
        varchar role "権限 (USER / ADMIN)"
    }

    MEETING_ROOMS {
        bigint id PK
        varchar room_name "会議室名"
        int capacity "収容人数"
    }

    RESERVATIONS {
        bigint id PK
        bigint employee_id FK
        bigint meeting_room_id FK
        date reservation_date "予約日"
        time start_time "開始時間"
        time end_time "終了時間"
    }
