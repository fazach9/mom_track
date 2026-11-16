package com.example.momtrack

import android.app.AlertDialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Calendar
import java.util.Locale
import android.app.AlarmManager
import android.provider.Settings

class ProfileFragment : Fragment() {
    private lateinit var rvReminders: RecyclerView
    private lateinit var etReminderTime: EditText
    private lateinit var etReminderDesc: EditText
    private lateinit var btnAddReminder: Button
    private val reminders = mutableListOf<Reminder>()
    private lateinit var reminderAdapter: ReminderAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Inisialisasi UI
        rvReminders = view.findViewById(R.id.rvReminders)
        etReminderTime = view.findViewById(R.id.etReminderTime)
        etReminderDesc = view.findViewById(R.id.etReminderDesc)
        btnAddReminder = view.findViewById(R.id.btnAddReminder)

        // Minta izin notifikasi dan alarm
        requestNotificationPermission()
        requestExactAlarmPermission()

        // Setup RecyclerView
        rvReminders.layoutManager = LinearLayoutManager(requireContext())
        rvReminders.setHasFixedSize(true)
        reminderAdapter = ReminderAdapter(reminders, { position ->
            // Hapus pengingat
            cancelScheduledNotification((reminders[position].time + reminders[position].description).hashCode())
            reminders.removeAt(position)
            saveReminders()
            reminderAdapter.notifyDataSetChanged()
        }, { position ->
            // Edit pengingat
            showEditReminderDialog(position)
        })
        rvReminders.adapter = reminderAdapter

        // Setup TimePickerDialog
        etReminderTime.setOnClickListener {
            showTimePickerDialog()
        }

        // Tombol untuk menambah pengingat
        btnAddReminder.setOnClickListener {
            val time = etReminderTime.text.toString()
            val desc = etReminderDesc.text.toString()
            if (time.isNotEmpty() && desc.isNotEmpty()) {
                Log.d("ProfileFragment", "Adding reminder: time=$time, desc=$desc")
                reminders.add(Reminder(time, desc))
                saveReminders()
                reminderAdapter.notifyDataSetChanged()
                scheduleNotification(time, desc, reminders.size - 1)
                etReminderTime.text.clear()
                etReminderDesc.text.clear()
            } else {
                Log.w("ProfileFragment", "Empty time or description: time=$time, desc=$desc")
                Toast.makeText(requireContext(), "Isi waktu dan deskripsi", Toast.LENGTH_SHORT).show()
            }
        }

        // Muat pengingat yang sudah ada
        loadReminders()

        return view
    }

    private fun requestNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                if (shouldShowRequestPermissionRationale(android.Manifest.permission.POST_NOTIFICATIONS)) {
                    AlertDialog.Builder(requireContext())
                        .setTitle("Izin Notifikasi Diperlukan")
                        .setMessage("Izin ini diperlukan untuk menampilkan pengingat. Izinkan di pengaturan?")
                        .setPositiveButton("Ya") { _, _ ->
                            ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
                        }
                        .setNegativeButton("Tidak", null)
                        .show()
                } else {
                    ActivityCompat.requestPermissions(requireActivity(), arrayOf(android.Manifest.permission.POST_NOTIFICATIONS), 100)
                }
            }
        }
    }

    private fun requestExactAlarmPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            if (!alarmManager.canScheduleExactAlarms()) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Izin Alarm Diperlukan")
                    .setMessage("Izin ini diperlukan untuk menjadwalkan pengingat tepat waktu. Izinkan di pengaturan?")
                    .setPositiveButton("Ya") { _, _ ->
                        val intent = Intent(Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                        startActivity(intent)
                    }
                    .setNegativeButton("Tidak", null)
                    .show()
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(requireContext(), "Izin notifikasi diberikan", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(requireContext(), "Izin notifikasi diperlukan untuk pengingat", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showTimePickerDialog(onTimeSelected: (String) -> Unit = { time -> etReminderTime.setText(time) }) {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        TimePickerDialog(
            requireContext(),
            { _, selectedHour, selectedMinute ->
                val time = String.format(Locale.getDefault(), "%02d:%02d", selectedHour, selectedMinute)
                onTimeSelected(time)
            },
            hour,
            minute,
            true
        ).show()
    }

    private fun showEditReminderDialog(position: Int) {
        val reminder = reminders[position]
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_edit_reminder, null)
        val etEditTime = dialogView.findViewById<EditText>(R.id.etReminderTime)
        val etEditDesc = dialogView.findViewById<EditText>(R.id.etReminderDesc)
        etEditTime.setText(reminder.time)
        etEditDesc.setText(reminder.description)

        etEditTime.setOnClickListener {
            showTimePickerDialog { time ->
                etEditTime.setText(time)
            }
        }

        AlertDialog.Builder(requireContext())
            .setTitle("Edit Pengingat")
            .setView(dialogView)
            .setPositiveButton("Simpan") { _, _ ->
                val newTime = etEditTime.text.toString()
                val newDesc = etEditDesc.text.toString()
                if (newTime.isNotEmpty() && newDesc.isNotEmpty()) {
                    val oldId = (reminder.time + reminder.description).hashCode()
                    cancelScheduledNotification(oldId)
                    reminders[position] = Reminder(newTime, newDesc)
                    saveReminders()
                    reminderAdapter.notifyItemChanged(position)
                    scheduleNotification(newTime, newDesc, position)
                } else {
                    Toast.makeText(requireContext(), "Isi waktu dan deskripsi", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Batal", null)
            .show()
    }

    private fun scheduleNotification(time: String, description: String, notificationId: Int) {
        val calendar = Calendar.getInstance()
        val timeParts = time.split(":")
        try {
            calendar.set(Calendar.HOUR_OF_DAY, timeParts[0].toInt())
            calendar.set(Calendar.MINUTE, timeParts[1].toInt())
            calendar.set(Calendar.SECOND, 0)
        } catch (e: Exception) {
            Log.e("ProfileFragment", "Error parsing time: $time", e)
            Toast.makeText(requireContext(), "Format waktu tidak valid", Toast.LENGTH_SHORT).show()
            return
        }

        if (calendar.timeInMillis < System.currentTimeMillis()) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        val uniqueId = (time + description).hashCode()

        val intent = Intent(requireContext(), NotificationReceiver::class.java).apply {
            putExtra("description", description)
            putExtra("notificationId", uniqueId)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            uniqueId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
                Toast.makeText(requireContext(), "Izin alarm tepat waktu diperlukan", Toast.LENGTH_SHORT).show()
                return
            }
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
            Log.d("ProfileFragment", "Scheduled notification $uniqueId at ${calendar.time}")
        } catch (e: SecurityException) {
            Log.e("ProfileFragment", "Alarm scheduling failed due to permission issue", e)
            Toast.makeText(requireContext(), "Gagal menjadwalkan notifikasi: Izin ditolak", Toast.LENGTH_SHORT).show()
        }
    }

    private fun cancelScheduledNotification(notificationId: Int) {
        val intent = Intent(requireContext(), NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            notificationId,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent?.let {
            val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.cancel(pendingIntent)
            pendingIntent.cancel()
            Log.d("ProfileFragment", "Cancelled notification $notificationId")
        }
    }

    private fun saveReminders() {
        val sharedPreferences = requireContext().getSharedPreferences("MomTrackPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val gson = Gson()
        val json = gson.toJson(reminders)
        editor.putString("reminders", json)
        editor.apply()
    }

    private fun loadReminders() {
        val sharedPreferences = requireContext().getSharedPreferences("MomTrackPrefs", Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPreferences.getString("reminders", null)
        val type = object : TypeToken<List<Reminder>>() {}.type
        val savedReminders: List<Reminder>? = gson.fromJson(json, type)
        if (savedReminders != null) {
            reminders.clear()
            reminders.addAll(savedReminders)
            reminderAdapter.notifyDataSetChanged()
        }
    }

    private fun showNotification(time: String, description: String) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(requireContext(), "Izin notifikasi belum diberikan", Toast.LENGTH_SHORT).show()
            return
        }

        val notificationManager = NotificationManagerCompat.from(requireContext())
        val channelId = "MomTrackReminderChannel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Pengingat MomTrack",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(requireContext(), channelId)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setContentTitle("Pengingat: $time")
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }
}