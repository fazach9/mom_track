package com.example.momtrack

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class HomeFragment : Fragment() {
    private lateinit var rvInfo: RecyclerView
    private val listInformasi = ArrayList<Informasi>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rvInfo = view.findViewById(R.id.rvInformasi)
        rvInfo.layoutManager = LinearLayoutManager(requireContext())
        rvInfo.setHasFixedSize(true)

        listInformasi.addAll(getDummyInfo())
        rvInfo.adapter = InformasiAdapter(listInformasi) { info ->
            val intent = Intent(requireContext(), DetailActivity::class.java)
            intent.putExtra("JUDUL", info.judul)
            intent.putExtra("DESKRIPSI", getFullDescription(info.judul))
            intent.putExtra("GAMBAR", info.gambar)
            startActivity(intent)
        }

        return view
    }

    private fun getDummyInfo(): List<Informasi> {
        return listOf(
            Informasi(
                R.drawable.tips_tidur,
                "Tips Tidur Nyaman",
                "Gunakan bantal tambahan di antara lutut atau di bawah perut untuk tidur lebih nyaman."
            ),
            Informasi(
                R.drawable.makanan_sehat,
                "Makanan Sehat",
                "Konsumsi makanan kaya asam folat seperti sayuran hijau untuk mendukung perkembangan bayi."
            ),
            Informasi(
                R.drawable.senin_sehat,
                "Senam Sehat",
                "Senam ringan dapat membantu mengurangi nyeri punggung dan meningkatkan stamina."
            ),
            Informasi(
                R.drawable.makanan_sehat,
                "Nutrisi Seimbang",
                "Konsumsi makanan kaya protein, zat besi, kalsium, dan vitamin D untuk kesehatan ibu dan bayi."
            ),
            Informasi(
                R.drawable.tips_tidur,
                "Tidur yang Cukup",
                "Tidur 7-9 jam per malam dan istirahat siang jika perlu untuk mendukung perkembangan bayi."
            ),
            Informasi(
                R.drawable.hindari_stres,
                "Hindari Stres",
                "Praktikkan meditasi atau pernapasan dalam untuk menjaga kesehatan mental selama kehamilan."
            ),
            Informasi(
                R.drawable.pentingnya_vaksinasi,
                "Pentingnya Vaksinasi",
                "Vaksin influenza dan Tdap dianjurkan untuk melindungi ibu dan bayi dari penyakit."
            ),
            Informasi(
                R.drawable.berat_badan,
                "Pantau Berat Badan",
                "Kenaikan berat badan sehat (11-16 kg untuk BMI normal) mendukung kehamilan yang sehat."
            ),
            Informasi(
                R.drawable.zat_berbahaya,
                "Hindari Zat Berbahaya",
                "Jauhi alkohol, rokok, dan kafein berlebih untuk mencegah risiko pada bayi."
            ),
            Informasi(
                R.drawable.persiapan_persalinan,
                "Persiapan Persalinan",
                "Ikuti kelas persiapan kelahiran untuk mempelajari teknik pernapasan dan perawatan bayi."
            ),
            Informasi(
                R.drawable.perawatan_kulit,
                "Perawatan Kulit",
                "Gunakan pelembap aman untuk mencegah stretch mark dan hindari bahan kimia keras."
            )
        )
    }

    private fun getFullDescription(judul: String): String {
        return when (judul) {
            "Tips Tidur Nyaman" -> """
                Ibu hamil sering mengalami gangguan tidur akibat perubahan hormon, ukuran perut, atau kecemasan. Tidur cukup sangat penting untuk kesehatan janin dan ibu.
                • Posisi Tidur:
                  - Miring ke kiri: meningkatkan aliran darah ke plasenta (berdasarkan studi American Pregnancy Association).
                  - Hindari telentang: dapat menekan vena cava dan mengurangi sirkulasi.
                • Bantal Penyangga:
                  - Gunakan bantal berbentuk "U" atau "C" untuk menyangga perut, punggung, dan lutut.
                • Lingkungan Tidur:
                  - Suhu ideal: 18–22°C. Gunakan tirai gelap dan white noise (jika perlu).
            """.trimIndent()
            "Makanan Sehat" -> """
                Nutrisi selama hamil memengaruhi perkembangan otak janin, berat badan lahir, dan kesehatan ibu.
                • Zat Gizi Penting:
                  - Asam folat: 400-800 mcg/hari (bayam, jeruk) untuk mencegah cacat tabung saraf.
                  - Zat besi: 27 mg/hari (daging merah, kacang lentil) untuk mencegah anemia.
                  - Kalsium: 1000mg/hari (susu, brokoli) untuk tulang janin.
                • Pantangan:
                  - Makanan mentah (sushi, telur setengah matang) resiko bakteri.
                  - Batasi ikan tinggi merkuri (tuna, makarel).
                • Contoh Menu Harian:
                  - Sarapan: Oatmeal + pisang + susu.
                  - Makan siang: Nasi merah + ikan salmon + tumis bayam.
                  - Camilan: Yoghurt + kacang almond.
            """.trimIndent()
            "Senam Sehat" -> """
                Olahraga ringan mengurangi nyeri punggung, meningkatkan mood, dan mempersiapkan persalinan.
                • Jenis Senam:
                  - Yoga prenatal: Posisi "Cat-Cow" untuk fleksibilitas punggung.
                  - Kegel: Kontraksi otot panggul (tahan 5 detik, ulangi 10x) untuk cegah inkontinensia.
                  - Jalan kaki: 30 menit/hari dengan sepatu nyaman.
                • Hindari:
                  - Gerakan melompat atau berbaring telentang >5 menit (resiko pusing).
                • Contoh Jadwal:
                  - Senin: Yoga 20 menit.
                  - Rabu: Jalan pagi 30 menit.
                  - Jumat: Senam kegel + peregangan.
            """.trimIndent()
            "Nutrisi Seimbang" -> """
                Kebutuhan nutrisi meningkat setiap trimester. Trimester 1 fokus asam folat, trimester 3 fokus protein dan zat besi.
                • Porsi per Trimester:
                  - Trimester 1: Tambahkan asam folat + vitamin B6 (atasi mual).
                  - Trimester 2: Protein + omega-3 (perkembangan otak janin).
                  - Trimester 3: Zat besi + serat (cegah anemia dan sembelit).
                • Contoh Kombinasi:
                  - Sarapan: Roti gandum + alpukat + telur rebus.
                  - Makan malam: Daging tanpa lemak + quinoa + brokoli kukus.
            """.trimIndent()
            "Tidur yang Cukup" -> """
                Kurang tidur pada ibu hamil terkait dengan resiko persalinan prematur dan tekanan darah tinggi.
                • Manfaat Tidur Cukup:
                  - Trimester 1: Mengurangi morning sickness.
                  - Trimester 3: Mengurangi pembengkakan kaki.
                • Contoh Power Nap:
                  - 20 menit setelah makan siang (gunakan bantal leher).
            """.trimIndent()
            "Hindari Stres" -> """
                Stres berlebihan dapat memengaruhi kesehatan ibu dan bayi, termasuk risiko tekanan darah tinggi atau kelahiran prematur.
                • Teknik Relaksasi:
                  - Meditasi: Luangkan 10-15 menit per hari untuk meditasi mindfulness.
                  - Pernapasan dalam: Tarik napas dalam selama 4 detik, tahan 4 detik, keluarkan 6 detik.
                  - Jurnal harian: Tulis pikiran atau rasa syukur untuk mengurangi kecemasan.
                • Dukungan Sosial:
                  - Bergabung dengan kelompok ibu hamil untuk berbagi pengalaman.
                  - Diskusikan kekhawatiran dengan pasangan atau keluarga.
                • Hindari:
                  - Overthinking: Batasi paparan berita atau media sosial yang memicu stres.
                  - Jadwal berlebihan: Prioritaskan waktu untuk istirahat dan relaksasi.
            """.trimIndent()
            "Pentingnya Vaksinasi" -> """
                Vaksinasi selama kehamilan melindungi ibu dan bayi dari penyakit serius, seperti influenza dan pertusis.
                • Vaksin yang Dianjurkan:
                  - Vaksin influenza: Aman untuk semua trimester, mengurangi risiko flu berat.
                  - Vaksin Tdap: Diberikan pada trimester kedua atau ketiga untuk mencegah pertusis pada bayi.
                • Manfaat:
                  - Meningkatkan imunitas ibu dan mentransfer antibodi ke bayi.
                  - Mengurangi risiko komplikasi selama kehamilan.
                • Tips:
                  - Konsultasikan jadwal vaksinasi dengan dokter atau bidan.
                  - Pastikan vaksin diberikan di fasilitas kesehatan terpercaya.
            """.trimIndent()
            "Pantau Berat Badan" -> """
                Kenaikan berat badan yang sehat (11-16 kg untuk BMI normal) membantu mencegah komplikasi seperti diabetes gestasional.
                • Panduan Kenaikan Berat Badan:
                  - BMI normal (18.5-24.9): 11-16 kg selama kehamilan.
                  - BMI rendah (<18.5): 12.5-18 kg untuk mendukung pertumbuhan bayi.
                  - BMI tinggi (>25): 7-11.5 kg untuk mengurangi risiko komplikasi.
                • Tips Pemantauan:
                  - Timbang berat badan setiap bulan di dokter atau klinik.
                  - Konsumsi makanan bergizi seperti sayuran, protein tanpa lemak, dan biji-bijian.
                • Hindari:
                  - Kalori kosong: Batasi makanan manis atau gorengan.
                  - Diet ketat: Jangan membatasi asupan kalori tanpa saran dokter.
            """.trimIndent()
            "Hindari Zat Berbahaya" -> """
                Alkohol, rokok, dan kafein berlebih dapat menyebabkan cacat lahir, kelahiran prematur, atau gangguan perkembangan bayi.
                • Zat yang Harus Dihindari:
                  - Alkohol: Tidak ada jumlah aman selama kehamilan; risiko sindrom alkohol janin.
                  - Rokok: Berhenti merokok dan hindari paparan asap rokok.
                  - Kafein: Batasi hingga 200 mg/hari (1 cangkir kopi).
                • Tips Keamanan:
                  - Konsultasikan semua obat (termasuk obat bebas) dengan dokter.
                  - Gunakan produk pembersih atau kosmetik bebas bahan kimia berbahaya.
                • Manfaat:
                  - Mengurangi risiko cacat lahir dan komplikasi kehamilan.
                  - Mendukung perkembangan janin yang sehat.
            """.trimIndent()
            "Persiapan Persalinan" -> """
                Persiapan persalinan membantu ibu merasa lebih percaya diri dan siap menghadapi kelahiran.
                • Langkah Persiapan:
                  - Ikuti kelas persiapan kelahiran untuk mempelajari teknik pernapasan dan posisi melahirkan.
                  - Buat rencana kelahiran: Tentukan preferensi seperti metode persalinan atau kehadiran pendamping.
                  - Siapkan tas rumah sakit: Sertakan pakaian bayi, dokumen, dan kebutuhan pribadi.
                • Tips Praktis:
                  - Diskusikan rencana kelahiran dengan dokter atau bidan.
                  - Latih teknik relaksasi untuk mengurangi kecemasan saat persalinan.
                • Manfaat:
                  - Mengurangi ketakutan dan meningkatkan kesiapan mental.
                  - Membantu proses persalinan lebih lancar.
            """.trimIndent()
            "Perawatan Kulit" -> """
                Kehamilan dapat menyebabkan perubahan kulit seperti stretch mark, kulit kering, atau hiperpigmentasi.
                • Tips Perawatan:
                  - Gunakan pelembap alami: Minyak kelapa atau lotion berbahan dasar aloe vera.
                  - Hidrasi: Minum air 8-10 gelas per hari untuk menjaga elastisitas kulit.
                  - Lindungi kulit: Gunakan tabir surya SPF 30+ untuk mencegah hiperpigmentasi.
                • Bahan yang Harus Dihindari:
                  - Retinoid dan asam salisilat tinggi: Berisiko bagi janin.
                  - Parfum kuat: Dapat menyebabkan iritasi kulit sensitif.
                • Manfaat:
                  - Mengurangi stretch mark dan menjaga kulit tetap sehat.
                  - Meningkatkan rasa percaya diri selama kehamilan.
            """.trimIndent()
            else -> "Informasi tidak tersedia."
        }
    }
}