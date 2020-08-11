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
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.mjslick.R
import com.mjslick.model.LadiesWear
import com.mjslick.ui.factory.AddFemaleClothFactory
import com.mjslick.utility.Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
import com.mjslick.utility.Constants.OPEN_MEDIA_PICKER
import com.mjslick.utility.Network.isOnline
import kotlinx.android.synthetic.main.fragment_add_female_cloths.view.*
import java.io.ByteArrayOutputStream
import java.util.*

class AddFemaleClothsFragment : Fragment() {


    private lateinit var selectedImageBytes: ByteArray

    private lateinit var viewModel: AddFemaleClothViewModel

    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_add_female_cloths, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddFemaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddFemaleClothViewModel::class.java)


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

                                when(i){
                                   0 -> {
                                      loadImage(selectedImageBytes, root.shirtImage1)
                                      root.shirtImage1.visibility = View.VISIBLE
                                  }
                                  1 -> {
                                      loadImage(selectedImageBytes, root.shirtImage2)
                                      root.shirtImage2.visibility = View.VISIBLE
                                  }
                                  2 -> {
                                      loadImage(selectedImageBytes, root.shirtImage3)
                                      root.shirtImage3.visibility = View.VISIBLE
                                  }
                                  3 -> {
                                      loadImage(selectedImageBytes, root.shirtImage4)
                                      root.shirtImage4.visibility = View.VISIBLE
                                  }  else -> {
                                    Toast.makeText(requireContext(), "Can't select more than 4 item, try again",
                                        Toast.LENGTH_LONG).show()
                                    clearImage()
                                }
                                }
                        val clothsImages = listOf(selectedImageBytes)
                        if (root.shirtImage1 != null) {
                            viewModel.uploadFemaleClothImage(clothsImages){imagePath ->
                                addFemaleCloth(imagePath)

                                Log.d("clothes", imagePath.toString())
                            }
                        }else {
                            Toast.makeText(requireContext(), "Please select Image",
                                Toast.LENGTH_SHORT).show()
                        }
                        }

                }else if (data.data != null){
                    val imageLink = data.data
                    val singleImageBmp = MediaStore.Images.Media
                        .getBitmap(activity?.contentResolver, imageLink)

                    val singleOutputStream = ByteArrayOutputStream()
                    singleImageBmp.compress(Bitmap.CompressFormat.JPEG, 90, singleOutputStream)
                    selectedImageBytes = singleOutputStream.toByteArray()
                    loadImage(selectedImageBytes, root.shirtImage1)
                    root.shirtImage1.visibility = View.VISIBLE

                    val clothsImages = listOf(selectedImageBytes)
                    if (root.shirtImage1 != null) {
                        viewModel.uploadFemaleClothImage(clothsImages){imagePath ->
                            addFemaleCloth(imagePath)

                            Log.d("clothes", imagePath.toString())
                        }
                    }else {
                        Toast.makeText(requireContext(), "Please select Image",
                            Toast.LENGTH_SHORT).show()
                    }

                }
            }
        }
    }

   private fun addFemaleCloth(imagePath: ArrayList<String>) {
       Log.d("clothImagePaths", imagePath.toString())

       root.saveFemaleCloths.setOnClickListener {
           if (isOnline(requireContext())){
           val clothName = Objects.requireNonNull(root.female_cloth_name.text.toString())
           val clothType = root.select_female_cloth.selectedItem.toString()
           val clothDescription = root.female_cloth_description.text.toString()
           val topPrice = root.female_top_price.text.toString()
           val trouserPrice = root.female_trouser_price.text.toString()
           val completePrice = root.female_complete_price.text.toString()

           val femaleWear = LadiesWear(
               clothName, clothType, clothDescription,
               getString(R.string.currency_naira) + topPrice,
               getString(R.string.currency_naira) + trouserPrice,
               getString(R.string.currency_naira) + completePrice, imagePath
           )

           if (clothName.isEmpty() || clothDescription.isEmpty()) {
               Toast.makeText(
                   requireContext(), "Name and description can not be empty",
                   Toast.LENGTH_SHORT
               ).show()
           } else {
               viewModel.addFemaleCloth(femaleWear)
               Toast.makeText(
                   requireContext(), "cloths added successfully",
                   Toast.LENGTH_SHORT
               ).show()
               Navigation.findNavController(root).navigate(R.id.navigation_female)
           }
       }else{
               Toast.makeText(requireContext(), "check Network", Toast.LENGTH_SHORT).show()
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
        root.shirtImage1.setImageResource(android.R.color.transparent)
        root.shirtImage2.setImageResource(android.R.color.transparent)
        root.shirtImage3.setImageResource(android.R.color.transparent)
        root.shirtImage4.setImageResource(android.R.color.transparent)
        root.shirtImage1.visibility = View.GONE
        root.shirtImage2.visibility = View.GONE
        root.shirtImage3.visibility = View.GONE
        root.shirtImage4.visibility = View.GONE
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