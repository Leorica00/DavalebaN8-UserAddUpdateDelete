package com.example.davaleban8

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatEditText
import com.example.davaleban8.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {
    private val emailPattern =
        "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
    private lateinit var binding: ActivityUserBinding
    private lateinit var user: User
    private lateinit var userToUpdate: User
    private lateinit var addInputButton: AppCompatButton
    private lateinit var updateInputButton: AppCompatButton
    private lateinit var name: AppCompatEditText
    private lateinit var surname: AppCompatEditText
    private lateinit var email: AppCompatEditText
    private lateinit var users: MutableList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        with(binding) {
            name = nameEt
            surname = surnameEt
            email = emailEt
            addInputButton = addInputBtn
            updateInputButton = updateInputBtn
        }
        users = UserListSingleton.users
        setUp()
        listeners()
    }

    private fun setUp(){
        getData()
    }

    private fun listeners(){
        setUpUserAddClick()
        setUpUpdateUser()
    }

    private fun setUpUserAddClick() {
        addInputButton.setOnClickListener {
            if (fullValidation(name, surname, email)) {
                if (!users.contains(user)) {
                    users.add(user)
                    cleanEditTexts()
                    setResult(RESULT_OK, Intent())
                    finish()
                } else {
                    showToast(this, "User already exists")
                }
            }
        }
    }

    private fun updateSetUp() {
        updateInputButton.visibility = View.VISIBLE
        user = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent.getParcelableExtra("user", User::class.java)!!
        } else {
            intent.getParcelableExtra("user")!!
        }
        userToUpdate = user
        user.name?.let { it1 ->
            user.surname?.let { it2 ->
                user.email?.let { it3 ->
                    setData(
                        it1, it2, it3
                    )
                }
            }
        }
    }

    private fun getData() {
        val data = intent.extras?.getString("ButtonValue")
        data?.let {
            if (it == "Add") {
                addInputButton.visibility = View.VISIBLE
            } else if (it == "Update") {
                updateSetUp()
            }
        }
    }

    private fun setUpUpdateUser() {
        updateInputButton.setOnClickListener {
            if (fullValidation(name, surname, email)) {
                if (!users.contains(user)) {
                    val index = users.indexOf(userToUpdate)
                    users[index] = user
                    val intent = Intent()
                    intent.putExtra("UserIndex", index)
                    setResult(RESULT_OK, intent)
                    addInputButton.visibility = View.GONE
                    finish()
                }
            }
        }
    }

    private fun setData(fName: String, lName: String, mail: String) {
        name.setText(fName)
        surname.setText(lName)
        email.setText(mail)
    }

    private fun fullValidation(
        firstName: AppCompatEditText,
        lastName: AppCompatEditText,
        email: AppCompatEditText,
    ): Boolean {
        if (checkIfEmpty(firstName, lastName, email)) {
            val mail: String? = emailValidation(email)
            mail?.let {
                user = User(
                    firstName.text.toString(),
                    lastName.text.toString(),
                    email.text.toString(),
                )
                return true
            }
            return false
        }
        return false
    }

    private fun checkIfEmpty(
        firstName: AppCompatEditText,
        lastName: AppCompatEditText,
        email: AppCompatEditText,
    ): Boolean {
        val fName: String = firstName.text.toString()
        val lName: String = lastName.text.toString()
        val mail: String = email.text.toString()
        return if (fName.isEmpty() || lName.isEmpty() || mail.isEmpty()) {
            showToast(this, "Fill all Fields")
            false
        } else {
            true
        }
    }

    private fun emailValidation(editText: AppCompatEditText): String? {
        val email: String = editText.text.toString()
        return if (!email.matches(emailPattern.toRegex())) {
            showToast(this, "Email is not valid.")
            null
        } else {
            email
        }
    }

    private fun cleanEditTexts() {
        name.setText("")
        surname.setText("")
        email.setText("")
    }

    private fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }
}
