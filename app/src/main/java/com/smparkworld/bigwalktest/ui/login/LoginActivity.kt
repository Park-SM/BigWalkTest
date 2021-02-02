package com.smparkworld.bigwalktest.ui.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.smparkworld.bigwalktest.BigWalkApp
import com.smparkworld.bigwalktest.R
import com.smparkworld.bigwalktest.databinding.ActivityLoginBinding
import com.smparkworld.bigwalktest.ui.donation.DonationActivity
import javax.inject.Inject

class LoginActivity : AppCompatActivity() {

    @Inject
    lateinit var mAuth: FirebaseAuth

    @Inject
    lateinit var mGoogleOptions: GoogleSignInOptions

    private lateinit var googleClient: GoogleSignInClient

    private lateinit var mBinding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding =DataBindingUtil.setContentView<ActivityLoginBinding>(this, R.layout.activity_login).apply {
            btnGoogleSignIn.setOnClickListener { signIn() }
        }
        (application as BigWalkApp).appComponent.loginComponent().create().inject(this)
        if (mAuth.currentUser != null) {
            val intent = Intent(application, DonationActivity::class.java)
            startActivity(intent)
            finish()
        }

        googleClient = GoogleSignIn.getClient(this, mGoogleOptions)
    }

    private fun signIn() {
        showLoadingLayout(true)

        val signInIntent = googleClient.signInIntent
        startActivityForResult(signInIntent, GOOGLE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == GOOGLE_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)!!
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                showLoadingLayout(false)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        mAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser?.displayName ?: mAuth.currentUser?.uid ?: ""
                    Toast.makeText(this, user + "님 환영합니다.", Toast.LENGTH_SHORT).show()
                    loginSuccess()
                } else {
                    Toast.makeText(this, "로그인 실패", Toast.LENGTH_SHORT).show()
                    showLoadingLayout(false)
                }
            }
    }

    private fun loginSuccess(){
        val intent = Intent(this, DonationActivity::class.java)
        startActivity(intent)
        finish()
    }

    private fun showLoadingLayout(isShow: Boolean) {
        mBinding.loadingLayout.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    companion object {
        const val GOOGLE_REQUEST_CODE = 999
    }
}