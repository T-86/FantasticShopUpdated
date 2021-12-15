package com.example.fantasticshop;

import static com.example.fantasticshop.UserCreateAccountActivity.EMAIL_ADDRESS_REGEX;
import static com.example.fantasticshop.UserCreateAccountActivity.MY_SHARED_PREF;
import static com.example.fantasticshop.UserCreateAccountActivity.PASSWORD_REGEX;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserLoginActivity extends AppCompatActivity {
    private EditText userLoginEmail_input;
    private EditText userLoginPassword_input;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_login);

        // Grab the view resources by id's for email & password inputs
        userLoginEmail_input = findViewById(R.id.userLogin_email_input_id);
        userLoginPassword_input = findViewById(R.id.userLogin_password_input_id);
        Button userLoginBtn = findViewById(R.id.userLogin_btn_id);
        Button userCreateNewAccount = findViewById(R.id.userLogin_createAccount_btn_id);

        SharedPreferences sharedPreferences = getSharedPreferences(MY_SHARED_PREF, Context.MODE_PRIVATE);
        String last_user_email = sharedPreferences.getString("lastEmail", null);
        if (last_user_email != null) {
            Log.e("Shopper","Got Default user email");
            userLoginEmail_input.setText(last_user_email);
        }
        else Log.e("Shopper","Didn't get Default user email");

        userLoginBtn.setOnClickListener(v -> {
            // SharedPreferences sharedPreferences = getSharedPreferences(MY_SHARED_PREF, Context.MODE_PRIVATE);
            String user_name = sharedPreferences.getString("userName", null);
            String user_email = sharedPreferences.getString("email", null);
            String user_password = sharedPreferences.getString("password", null);

            String userLoginEmail = userLoginEmail_input.getText().toString();
            String userLoginPassword = userLoginPassword_input.getText().toString();


            if (emailValidation() && userLoginEmail.equalsIgnoreCase(user_email)) {
                // create an intent telling android to go to HomeActivity
                Intent intent = new Intent(UserLoginActivity.this, HomeActivity.class);
                if (passwordValidation() && userLoginPassword.equalsIgnoreCase(user_password)) {
                    SharedPreferences.Editor myEditor = sharedPreferences.edit();
                    myEditor.putString("lastEmail", user_email);
                    myEditor.apply();
                    Toast.makeText(UserLoginActivity.this, String.format("%s %s", getString(R.string.user_login_successful), user_name), Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (!(emailValidation() && userLoginEmail.equalsIgnoreCase(user_email))) {

                    Toast.makeText(UserLoginActivity.this, String.format("%s %s", getString(R.string.user_login_failed), user_name), Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(UserLoginActivity.this, String.format("%s %s", getString(R.string.user_login_failed), user_name), Toast.LENGTH_SHORT).show();
            }


        });

        // user create a new account
        userCreateNewAccount.setOnClickListener(v -> {
            Intent intent = new Intent(UserLoginActivity.this, UserCreateAccountActivity.class);
            startActivity(intent);
        });
    }

    public Boolean emailValidation(){

        String email = userLoginEmail_input.getText().toString().trim();
        Pattern pattern = Pattern.compile(EMAIL_ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(email.isEmpty()){
            userLoginEmail_input.setError("Please, fill the empty field");
            return false;
        } else if (!(matcher.matches())){
            userLoginEmail_input.setError("Wrong email address");
            return false;
        }else {
            userLoginEmail_input.setError(null);
            return true;
        }

    }

    public Boolean passwordValidation() {
        String password = userLoginPassword_input.getText().toString().trim();
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);

        if (password.isEmpty()) {
            userLoginPassword_input.setError("Please, fill the empty password field");
            return false;
        } else if (!(matcher.matches())) {
            userLoginPassword_input.setError("Wrong password, please try again");
            return false;
        }else {
            userLoginPassword_input.setError(null);
            return true;
        }
    }




}