# 🤰 MomTrack — Aplikasi Pendamping & Edukasi Ibu Hamil (Android)

Repositori ini berisi kode program dan dokumentasi proyek pengembangan aplikasi mobile **MomTrack** berbasis Android yang dirancang untuk mendampingi masa kehamilan melalui layanan edukasi kesehatan dan sistem pengingat jadwal harian.

Aplikasi ini menggabungkan pusat informasi kehamilan yang terstruktur dengan fitur manajemen pengingat otomatis (*reminder*) berbasis notifikasi perangkat.

---

## 🎯 Fitur Utama

* **Pusat Edukasi Kesehatan (Home):** Menyajikan artikel informatif beserta ilustrasi visual seputar nutrisi seimbang, manajemen berat badan, kualitas tidur, pencegahan stres, pentingnya vaksinasi, perawatan kulit, hingga persiapan persalinan.
* **Halaman Detail Artikel:** Menampilkan konten bacaan kesehatan ibu hamil secara lengkap dan nyaman dibaca di layar perangkat mobile.
* **Pengingat Jadwal Otomatis (Reminder):** Memungkinkan pengguna membuat, mengedit, dan menghapus jadwal pengingat (seperti konsumsi vitamin atau kontrol dokter) dengan pemicu notifikasi *real-time*.
* **Profil & Manajemen Aktivitas (Profile):** Menampilkan ringkasan profil pengguna beserta daftar jadwal pengingat yang sedang aktif.

---

## 🛠️ Spesifikasi Teknologi (Tech Stack)

* **Bahasa Pemrograman:** Kotlin
* **Platform:** Android SDK
* **Antarmuka Pengguna (UI):** XML Layout, Fragment, Bottom Navigation, RecyclerView, CardView
* **Layanan Sistem:** `AlarmManager` & `BroadcastReceiver` untuk penjadwalan notifikasi latar belakang
* **Build System:** Gradle (Kotlin DSL)

---

## ⚙️ Alur Kerja Sistem

1. **Eksplorasi Informasi:** Pengguna membuka halaman utama (*Home*) untuk memilih dan membaca artikel edukasi kehamilan sesuai kebutuhan.
2. **Penjadwalan Pengingat:** Pengguna menambahkan jadwal aktivitas atau pengingat melalui fitur *Reminder* dan menentukan waktu notifikasi.
3. **Background Trigger:** Sistem menjadwalkan waktu pengingat menggunakan `AlarmManager` yang berjalan di latar belakang.
4. **Push Notification:** Saat waktu yang ditentukan tiba, `BroadcastReceiver` memicu munculnya notifikasi pengingat pada perangkat Android pengguna.

---

## 👨‍💻 Pengembang

**Faza Chansa Maulana, S.Kom**  
Lulusan S1 Teknik Informatika — Universitas Muria Kudus  
📧 Email: [fazachansa123@gmail.com](mailto:fazachansa123@gmail.com)
