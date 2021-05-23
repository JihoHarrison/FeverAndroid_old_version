package com.example.matchcubeandroid.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.image.URLtoBitmapTask
import com.example.matchcubeandroid.model.AccountIdModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_edit_my_profile.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.net.URL


class EditMyProfileActivity : AppCompatActivity() {



    val OPEN_GALLERY = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_my_profile)




        val name: TextView = findViewById(R.id.MyProfile)
        val profileimage: CircleImageView = findViewById(R.id.editImgProfile)


        Client.retrofitService.accountId(1).enqueue(object : Callback<AccountIdModel> {
            override fun onResponse(
                call: Call<AccountIdModel>,
                response: Response<AccountIdModel>
            ) {

                if (response.body()?.statusCode == 100) { // 100 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(this@EditMyProfileActivity, response.body()?.responseMessage, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("Account", "${response.body()?.toString()}")
                    name.setText(data?.name)

                    // 이미지 처리 객체
                    var image_task: URLtoBitmapTask = URLtoBitmapTask()
                    image_task = URLtoBitmapTask().apply {
                        url = URL(data?.profileImage)
                    }
                    var bitmap: Bitmap = image_task.execute().get()
                    profileimage.setImageBitmap(bitmap)
                } else {
                    Toast.makeText(this@EditMyProfileActivity, response.body()?.responseMessage, Toast.LENGTH_SHORT)
                        .show()
                    Log.d("d", "${response.body()?.toString()}")
                }
            }

            override fun onFailure(call: Call<AccountIdModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(this@EditMyProfileActivity, it, Toast.LENGTH_SHORT).show()
                }
            }

        })


        profileimage.setOnClickListener {
            val intent2: Intent = Intent(Intent.ACTION_PICK)
            intent2.setType(MediaStore.Images.Media.CONTENT_TYPE)
            startActivityForResult(intent2, 1)

        }



    }

    // 프로필 이미지 누르면 갤러리에 있는 이미지 불러올수 있도록하는 코드

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == OPEN_GALLERY) {
                data?.data?.let { uri ->
                    editImgProfile.setImageURI(uri)
                }
            }
        }

    }



}



