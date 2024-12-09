package com.example.magazincustom2

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ListView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.io.IOException

class ActivityTwo : AppCompatActivity() {

    val GALERRY_REQUEST = 1

    var bitmap: Bitmap? = null

    val products: MutableList<Product> = mutableListOf()

private lateinit var toolbar: Toolbar
    private lateinit var listViewLW: ListView
    private lateinit var saveBTN: Button
    private lateinit var productPriceET:EditText
    private lateinit var productNameET: EditText
    private lateinit var editImageIV:ImageView



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        listViewLW = findViewById(R.id.listViewLW)
        saveBTN = findViewById(R.id.saveBTN)
        productNameET = findViewById(R.id.productNameET)
        productPriceET = findViewById(R.id.productPriceET)
        editImageIV = findViewById(R.id.editImageIV)
        toolbar = findViewById(R.id.toolbar)
        title = "Корзина с продуктами"
        setSupportActionBar(toolbar)

        editImageIV.setOnClickListener{
            val photoPickerIntent = Intent(Intent.ACTION_PICK)
            photoPickerIntent.type = "image/*"
            startActivityForResult(photoPickerIntent, GALERRY_REQUEST)
        }
saveBTN.setOnClickListener {
    val productName = productNameET.text.toString()
    val productPrice = productPriceET.text.toString()
    val productimage = bitmap
    val product = Product(productName, productPrice, productimage)
    products.add(product)
    val ListAdapter = ListAdapter(this@ActivityTwo, products)
    listViewLW.adapter = ListAdapter
    ListAdapter.notifyDataSetChanged()
    productNameET.text.clear()
    productPriceET.text.clear()
    editImageIV.setImageResource(R.drawable.ic_launcher_foreground)
}
    }


    override fun onActivityResult(
        requestCode: Int,
        resultCode: Int,
        data: Intent?,
    ) {
        super.onActivityResult(requestCode, resultCode, data)
        editImageIV = findViewById(R.id.imageViewIV)
        when (requestCode) {
            GALERRY_REQUEST -> if (resultCode == RESULT_OK) {
                val selectedImage: Uri? = data?.data
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, selectedImage)
                } catch (e: IOException) {
                    e.printStackTrace()
                    editImageIV.setImageBitmap(bitmap)

                }
            }

        }

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_exit, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.menu_exit -> finishAffinity()
        }
        return super.onOptionsItemSelected(item)
    }

}




