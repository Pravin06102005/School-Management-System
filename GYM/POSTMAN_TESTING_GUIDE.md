# Gym Management System - Postman API Testing Guide

## Base URL
```
http://localhost:8080/gymapp
```

## 1. Trainer Signup Process

### Step 1: Create a User Account for Trainer
**POST** `/api/users`
```json
{
    "fullName": "John Trainer",
    "email": "john.trainer@example.com",
    "password": "password123",
    "phone": "1234567890",
    "role": "TRAINER"
}
```

### Step 2: Signup as Trainer
**POST** `/api/trainers/signup`
```json
{
    "specialization": "Weight Training",
    "experience": 5,
    "salary": 50000.00,
    "user": {
        "fullName": "John Trainer",
        "email": "john.trainer@example.com",
        "password": "password123",
        "phone": "1234567890",
        "role": "TRAINER"
    }
}
```

**Expected Response:**
- Status: 201 Created
- The trainer will have `approvalStatus: "PENDING"`
- The user will have `enabled: false`

## 2. Admin Operations

### Step 3: Get Pending Trainer Applications (Admin)
**GET** `/api/admin/trainers/pending`

**Expected Response:**
- List of trainers with `approvalStatus: "PENDING"`

### Step 4: Approve Trainer (Admin)
**PUT** `/api/admin/trainers/{trainerId}/approve`

Replace `{trainerId}` with the actual trainer ID from Step 2.

**Expected Response:**
- Status: 200 OK
- `approvalStatus: "APPROVED"`
- `approvalDate: "2024-01-XX"`
- User `enabled: true`

### Step 5: Reject Trainer (Alternative Admin Action)
**PUT** `/api/admin/trainers/{trainerId}/reject`

**Expected Response:**
- Status: 200 OK
- `approvalStatus: "REJECTED"`
- `approvalDate: "2024-01-XX"`
- User `enabled: false`

## 3. Authentication Testing

### Step 6: Test Login Before Approval
**POST** `/api/auth/login`
```json
{
    "email": "john.trainer@example.com",
    "password": "password123"
}
```

**Expected Response (Before Approval):**
- Status: 400 Bad Request
- `success: false`
- `message: "Your trainer application is pending admin approval"`

### Step 7: Test Login After Approval
**POST** `/api/auth/login`
```json
{
    "email": "john.trainer@example.com",
    "password": "password123"
}
```

**Expected Response (After Approval):**
- Status: 200 OK
- `success: true`
- `message: "Login successful"`
- `role: "TRAINER"`
- `userId: [user_id]`

### Step 8: Test Login After Rejection
**POST** `/api/auth/login`
```json
{
    "email": "john.trainer@example.com",
    "password": "password123"
}
```

**Expected Response (After Rejection):**
- Status: 400 Bad Request
- `success: false`
- `message: "Your trainer application has been rejected"`

## 4. Member Signup (For Comparison)

### Step 9: Create Member Account
**POST** `/api/users`
```json
{
    "fullName": "Jane Member",
    "email": "jane.member@example.com",
    "password": "password123",
    "phone": "0987654321",
    "role": "MEMBER"
}
```

### Step 10: Member Login
**POST** `/api/auth/login`
```json
{
    "email": "jane.member@example.com",
    "password": "password123"
}
```

**Expected Response:**
- Status: 400 Bad Request (if user not enabled)
- `message: "Account is not activated"`

## 5. Additional Admin Endpoints

### Get All Trainers
**GET** `/api/admin/trainers`

### Get All Users
**GET** `/api/users`

### Get User by Email
**GET** `/api/users/email/{email}`

## Testing Scenarios Summary

1. **Trainer Signup Flow:**
   - Create user → Signup trainer → Check pending status → Admin approves → Login successful

2. **Trainer Rejection Flow:**
   - Create user → Signup trainer → Check pending status → Admin rejects → Login fails

3. **Member Flow:**
   - Create member → Login (will fail until enabled by admin)

## Important Notes

- All trainer accounts start with `enabled: false` and `approvalStatus: "PENDING"`
- Only admins can approve/reject trainers
- Trainers cannot login until approved by admin
- Members also need to be enabled (separate process)
- The system uses simple password comparison (not encrypted in this example)

## Error Handling

The system provides clear error messages for:
- Pending approval status
- Rejected applications
- Invalid credentials
- Account not activated
- User not found
