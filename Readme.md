# 🚀 Project Tallér
dataaa[Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=codemosaic_Taller&metric=alert_status)](https://sonarcloud.io/summary/new_code?id=codemosaic_Taller)

This project uses [SonarCloud](https://sonarcloud.io) for continuous code quality inspection.  
It automatically analyzes code for bugs, vulnerabilities, and code smells on every push and pull request.

## 🧪 Quality Reports
- View full analysis: [SonarCloud Dashboard](https://sonarcloud.io/project/overview?id=codemosaic_Taller)


## 🗺️ Taller Roadmap from 2025.12.16
This document outlines the development roadmap for the Taller backend service. The focus is on scaling infrastructure, expanding the domain model to support complex financial assets, and enhancing statistical capabilities.

### 🚀 Phase 1: Infrastructure & Data Persistence
Goal: Migrate from the current data storage (e.g., H2/In-Memory) to a robust production-grade database.

🐘PostgreSQL Migration data
- Add PostgreSQL driver dependency (e.g., org.postgresql:postgresql).
- Configure application.properties / application.yml for Postgres connection.
- Set up Docker Compose to spin up a local Postgres instance for development.
- Database Version Control
- Integrate Flyway or Liquibase for schema migrations.
- Create initial migration script (V1__init_schema.sql) to reflect the current state.

### 💰 Phase 2: Domain Expansion (Assets & Currencies)
Goal: accurate modeling of a diverse financial portfolio beyond simple cash accounts.

- Create a scheduled job (e.g., @Scheduled) to fetch daily rates from an external API (e.g., Exchangerate.host or Open Exchange Rates).

- Support Stock Positions on Stock accounts

- Implement current value calculation based on live/last-known market price. (refinement needed)


### 📊 Phase 3: Analytics & Reporting
Goal: Turn raw data into actionable insights through complex queries and aggregation.

- Statistical Views (refinement needed)

- Implement endpoints for Net Worth History (snapshotting total value over time).

- Portfolio Distribution: Endpoint to calculate % allocation by Asset Class (Crypto vs. Stocks vs. Cash).

 Complex Queries:
- Add filters for "Value in [Target Currency]" (converting all assets to a single base currency dynamically).
- Create "Year-to-Date" (YTD) and "Month-over-Month" (MoM) growth queries.

## 💡 Future Concepts & Ideas
### 🛠 Technical
API Documentation: Integrate Swagger/OpenAPI (springdoc-openapi) to auto-generate UI for the frontend team.

Import/Export Engine: Allow CSV upload for bank statements (parsers for OTP, Revolut, Wise).

### 📈 Functional
Budgeting & Goals: Set monthly spending limits or savings goals (e.g., "Save 1M HUF by Dec").

Multi-Tenancy: Allow multiple users to host their data on a single instance (Family accounts, currently user based, but only 1 supported)

Price Alerts: Notification system (Email/Telegram bot/push notification) when a Stock or Currency hits a certain price