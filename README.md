# CampusPulse 📱

CampusPulse is a modern, event-focused Android application designed to keep students connected with campus activities, society events, and real-time updates.

---

## 🌟 Features
- **Event Discovery**: Browse upcoming campus events with detailed schedules, venues, and categories.
- **RSVP & Event Management**: Reserve seats/tickets instantly with real-time confirmation status.
- **Offline Caching**: Built with Room Database to store event and ticket details locally when offline.
- **Background Data Sync**: Automatic synchronization of offline RSVPs once internet connection is restored.
- **REST API Integration**: Syncs live event data and user profiles with a ASP.NET Core backend.

---

## 🛠️ Tech Stack & Architecture
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel), View Binding
- **Database**: Room DB (SQLite)
- **Networking**: Retrofit, Gson Converter
- **Asynchronous Processing**: Kotlin Coroutines, LiveData, WorkManager
- **UI Components**: Material Design 3, RecyclerView, ConstraintLayout

---

## 📁 Repository & Submission Assets
- **Executable APK**: Included as `app-debug.apk` in the root directory and under the [GitHub Releases](../../releases) section.
- **Source Code**: Located in the `/app` directory.

---

## 🚀 How to Run locally
1. Clone the repository:
   ```bash
   git clone [https://github.com/DugStellar/OPSC6312-PART2-CampusPulse.git](https://github.com/DugStellar/OPSC6312-PART2-CampusPulse.git)
