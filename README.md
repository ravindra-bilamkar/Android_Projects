# 🥜 Premium Dry Fruits & Fresh Flours Shopping App

An ultra-fast, modern, and adaptive Android application for purchasing organic dry fruits, nuts, seeds, and freshly ground flours — inspired by leading quick-commerce platforms like Zepto and Blinkit.

---

## 🌟 Key Features

### 🛒 Customer Experience & Shopping
- **10 Min Delivery App Inspired Fast UI**: Pure Dark Mode UI, high-contrast typography, and a sticky bottom cart bar for quick checkout.
- **Rich Product Catalog**: Handpicked Almonds, Cashews, Pistachios, Walnuts, Figs, Prunes, Apricots, Berries, Seeds, and Fresh Flours (Sharbati Wheat, Jowar, Bajra).
- **Weight Variants**: Flexible weight options (`100gm`, `200gm`, `250gm`, `500gm`, `750gm`, `1kg`) with dynamic price scaling.
- **Interactive Product Details**: Sourcing stories, nutritional tables, allergen warnings, customer reviews, and stock alerts (*Only few left!*).

### 🎨 Custom Builders & Gifting
- **Build Your Own Mix**: Interactive custom trail-mix builder allowing users to mix nuts, seeds, and dried fruits with live price calculation.
- **Gift Hamper Builder**: Festive gift box customizer for Diwali, Weddings, and Corporate occasions.
- **Subscribe & Save**: Recurring delivery management for weekly/monthly nut boxes.

### 🌐 Multi-Language & Adaptive Layouts
- **Trilingual Support**: Full localization in **English**, **Hindi (हिंदी)**, and **Kannada (ಕನ್ನಡ)** with instant in-app switching.
- **Phone & Tablet Adaptive**: Built using Material 3 Adaptive layouts, featuring a two-pane list-detail view for tablets and foldables.

### 🔐 Authentication & Onboarding
- **Flexible Auth**: Phone number + OTP login, Google/Apple Sign-In UI, and **Guest Browsing** mode.
- **Editable Profile**: Manage name, email, DOB, saved delivery addresses, and track **Loyalty Rewards**.

### 👑 Admin Inventory Management
- **Exclusive Admin Rights**: Admin mode unlocked for authorized number (`9008882827`).
- **Live Stock Controls**: Add new SKUs and toggle live stock status (`In Stock`, `Low Stock`, `Out of Stock`).

### 💳 Cart, Checkout & Orders
- **Smart Checkout**: Delivery slot picker (Today/Tomorrow), delivery partner tips, and GSTIN Corporate Invoicing.
- **Transparent Billing**: Detailed bill breakdown with item subtotal, 5% GST, handling fee, and automatic free delivery above ₹500.
- **Razorpay Payment Integration**: Integrated payment gateway flow with transaction confirmation.
- **Order Tracking**: Live vertical status timeline (*Placed ➔ Packed ➔ Out for Delivery ➔ Delivered*).

---

## 🛠️ Tech Stack & Architecture

- **Language**: 100% Idiomatic Kotlin
- **UI Framework**: Jetpack Compose with Material 3 (M3) Design
- **Architecture**: Unidirectional Data Flow (UDF) with MVVM & Clean Layered Architecture
- **State Management**: Kotlin Coroutines & `StateFlow`
- **Adaptive Layouts**: `androidx.compose.material3.adaptive` (NavigationSuiteScaffold & ListDetailPaneScaffold)
- **Navigation**: Jetpack Navigation Compose
- **Image Loading**: Coil (`io.coil-kt:coil-compose`)
- **Payment SDK**: Razorpay Checkout (`com.razorpay:checkout`)
- **Permissions**: Jetpack Activity Result Contracts for Location & Notification permissions

---

## 📁 Project Structure

```
com.example.myapplication/
├── data/
│   ├── model/          # Product, Category, ProductVariant data classes
│   └── repository/     # GroceryRepository, AuthRepository, CartRepository, PaymentRepository
├── ui/
│   ├── components/     # ProductItem, LocationSelector, StickyCartBar
│   ├── screens/        # HomeScreen, ProductDetailScreen, CartScreen, LoginScreen, 
│   │                   # ProfileScreen, CustomMixScreen, GiftHamperBuilder, OrderTrackingScreen
│   ├── theme/          # Color.kt, Theme.kt, Type.kt (Dark/Light Mode)
│   └── viewmodel/      # HomeViewModel, CartViewModel, LoginViewModel, PaymentViewModel
└── MainActivity.kt     # App entry point, NavHost & Permission Launcher
```

---

## 🚀 Getting Started

### Prerequisites
- Android Studio Ladybug (2024.2.1) or newer
- Android SDK 37
- JDK 11 or JDK 17
- Minimum Android Version: Android 7.0 (API Level 24)

### Building the App
1. **Clone the Repository**:
   ```bash
   git clone https://github.com/ravindra-bilamkar/Android_Projects.git
   cd Android_Projects
   ```
2. **Open in Android Studio**: Open the project folder in Android Studio and let Gradle sync.
3. **Run the App**: Select an emulator or physical device and click **Run 'app'** (`Shift + F10`).

---

## 🔑 Demo Credentials
- **Customer Login**: Enter any 10-digit mobile number, use OTP `123456`.
- **Admin Login**: Enter mobile number `9008882827`, use OTP `123456` to access inventory management controls.
- **Guest Mode**: Tap "Browse as Guest" on the login screen to explore without logging in.

---

## 📜 License
This project is open-source and available under the [MIT License](LICENSE).
