package com.example.matchcubeandroid.activities.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.retrofit.Client
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.example.matchcubeandroid.social.SocialType
import com.example.matchcubeandroid.social.setSocialType
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.activity_login.*
import kotlin.concurrent.thread

// 구글 파이어베이스의 OAuth 토큰을 사용하기 위해서 카카오톡 로그인과 구글 로그인을 통합
// 그래서 카카오톡 로그인 성공시 해당 정보를 갖고 구글로그인 가입이 되게 하여 토큰을 받을 수 있게
// 즉 카카오톡 로그인 -> 구글 로그인 -> 가입 완료가 되는 절차
class LoginActivity : AppCompatActivity() {

    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    //private const val TAG = "GoogleActivity"
    private val RC_SIGN_IN = 99

    var getAccountYn: Int = 0
    var accountId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 구글 로그인 버튼
        btnGoogleLogin.setOnClickListener {signIn()}

        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(this, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()
    }

    // onStart. 유저가 앱에 이미 구글 로그인을 했는지 확인
    public override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        if(account!==null){ // 이미 로그인 되어있을시 바로 메인 액티비티로 이동
            toMainActivity(firebaseAuth.currentUser)
        }
    }

    // onActivityResult
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)

            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w("LoginActivity", "Google sign in failed", e)
            }
        }
    }

    // firebaseAuthWithGoogle
    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Log.d("LoginActivity", "firebaseAuthWithGoogle:" + acct.id!!)
        Log.d("idToken", "idToken : " + acct.idToken!!)

        //Google SignInAccount 객체에서 ID 토큰을 가져와서 Firebase Auth로 교환하고 Firebase에 인증
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val emailId = firebaseAuth.currentUser.email
                    val socialType = setSocialType(SocialType.GOOGLE)

                    Log.d("juntae1", "email : " + emailId + "social type : " + socialType)

                    // 동기 처리
                    thread(start = true){
                        getAccountYn = getAccountYn(emailId, socialType)
                    }

                    Thread.sleep(1000L)

                    Log.d("getAccountYn1", "getAccountYn : " + getAccountYn)
                    if (getAccountYn == 100){

                        Log.d("alstn", "accountId : " + accountId)
                        MySharedPreferences.setUserId(this@LoginActivity, accountId )
                        Log.d("alstn2", "accountId : " + MySharedPreferences.getUserId(this))
                        MySharedPreferences.setSocialType(this@LoginActivity, socialType)
                        toMainActivity(firebaseAuth?.currentUser)
                    }else{
                        // 회원가입 화면으로 이동
                        val registerIntent = Intent(this, RegisterActivity::class.java)
                        registerIntent.putExtra("emailId", emailId)
                        registerIntent.putExtra("socialType", socialType)
                        startActivity(registerIntent)
                        finish()
                    }
                } else {
                }
            }
    }// firebaseAuthWithGoogle END

    // toMainActivity
    fun toMainActivity(user: FirebaseUser?) {
        if(user !=null) { // MainActivity 로 이동
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    } // toMainActivity End

    // signIn
    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun signOut() { // 로그아웃
        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener(this) {
            //updateUI(null)
        }
    }

    private fun getAccountYn(email: String, socialType: String): Int{ // DB 회원등록 여부 조회 동기처리
        val data = Client.retrofitService.isExstUser(email, socialType).execute()
        Log.d("getAccountYn2", "getAccountYn : " + data)
        accountId = data.body()?.accountId!!
        return data.body()?.statusCode!!
    }
}

