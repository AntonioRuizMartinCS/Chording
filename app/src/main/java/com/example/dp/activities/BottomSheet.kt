package com.example.dp.activities


import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import com.example.dp.databinding.FragmentBottomSheetBinding
import com.example.dp.objects.Song
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.File
import java.io.FileOutputStream

//https://www.youtube.com/watch?v=RzjCMa4GBD4
class BottomSheet(private val tab:Song) : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentBottomSheetBinding

    //declare the path for the file to share in the global scope
    var path: File? = null



//    https://nphausg.medium.com/android-fileuriexposedexception-how-does-it-happen-with-your-application-b9238a8f0514

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val shareButton = binding.tabShare

        // Initialize path variable
        path = requireContext().filesDir

        shareButton.setOnClickListener {
            val fileName = "${tab.songName}.txt"

            writeToFile(fileName, buildTabSheet().joinToString("\n"))



            val file = File(path, fileName)

            // Check if file exists before proceeding
            if (file.exists()) {
                val apkURI: Uri? = context?.let { it1 ->
                    FileProvider.getUriForFile(
                        it1,
                        requireContext().applicationContext.packageName + ".provider",
                        file
                    )
                }

                // Check if URI is null
                if (apkURI != null) {
                    val intent = Intent(Intent.ACTION_SEND)
                    intent.type = "text/*"
                    intent.putExtra(Intent.EXTRA_STREAM, apkURI)
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                    // Start activity to share
                    startActivity(Intent.createChooser(intent, "Share Text File"))
                } else {
                    // Handle case where URI is null
                    Toast.makeText(requireContext(), "Failed to get file URI", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Handle case where file does not exist
                Toast.makeText(requireContext(), "File does not exist", Toast.LENGTH_SHORT).show()
            }
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBottomSheetBinding.inflate(inflater,container,false)
        return binding.root
    }


    private fun buildTabSheet(): ArrayList<String>{

        val duration = " ${tab.minutes} : ${tab.seconds}"
        val finalTabSheet = arrayListOf<String>()

        finalTabSheet.add(tab.songName)
        finalTabSheet.add(tab.artist)
        finalTabSheet.add(tab.tuning)
        finalTabSheet.add(tab.key)
        finalTabSheet.add(tab.capo)
        finalTabSheet.add(duration)
        finalTabSheet.add(tab.songChords.joinToString(" "))
        finalTabSheet.add(tab.songBody)
        return finalTabSheet

    }

    private fun writeToFile(fileName:String, content:String){


        val writer = FileOutputStream(File(path, fileName))
        writer.write(content.toByteArray())
        writer.close()
        Toast.makeText(context, "successfully saved $fileName", Toast.LENGTH_SHORT).show()


    }





}