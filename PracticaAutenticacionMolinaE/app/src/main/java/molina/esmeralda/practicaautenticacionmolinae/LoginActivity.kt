package molina.esmeralda.practicaautenticacionmolinae

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseUser

class LoginActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_activity)


        auth = Firebase.auth

        val email: EditText = findViewById(R.id.etEmail)
        val password: EditText = findViewById(R.id.etPassword)
        val errorTv: TextView = findViewById(R.id.tvError)
        val button: Button = findViewById(R.id.btnLogin)
        val buttonR: Button = findViewById(R.id.btnGoRegister)

        errorTv.visibility = View.INVISIBLE

        button.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()

            if (emailText.isEmpty() || passwordText.isEmpty()) {
                showError("Por favor ingrese email y contraseña", true)
            } else {
                login(emailText, passwordText)
            }
        }

        buttonR.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }


    fun goToMain(user: FirebaseUser) {
        val intent = Intent( this, MainActivity::class.java)
        intent.putExtra( "user", user.email)
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun showError(text: String = "", visible: Boolean){
    val errorTv: TextView = findViewById(R.id.tvError)
    errorTv.text = text
    errorTv.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    public override fun onStart()
    {
        super.onStart()
        val currentUser = auth.currentUser
        if (currentUser != null) {
            goToMain(currentUser)
        }
    }

    fun login(email: String, password: String){
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                showError(visible = false)
                goToMain (user!!)
            } else {
                showError ("Usuario Y/o contraseña equivocados",  true)
            }
        }
    }



}

