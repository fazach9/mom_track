# MomTrack 🤰📱

**MomTrack** adalah aplikasi mobile berbasis Android (Kotlin) yang dirancang untuk membantu ibu hamil memantau kesehatan dan mengatur jadwal harian selama masa kehamilan. Aplikasi ini menggabungkan pusat informasi edukatif dengan sistem pengingat otomatis yang praktis.

## ✨ Fitur Utama

* **Pusat Edukasi Kehamilan (Home):** Menyediakan daftar artikel informatif lengkap dengan ilustrasi visual mengenai nutrisi seimbang, manajemen berat badan, kualitas tidur, pencegahan stres, pentingnya vaksinasi, perawatan kulit, hingga persiapan persalinan.
* **Halaman Detail Artikel:** Tampilan bacaan yang rapi dan mudah dipahami untuk setiap topik kesehatan ibu hamil.
* **Pengingat Jadwal Otomatis (Reminder):** Pengguna dapat menambah, mengubah, dan menghapus jadwal pengingat (seperti jadwal minum vitamin atau kontrol dokter) yang terhubung dengan notifikasi perangkat.
* **Profil & Manajemen Aktivitas (Profile):** Menampilkan ringkasan informasi pengguna beserta daftar pengingat yang sedang aktif.

## 🛠️ Teknologi & Komponen

* **Bahasa Pemrograman:** Kotlin
* **Arsitektur & UI:** XML Layout, Fragment, Bottom Navigation, RecyclerView, CardView
* **Sistem Latar Belakang:** `AlarmManager` & `BroadcastReceiver` untuk penjadwalan notifikasi
* **Build System:** Gradle (Kotlin DSL)

## 🚀 Cara Menjalankan Proyek

1. **Clone repositori ini:**
   ```bash
   git clone https://github.com
   ```
2. **Buka di Android Studio:** Pilih **File > Open** dan arahkan ke folder hasil *clone* `MomTrack`.
3. **Sinkronisasi Gradle:** Tunggu hingga Android Studio selesai mengunduh dependensi (`Gradle Sync`).
4. **Jalankan Aplikasi:** Hubungkan perangkat Android atau jalankan Emulator, lalu klik tombol **Run** (`Shift + F10`).
