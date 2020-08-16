package com.mjslick.ui.male

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.signature.ObjectKey
import com.mjslick.R
import com.mjslick.model.MaleWear
import com.mjslick.ui.factory.AddMaleClothFactory
import com.mjslick.utility.Constants
import com.mjslick.utility.Network
import kotlinx.android.synthetic.main.fragment_add_male_cloth.view.*
import java.io.ByteArrayOutputStream
import java.util.*

class AddMaleClothFragment : Fragment() {


    private lateinit var selectedImageBytes: ByteArray
    private lateinit var viewModel: AddMaleClothViewModel
    private lateinit var root: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         root = inflater.inflate(R.layout.fragment_add_male_cloth, container, false)

        val application = requireNotNull(this.activity).application
        val viewModelFactory = AddMaleClothFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddMaleClothViewModel::class.java)


        root.maleSelectPicture.setOnClickListener {
            clearImage()
            if (!permissionIfNeeded()) {
                if (Build.VERSION.SDK_INT < 19) {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(intent, "select Picture"),
                        Constants.OPEN_MEDIA_PICKER
                    )
                }else {
                    val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.addCategory(Intent.CATEGORY_OPENABLE)
                    intent.type = "image/*"
                    startActivityForResult(intent, Constants.OPEN_MEDIA_PICKER)
                }
            }
        }

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.OPEN_MEDIA_PICKER) {
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
                            loadImage(selectedImageBytes,root.maleShirtImage1)
                            root.maleShirtImage1.visibility = View.VISIBLE
                        }
                        if (i == 1) {
                            loadImage(selectedImageBytes, root.maleShirtImage2)
                            root.maleShirtImage2.visibility = View.VISIBLE
                        }
                        if (i == 2) {
                            loadImage(selectedImageBytes, root.maleShirtImage3)
                            root.maleShirtImage3.visibility = View.VISIBLE
                        }
                        if (i == 3) {
                            loadImage(selectedImageBytes, root.maleShirtImage4)
                            root.maleShirtImage4.visibility = View.VISIBLE
                        }
                        if (i == 4) {
                            Toast.makeText(requireContext(), "Can't select more than 4 item, try again",
                                Toast.LENGTH_LONG).show()
                            clearImage()
                        }
                        val clothsImages = listOf(selectedImageBytes)
                        if (root.maleShirtImage1 != null){
                            viewModel.uploadMaleClothImage(clothsImages){imagePath ->
                                addMaleCloth(imagePath)
                            }
                        }else{
                            Toast.makeText(requireContext(), "Please select image",
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
                    loadImage(selectedImageBytes, root.maleShirtImage1)
                    root.maleShirtImage1.visibility = View.VISIBLE

                    val clothsImages = listOf(selectedImageBytes)
                    if (root.maleShirtImage1 != null){
                        viewModel.uploadMaleClothImage(clothsImages){imagePath ->
                            addMaleCloth(imagePath)
                        }
                    }else{
                        Toast.makeText(requireContext(), "Please select image",
                            Toast.LENGTH_SHORT).show()
                    }
                }

            }
        }
    }

    private fun addMaleCloth(imagePath: List<String>) {
        root.saveMaleCloths.setOnClickListener {
            if (Network.isOnline(requireContext())){
            val clothName = Objects.requireNonNull(root.male_cloth_name.text.toString())
            val clothType = root.select_male_cloth.selectedItem.toString()
            val clothDescription = root.male_cloth_description.text.toString()
            val topPrice = root.male_top_price.text.toString()
            val trouserPrice = root.male_trouser_price.text.toString()
            val completePrice = root.male_complete_price.text.toString()

            val maleWear = MaleWear(
                clothName, clothType, clothDescription,
               getString(R.string.currency_naira) + topPrice,
                getString(R.string.currency_naira) + trouserPrice,
                getString(R.string.currency_naira) + completePrice, imagePath, ""
            )

            if (TextUtils.isEmpty(clothName) || TextUtils.isEmpty(clothDescription)) {
                Toast.makeText(
                    requireContext(), "Name and description Can't be empty",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                viewModel.addMaleCloth(maleWear)
                Toast.makeText(
                    requireContext(), "cloths added successfully",
                    Toast.LENGTH_SHORT
                ).show()
                Navigation.findNavController(root).navigate(R.id.navigation_male)
            }
        }else{
                Toast.makeText(requireContext(), "Check Network", Toast.LENGTH_SHORT).show()
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
        root.maleShirtImage1.setImageResource(android.R.color.transparent)
        root.maleShirtImage2.setImageResource(android.R.color.transparent)
        root.maleShirtImage3.setImageResource(android.R.color.transparent)
        root.maleShirtImage4.setImageResource(android.R.color.transparent)
        root.maleShirtImage1.visibility = View.GONE
        root.maleShirtImage2.visibility = View.GONE
        root.maleShirtImage3.visibility = View.GONE
        root.maleShirtImage4.visibility = View.GONE
    }

    private fun permissionIfNeeded(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                )
                != PackageManager.PERMISSION_GRANTED) {
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    Toast.makeText(requireContext(), "accept permission to read device storage",
                        Toast.LENGTH_SHORT).show()
                }

                requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    Constants.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE
                )
                return true
            }
        }
        return false
    }

}