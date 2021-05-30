package com.example.matchcubeandroid.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.model.AccountIdModel
import com.example.matchcubeandroid.model.EditProfileModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.activity_edit_my_profile.*
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.fragment_my_page.*
import kotlinx.android.synthetic.main.fragment_my_page.txtName
import okhttp3.internal.http.hasBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Body


class EditMyProfileActivity : AppCompatActivity() {




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_my_profile)

        val editnickname: EditText = findViewById(R.id.editNickname)
        val editname:EditText = findViewById(R.id.editName)
        val editphonenumber: EditText = findViewById(R.id.editPhoneNumber)
        val editbirthday: EditText = findViewById(R.id.editBirth)



        Client.retrofitService.accountId(1).enqueue(object : Callback<AccountIdModel> {
            override fun onResponse(
                call: Call<AccountIdModel>,
                response: Response<AccountIdModel>
            ) {

                if (response.body()?.statusCode == 100) { // 100 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }
                    Toast.makeText(
                        this@EditMyProfileActivity,
                        response.body()?.responseMessage,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.d("Account", "${response.body()?.toString()}")
                    editnickname.setText(data?.nickName)
                    editname.setText(data?.name)
                    editphonenumber.setText(data?.phoneNumber)
                    editbirthday.setText(data?.birthday)



                } else {
                    Toast.makeText(
                        this@EditMyProfileActivity,
                        response.body()?.responseMessage,
                        Toast.LENGTH_SHORT
                    )
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

        btnUpdate.setOnClickListener {update(
            editnickname.text.toString(),
            editname.text.toString(),
            editphonenumber.text.toString(),
            editBirth.text.toString()
        )}

    }

    private fun update(name: String,
                       nickName: String,
                       phoneNumber: String,
                       birthday: String) {
        var body = HashMap<String, Any>()
        body.put("name", name)
        body.put("nickName", nickName)
        body.put("phoneNumber", phoneNumber)
        body.put("birthday", birthday)

        Client.retrofitService.update(body).enqueue(object : Callback<EditProfileModel> {
            override fun onResponse(
                call: Call<EditProfileModel>,
                response: Response<EditProfileModel>
            ) {

                if (response.body()?.statusCode == 101) { // 100 : successful
                    val data = response.body()?.data
                    data?.let { Result.success(data) }

                    Toast.makeText(this@EditMyProfileActivity,
                        response.body()?.responseMessage,
                        Toast.LENGTH_SHORT
                    ).show()

                    //MySharedPreferences.setUserId(this@EditMyProfileActivity,response.body()!!.accountId )
                    //MySharedPreferences.setSocialType(this@EditMyProfileActivity, socialType)

                    //Log.d("account", "account  : " + MySharedPreferences.getUserId(this@RegisterActivity) )

                    startActivity(Intent(this@EditMyProfileActivity, MainActivity::class.java))
                    //finish()




                    Log.d("Account", "${response.body()?.toString()}")


                } else {
                    Toast.makeText(
                        this@EditMyProfileActivity,
                        response.body()?.responseMessage,
                        Toast.LENGTH_SHORT
                    )
                        .show()
                    Log.d("d", "${response.body()?.toString()}")
                }
            }

            override fun onFailure(call: Call<EditProfileModel>, t: Throwable) {
                t?.message?.let {
                    Toast.makeText(this@EditMyProfileActivity, it, Toast.LENGTH_SHORT).show()
                }
            }


        })

    }








    // 프로필 이미지 누르면 갤러리에 있는 이미지 불러올수 있도록하는 코드




}



