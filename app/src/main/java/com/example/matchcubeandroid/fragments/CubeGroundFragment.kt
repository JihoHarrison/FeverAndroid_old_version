package com.example.matchcubeandroid.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.login.LoginActivity
import com.example.matchcubeandroid.activities.main.MainActivity
import com.example.matchcubeandroid.sharedPreferences.MySharedPreferences
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_cubeground.view.*

class CubeGroundFragment : Fragment()  {

    private lateinit var activity : MainActivity
    //firebase Auth
    private lateinit var firebaseAuth: FirebaseAuth
    //google client
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cubeground, container, false)
        var context: Context = view.context

        //Google 로그인 옵션 구성. requestIdToken 및 Email 요청
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.google_client_id))
            .requestEmail()
            .build()

        googleSignInClient = GoogleSignIn.getClient(context, gso)

        //firebase auth 객체
        firebaseAuth = FirebaseAuth.getInstance()

        view.button1.setOnClickListener{
            signOut()
        }
        view.button2.setOnClickListener{
            revokeAccess()
        }

        return view
    }

    private fun signOut() { // 구글로그아웃

        MySharedPreferences.clearUser(requireContext())

        // Firebase sign out
        firebaseAuth.signOut()

        // Google sign out
        googleSignInClient.signOut().addOnCompleteListener{
            toMainActivity(firebaseAuth.currentUser)
        }
    }

    private fun revokeAccess() { // 구글회원탈퇴

        MySharedPreferences.clearUser(requireContext())

        // Firebase sign out
        firebaseAuth.currentUser.delete()

        // Google sign out
        googleSignInClient.revokeAccess().addOnCompleteListener{
            toMainActivity(firebaseAuth.currentUser)
        }
    }

    // toMainActivity
    fun toMainActivity(user: FirebaseUser?) {
        if(user == null) { // MainActivity 로 이동
            startActivity(Intent(context, LoginActivity::class.java))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}