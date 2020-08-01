package com.mjslick.ui.female

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.github.siyamed.shapeimageview.mask.PorterShapeImageView
import com.mjslick.R
import com.mjslick.utility.Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import com.mjslick.utility.Constants.OPEN_MEDIA_PICKER
import kotlinx.android.synthetic.main.fragment_add_female_cloths.view.*
import java.io.ByteArrayOutputStream

class AddFemaleClothsFragment : Fragment() {

    private lateinit var shirtImage1: PorterShapeImageView
    private lateinit var shirtImage2: PorterShapeImageView
    private lateinit var shirtImage3: PorterShapeImageView
    private lateinit var shirtImage4: PorterShapeImageView
    private lateinit var selectedImageBytes: ByteArray

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_add_female_cloths, container, false)

        shirtImage1 = root.findViewById(R.id.shirtImage1)
        shirtImage2 = root.findViewById(R.id.shirtImage2)
        shirtImage3 = root.findViewById(R.id.shirtImage3)
        shirtImage4 = root.findViewById(R.id.shirtImage4)

        root.femaleSelectPicture.setOnClickListener {
            clearImage()
            if (!permissionIfNeeded()) {
               if (Build.VERSION.SDK_INT < 19) {
                   val intent = Intent()
                   intent.type = "image/*"
                   intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                   intent.action = Intent.ACTION_GET_CONTENT
                   startActivityForResult(
                       Intent.createChooser(intent, "select Picture"),
                       OPEN_MEDIA_PICKER
                   )
               }else {
                   val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                   intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                   intent.addCategory(Intent.CATEGORY_OPENABLE)
                   intent.type = "image/*"
                   startActivityForResult(intent, OPEN_MEDIA_PICKER)
               }
            }
        }
        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OPEN_MEDIA_PICKER) {
            if (resultCode == Activity.RESULT_OK && data != null) {

                if (data.clipData != null) {
                    val count = data.clipData?.itemCount
                    (0 until count!!).forEach { i ->
                            val imageUri: Uri = data.clipData!!.getItemAt(i).uri
                            val selectedImageBmp = MediaStore.Images.Media
                                .getBitmap(activity?.contentResolver, imageUri)

                            val outputStream = ByteArrayOutputStream()
                            selectedImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, outputStream)
                            selectedImageBytes = outputStream.toByteArray()

                            if (i == 0) {
                                loadImage(selectedImageBytes, shirtImage1)
                                shirtImage1.visibility = View.VISIBLE
                            }
                            if (i == 1) {
                                loadImage(selectedImageBytes, shirtImage2)
                                shirtImage2.visibility = View.VISIBLE
                            }
                            if (i == 2) {
                                loadImage(selectedImageBytes, shirtImage3)
                                shirtImage3.visibility = View.VISIBLE
                            }
                            if (i == 3) {
                                loadImage(selectedImageBytes, shirtImage4)
                                shirtImage4.visibility = View.VISIBLE
                            }
                            if (i == 4) {
                            Toast.makeText(requireContext(), "Can't select more than 4 item, try again",
                                Toast.LENGTH_LONG).show()
                           clearImage()
                        }

                    }
                }else if (data.data != null){
                    val imageLink = data.data
                    val singleImageBmp = MediaStore.Images.Media
                        .getBitmap(activity?.contentResolver, imageLink)

                    val singleOutputStream = ByteArrayOutputStream()
                    singleImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, singleOutputStream)
                    selectedImageBytes = singleOutputStream.toByteArray()
                    loadImage(selectedImageBytes, shirtImage1)
                    shirtImage1.visibility = View.VISIBLE
                }
                Log.d("result", shirtImage1.toString() + shirtImage2.toString()
                + shirtImage3.toString() + shirtImage4.toString())
            }
        }
    }

    private fun loadImage(imageByte : ByteArray, imageView: ImageView){
        Glide.with(requireContext())
            .load(imageByte)
            .apply(RequestOptions().signature(ObjectKey("signature string")))
            .into(imageView)
    }

    private fun clearImage(){
        shirtImage1.setImageResource(android.R.color.transparent)
        shirtImage2.setImageResource(android.R.color.transparent)
        shirtImage3.setImageResource(android.R.color.transparent)
        shirtImage4.setImageResource(android.R.color.transparent)
        shirtImage1.visibility = View.GONE
        shirtImage2.visibility = View.GONE
        shirtImage3.visibility = View.GONE
        shirtImage4.visibility = View.GONE
    }

    private fun permissionIfNeeded(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(requireContext(), "accept permission to read device storage",
                        Toast.LENGTH_SHORT).show()
                }

                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE)
                return true
            }
        }
        return false
    }

}