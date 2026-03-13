# CheatBite 🍔

### Master Your Cheat Meals, Don't Let Them Master You.

CheatBite is not another clunky, standard calorie counter. It is a modern, specialized tool designed to help users strategically manage their "cheat meals" while staying on track with their nutritional goals. Featuring a sophisticated, fluid user experience and an AI-driven assistant, CheatBite turns macro management into an interactive experience.

**✨ Say Goodbye to the "Old Preference Hustle"**
Unlike traditional fitness apps that bury settings deep within multi-layered menus, CheatBite keeps the user in flow. All critical information—from profile information and dietary preferences ("Veg vs. Non-Veg") to your unique weekly diet plan—can be updated **directly from the profile dashboard**. This highly interactive, friction-free design ensures your plan adapts to your life instantly, without requiring navigation marathons.

---

## 📸 Visual Showcase

| 🚀 Seamless Onboarding | 🧘 Snack Sensei AI | 👤 Interactive Profile |
| :---: | :---: | :---: |
| <img src="https://github.com/thevedantchourey/CheatBite/blob/main/app/src/main/res/raw/screenshots/Screenshot_20260313_021253.png" width="250"> | <img src="https://github.com/thevedantchourey/CheatBite/blob/main/app/src/main/res/raw/screenshots/Screenshot_20260313_022137.png" width="250"> | <img src="https://github.com/thevedantchourey/CheatBite/blob/main/app/src/main/res/raw/screenshots/Screenshot_20260313_021113.png" width="250"> | <img src="https://github.com/thevedantchourey/CheatBite/blob/main/app/src/main/res/raw/screenshots/Screenshot_20260313_021125.png" width="250"> |
| Passwordless Sign-up | Your AI Food Guide | A Dashboard, Not a Form |

---

## 🔥 Key Features

### 🔐 Passwordless & Secure Onboarding
* **Sign-up with Gmail:** CheatBite provides a standard-compliant, secure onboarding experience by leveraging Firebase Auth. Users skip the friction of creating new passwords, getting into the app faster.

### 👥 Interactive Inline Settings System
* **Dash-Editing:** Dietary types, user avatars, and weekly diet compositions are updated **inline** directly on the profile dashboard, eliminating navigation hassle.
* **Fluid Avatar Pager:** Change your profile look instantly with a sleek, native horizontal pager that provides immediate visual feedback.

### 📅 The Week Diet Planner
* **Strategic Structure:** Design your optimal nutritional foundation for the week. This unique, structured grid allows you to easily visual when and where a "cheat meal" fits into your overall plan.
* **Quick Set Up:** Add or remove daily structures with a single tap, keeping your plan flexible and simple to manage.

### 🧘 Snack Sensei & History
* **AI-Driven Insight:** Stuck on what to eat? "Snack Sensei" is your integrated guide. Ask for ideas, and get back macro-conscious suggestions designed to fit your day.
* **Suggestion History:** All liked suggestions from Snack Sensei are saved to your History. Revisit past ideas, view their detailed nutritional breakdowns (Protein, Carbs, Fats), and reuse them in your weekly plan.

---

## 🎨 Unique UI/UX Philosophy

CheatBite’s design isn't just aesthetic; it's **functional art**.

* **Glassmorphism & Glow:** The app utilizes a sophisticated visual language of blurred glass layers, translucent borders, and soft gradients to create a distinct, modern depth.
* **Fluid Gestures:** From the horizontal avatar pager to the unique angular text rotation (engineered with fixed pivot points), every element is designed to feel tactile and fluid.
* **Micro-Interactions:** The UI is alive with small animations, loading states, and contextual color changes (like the red/green shifts based on diet preference), ensuring the user always knows what is happening.

---

## 🛠 Tech Stack & Architecture

CheatBite is built on the foundation of the modern Android Ecosystem, demonstrating an advanced mastery of architecture, concurrency, and security.

### Android / Kotlin Core
* **Jetpack Compose:** The entire UI is built natively in declarative Compose, showcasing custom complex layouts, graphic layers, and high-performance list management (`LazyColumn`).
* **MVVM Architecture:** Strict separation of concerns is maintained. The ViewModel handles complex business logic, manages application state with `MutableStateFlow`, and maps raw data to UI-friendly formats.
* **Kotlin Coroutines & Flow:** Asynchronous operations, database transactions, and network calls are handled seamlessly off the main thread, ensuring a smooth, butter-like scrolling experience.
* **collectAsStateWithLifecycle:** Advanced lifecycle awareness ensures the UI only consumes resources when it is active.

### Firebase Backend (BaaS)
* **Firebase Authentication:** Powers the passwordless Gmail sign-on.
* **Firebase Firestore:** A scalable, real-time NoSQL database handles user profile data, weekly diet plans, and the entire Snack Sensei history with complex, mapped data objects.

### Finalization & Production-Ready Standards
* **Security (Gitignore):** The repository demonstrates security best practices by rigorously hiding the `google-services.json` config file via `.gitignore`.
* **R8 / ProGuard Optimization:** The release build is fully optimized, utilizing strict `TypeToken`, `model`, `dto`, and `mapper` "keep" rules to shrink the APK size and secure the code while keeping the reflection-based data layer intact.

---

## 🚀 How to Run locally

**Prerequisites:**
1.  Android Studio (Hedgehog or newer)
2.  Your own Firebase Project config (`google-services.json`)

**Steps:**
1.  Clone the repository: `git clone https://github.com/thevedantchourey/cheatbite.git`
2.  Place your `google-services.json` file inside the `app/` folder.
3.  Open the project in Android Studio.
4.  Run the app on your device or emulator.

**Download the APK:**
1.  Navigate to the **Releases** section on GitHub.
2.  Download the latest `release.apk`.
3.  Install on your Android device.
