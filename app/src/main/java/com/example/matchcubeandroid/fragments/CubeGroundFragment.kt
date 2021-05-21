package com.example.matchcubeandroid.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.matchcubeandroid.R
import com.example.matchcubeandroid.activities.main.MainActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_cubeground.view.*

class CubeGroundFragment : Fragment()  {

    private lateinit var activity : MainActivity
    private lateinit var googleSignInClient: GoogleSignInClient

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_cubeground, container, false)

        var gso =
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.google_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(view.context, gso)

        view.button.setOnClickListener{
            FirebaseAuth.getInstance().signOut()

            googleSignInClient.signOut().addOnCompleteListener{
                activity.finish()
            }
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}