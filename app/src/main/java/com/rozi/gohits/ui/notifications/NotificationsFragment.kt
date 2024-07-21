package com.rozi.gohits.ui.notifications

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.rozi.gohits.ApiService
import com.rozi.gohits.UploadResponse
import com.rozi.gohits.databinding.FragmentNotificationsBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class NotificationsFragment : Fragment() {
    private var _binding: FragmentNotificationsBinding? = null
    private val binding get() = _binding!!
    private var selectedImageUri: Uri? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val items = listOf("SELECT CATEGORY", "Badminton", "FootBall", "Pimpong", "Running", "Esport")
        val par = listOf("SELECT PARTICIPANT", "8")

        val categoryAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, items)
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.category.adapter = categoryAdapter

        val participantAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, par)
        participantAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.participant.adapter = participantAdapter

        binding.date.setOnClickListener { showDatePickerDialog() }
        binding.time.setOnClickListener { showTimePickerDialog() }
        binding.buttonUploadImage.setOnClickListener { selectImage() }
        binding.save.setOnClickListener { uploadData() }
        val userSession = getUserSession()
        if (userSession != null) {
            val (userId, usernama) = userSession
            binding.textView4.text = usernama
        } else {
            Toast.makeText(context, "User session not found", Toast.LENGTH_SHORT).show()
        }

        return root
    }

    private fun uploadData() {
        val title = binding.title.text.toString().trim()
        val date = binding.date.text.toString().trim()
        val time = binding.time.text.toString().trim()
        val price = binding.price.text.toString().trim()
        val organizer = binding.organizer.text.toString().trim()
        val location = binding.location.text.toString().trim()
        val category = binding.category.selectedItem.toString().trim()
        val participant = binding.participant.selectedItem.toString().trim()

        // Cek apakah ada field yang kosong
        if (title.isEmpty()) {
            Toast.makeText(context, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (date.isEmpty()) {
            Toast.makeText(context, "Date cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (time.isEmpty()) {
            Toast.makeText(context, "Time cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (price.isEmpty()) {
            Toast.makeText(context, "Price cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (organizer.isEmpty()) {
            Toast.makeText(context, "Organizer cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (location.isEmpty()) {
            Toast.makeText(context, "Location cannot be empty", Toast.LENGTH_SHORT).show()
            return
        }
        if (category == "SELECT CATEGORY") {
            Toast.makeText(context, "Please select a category", Toast.LENGTH_SHORT).show()
            return
        }
        if (participant == "SELECT PARTICIPANT") {
            Toast.makeText(context, "Please select a participant", Toast.LENGTH_SHORT).show()
            return
        }
        if (selectedImageUri == null) {
            Toast.makeText(context, "Image is not selected", Toast.LENGTH_SHORT).show()
            return
        }

        val userSession = getUserSession()
        if (userSession != null) {
            val (userId, _) = userSession

            val idUser = RequestBody.create("text/plain".toMediaTypeOrNull(), userId)
            val titleBody = RequestBody.create("text/plain".toMediaTypeOrNull(), title)
            val dateBody = RequestBody.create("text/plain".toMediaTypeOrNull(), date)
            val timeBody = RequestBody.create("text/plain".toMediaTypeOrNull(), time)
            val priceBody = RequestBody.create("text/plain".toMediaTypeOrNull(), price)
            val organizerBody = RequestBody.create("text/plain".toMediaTypeOrNull(), organizer)
            val locationBody = RequestBody.create("text/plain".toMediaTypeOrNull(), location)
            val categoryBody = RequestBody.create("text/plain".toMediaTypeOrNull(), category)
            val participantBody = RequestBody.create("text/plain".toMediaTypeOrNull(), participant)

            val filePath = getRealPathFromURI(selectedImageUri!!)
            if (filePath == null) {
                Toast.makeText(context, "Failed to get image path", Toast.LENGTH_SHORT).show()
                return
            }

            val file = File(filePath)
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), file)
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val apiService = ApiClient.getClient(requireContext()).create(ApiService::class.java)
            val call = apiService.uploadImage(body, idUser, titleBody, dateBody, timeBody, priceBody, organizerBody, locationBody, categoryBody, participantBody)
            if (call != null) {
                call.enqueue(object : Callback<UploadResponse> {
                    override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Upload successful", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(context, "Upload failed: ${response.message()}", Toast.LENGTH_SHORT).show()
                            Log.d("NotificationsFragment", "Response: $response")
                        }
                    }

                    override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                        Toast.makeText(context, "lah: ${t.message}", Toast.LENGTH_SHORT).show()
                    }
                })
            }
        } else {
            Toast.makeText(context, "User session not found", Toast.LENGTH_SHORT).show()
        }
    }


    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            requireContext(),
            { _, year, monthOfYear, dayOfMonth ->
                val selectedDate = Calendar.getInstance()
                selectedDate.set(year, monthOfYear, dayOfMonth)
                val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
                binding.date.setText(dateFormat.format(selectedDate.time))
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        datePickerDialog.show()
    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val timePickerDialog = TimePickerDialog(
            requireContext(),
            { _, hourOfDay, minute ->
                val selectedTime = Calendar.getInstance()
                selectedTime.set(Calendar.HOUR_OF_DAY, hourOfDay)
                selectedTime.set(Calendar.MINUTE, minute)
                val timeFormat = SimpleDateFormat("HH:mm", Locale.US)
                binding.time.setText(timeFormat.format(selectedTime.time))
            },
            calendar.get(Calendar.HOUR_OF_DAY),
            calendar.get(Calendar.MINUTE),
            true
        )
        timePickerDialog.show()
    }

    private fun selectImage() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        imagePickerLauncher.launch(intent)
    }

    private val imagePickerLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK && result.data != null) {
            selectedImageUri = result.data?.data
            selectedImageUri?.let {
                binding.imageView.setImageURI(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getRealPathFromURI(uri: Uri): String? {
        var filePath: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = requireActivity().contentResolver.query(uri, projection, null, null, null)
        if (cursor != null) {
            cursor.moveToFirst()
            val columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
            filePath = cursor.getString(columnIndex)
            cursor.close()
        }
        return filePath
    }

    private fun getUserSession(): Pair<String, String>? {
        val sharedPreferences = activity?.getSharedPreferences("MyAppPreferences", Context.MODE_PRIVATE)
        val userId = sharedPreferences?.getString("userId", null)
        val usernama = sharedPreferences?.getString("usernama", null)
        return if (userId != null && usernama != null) {
            Pair(userId, usernama)
        } else {
            null
        }
    }

    companion object {
        private const val PERMISSION_REQUEST_CODE = 100
    }

}
