package com.example.matchcubeandroid.activities.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.fragments.MyPageFragment
import com.example.matchcubeandroid.model.LogInModel
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.kakao.sdk.auth.LoginClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.AuthErrorCause.*
import com.nhn.android.naverlogin.*
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.*
import kotlin.Result.Companion.success


class LoginActivity : AppCompatActivity() {

    lateinit var mOAuthLoginInstance : OAuthLogin
    lateinit var mContext: Context

    private val TAG = "retrofit"
    private lateinit var auth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 200

   // kakao
   val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
       if (error != null) {
           when {
               error.toString() == AccessDenied.toString() -> {
                   Toast.makeText(this, "접근이 거부 됨(동의 취소)", Toast.LENGTH_SHORT).show()
               }
               error.toString() == InvalidClient.toString() -> {
                   Toast.makeText(this, "유효하지 않은 앱", Toast.LENGTH_SHORT).show()
               }
               error.toString() == InvalidGrant.toString() -> {
                   Toast.makeText(this, "인증 수단이 유효하지 않아 인증할 수 없는 상태", Toast.LENGTH_SHORT).show()
               }
               error.toString() == InvalidRequest.toString() -> {
                   Toast.makeText(this, "요청 파라미터 오류", Toast.LENGTH_SHORT).show()
               }
               error.toString() == InvalidScope.toString() -> {
                   Toast.makeText(this, "유효하지 않은 scope ID", Toast.LENGTH_SHORT).show()
               }
               error.toString() == Misconfigured.toString() -> {
                   Toast.makeText(this, "설정이 올바르지 않음(android key hash)", Toast.LENGTH_SHORT).show()
               }
               error.toString() == ServerError.toString() -> {
                   Toast.makeText(this, "서버 내부 에러", Toast.LENGTH_SHORT).show()
               }
               error.toString() == Unauthorized.toString() -> {
                   Toast.makeText(this, "앱이 요청 권한이 없음", Toast.LENGTH_SHORT).show()
               }
               else -> { // Unknown
                   Toast.makeText(this, "기타 에러", Toast.LENGTH_SHORT).show()
               }
           }
       }
       else if (token != null) {
            Toast.makeText(this, "로그인에 성공하였습니다.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
        }
    }

    private fun signIn(){
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "구글 로그인 실패", e)
                // ...
            }
        }
    }

    private fun updateUI(account: FirebaseUser?) { // 로그인 후에 AfterActivity로 intent로 옮겨준다
            startActivity(Intent(this@LoginActivity, MyPageFragment::class.java))
            finish()
    }

    private fun signOut(){ // 로그아웃 함수
        FirebaseAuth.getInstance().signOut()
    }

    private fun revokeAccess(){ // 회원 탈퇴 함수
        auth.currentUser.delete()
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth?.signInWithCredential(credential)
                ?.addOnCompleteListener(this@LoginActivity) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "구글로그인 성공")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "signInWithCredential:failed", task.exception)
                        Toast.makeText(this@LoginActivity, "로그인 실패", Toast.LENGTH_SHORT).show()
                        // ..
                        updateUI(null)
                    }
                    // ...
                }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        //  네이버 아이디로 로그인
        val naver_client_id = getString(R.string.naver_client_id)
        val naver_client_secret = getString(R.string.naver_client_secret)
        val naver_client_name = getString(R.string.naver_client_name)

        mContext = this

        mOAuthLoginInstance = OAuthLogin.getInstance()
        mOAuthLoginInstance.init(mContext, naver_client_id, naver_client_secret, naver_client_name)
        buttonOAuthLoginImg.setOAuthLoginHandler(mOAuthLoginHandler)

//        if (mOAuthLoginInstance.getAccessToken(this) != null) {
//            startActivity(Intent(this@LoginActivity, MainActivity::class.java))
//            finish();
//        } else {
//            binding.buttonOAuthLoginImg.setOAuthLoginHandler(new NaverHandler(this, oAuthLogin, this));
//        }

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("801343645302-3d4d6fakuasfvqjkpmh1b2hp8p4bvnsj.apps.googleusercontent.com")
                .requestEmail()
                .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        auth = FirebaseAuth.getInstance()

//        if (auth.currentUser != null) {
//            val intent = Intent(application, MyPageFragment::class.java)
//            startActivity(intent)
//            finish()
//        }

        btnGoogleLogin.setOnClickListener {
            signIn()
        }

        btnKakaoLogin.setOnClickListener {
            if(LoginClient.instance.isKakaoTalkLoginAvailable(this)){
                LoginClient.instance.loginWithKakaoTalk(this, callback = callback)
            }else{
                LoginClient.instance.loginWithKakaoAccount(this, callback = callback)
            }
        }

        btnLogin.setOnClickListener {
            val body = HashMap<String, String>()
            body.put("emailId", editId.text.toString())
            body.put("password", editPw.text.toString())
            // 서비스 호출부
            Client.retrofitService.logIn(body).enqueue(object : Callback<LogInModel> {
                override fun onResponse(call: Call<LogInModel>, response: Response<LogInModel>) {

                    if (response.body()?.statusCode == 200) { // 200 : successful
                        val data = response.body()?.data
                        data?.let { success(data) }
                        // 자동 로그인 처리
                        if (checkAutoLogin.isChecked()) {
                            MySharedPreferences.setAutoChecked(this@LoginActivity, "Y")
                        } else {
                            MySharedPreferences.setAutoChecked(this@LoginActivity, "N")
                        }
                        // 데이터 저장
                        MySharedPreferences.setUserId(this@LoginActivity, editId.text.toString())
                        MySharedPreferences.setUserPass(this@LoginActivity, editPw.text.toString())

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))

                    } else {
                        Toast.makeText(this@LoginActivity, response.body()?.responseMessage, Toast.LENGTH_SHORT).show()
                        Log.d(TAG, "${response.body()?.toString()}")
                    }
                }

                override fun onFailure(call: Call<LogInModel>?, t: Throwable?) {
                    t?.message?.let {
                        Toast.makeText(this@LoginActivity, it, Toast.LENGTH_SHORT).show()
                    }
                }
            })
        }
    }

    // Naver
    val mOAuthLoginHandler: OAuthLoginHandler = object : OAuthLoginHandler() {
        override fun run(success: Boolean) {
            if (success) {
//                val accessToken: String = mOAuthLoginModule.getAccessToken(baseContext)
//                val refreshToken: String = mOAuthLoginModule.getRefreshToken(baseContext)
//                val expiresAt: Long = mOAuthLoginModule.getExpiresAt(baseContext)
//                val tokenType: String = mOAuthLoginModule.getTokenType(baseContext)
//                var intent = Intent(this, )
                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
            } else {
                val errorCode: String = mOAuthLoginInstance.getLastErrorCode(mContext).code
                val errorDesc = mOAuthLoginInstance.getLastErrorDesc(mContext)

                Toast.makeText(
                        baseContext, "errorCode:" + errorCode
                        + ", errorDesc:" + errorDesc, Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

}