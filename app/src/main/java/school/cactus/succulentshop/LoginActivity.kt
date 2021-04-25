package school.cactus.succulentshop

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import school.cactus.succulentshop.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    private val identifierValidator = IdentifierValidator()
    private val passwordValidator = PasswordValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.log_in)

        binding.apply {
            logInButton.setOnClickListener {
                passwordInputLayout.validate()
                identifierInputLayout.validate()
            }

            createAccountButton.setOnClickListener {
                navigateToSecondActivity()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_SECOND_ACTIVITY) {
            if (resultCode == SecondActivity.RESULT_YES) {
                Log.e("LoginActivity", "Yes!")
            } else {
                Log.e("LoginActivity", "Nooo!")
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }

    private fun navigateToSecondActivity() {
        val intent = Intent(this, SecondActivity::class.java)
        intent.putExtra("isLoggedIn", true)
        startActivityForResult(intent, REQUEST_SECOND_ACTIVITY)
    }

    private fun TextInputLayout.validate() {
        val errorMessage = validator().validate(editText!!.text.toString())
        error = errorMessage?.resolveAsString()
        isErrorEnabled = errorMessage != null
    }

    private fun Int.resolveAsString() = getString(this)

    private fun TextInputLayout.validator() = when (this) {
        binding.identifierInputLayout -> identifierValidator
        binding.passwordInputLayout -> passwordValidator
        else -> throw IllegalArgumentException("Cannot find any validator for the given TextInputLayout")
    }

    companion object {
        const val REQUEST_SECOND_ACTIVITY = 1001
    }
}
