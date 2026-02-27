# Order Management System

## Tech Stack

- **Frontend**: Next.js 16 + React 19 + Tailwind CSS 4
- **Backend**: Next.js API Routes (App Router)
- **Database**: SQLite (better-sqlite3)
- **Auth**: JWT + bcrypt
- **Service-to-service communication**: gRPC (integrates with an external coupon service)

## Core Features

### User Authentication

- Register / Login (`/api/auth/register`, `/api/auth/login`)
- JWT-based authentication with a 7-day expiration

### Order Management

- Create orders (supports coupon discounts)
- View a user's order list
- Order status management

### Coupon System (via gRPC to an external Java service)

- List available coupon templates
- Claim coupons
- Validate / use / return coupons
- Supports three coupon types: threshold discount, percentage discount, and instant reduction

## Architecture Highlights

- **Full-stack in one**: Next.js for both UI and API
- **Microservice integration**: Order service (Node.js) + Coupon service (Java/gRPC)
- **Database design**: `users` + `orders` tables, with coupon-related fields supported through migrations

## Configuration

| Config Item      | Default Value    | Description                                                |
| ---------------- | ---------------- | ---------------------------------------------------------- |
| Dev port         | 3001             | `npm run dev`                                              |
| gRPC service URL | `localhost:9090` | Environment variable `COUPON_SERVICE_URL`                  |
| JWT secret       | -                | Environment variable `JWT_SECRET` (required in production) |

## Quick Start

```bash
# Install dependencies
npm install

# Start development server
npm run dev

# Build for production
npm run build

# Start production server
npm start
```

## Project Structure

```
src/
├── app/
│   ├── api/
│   │   ├── auth/          # Auth APIs
│   │   ├── coupons/       # Coupon APIs
│   │   └── orders/        # Order APIs
│   └── page.tsx           # Home page
├── components/            # React components
├── grpc/                  # gRPC client
│   ├── client.ts
│   └── protos/
└── lib/                   # Utilities
	├── auth.ts            # Auth helpers
	└── db.ts              # Database connection

docs/coupon-service-java/  # Reference Java coupon service implementation
```
