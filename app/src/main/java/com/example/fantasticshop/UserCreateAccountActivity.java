package com.example.fantasticshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCreateAccountActivity extends AppCompatActivity {
    EditText userCreateAccount_name, userAccountEmail, userAccountPassword, userConfirmPassword;
    Button btnCreateAccount;

    public static final String MY_SHARED_PREF = "sharedPref";

    public static final String EMAIL_ADDRESS_REGEX = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
            "\\@"  +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
            "(" +
            "\\."  +
            "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}"  +
            ")";

    public static final String PASSWORD_REGEX = "^" +
            "(?=.*[0-9])"  +
            "(?=.*[a-z])" +
            "(?=.*[A-Z])" +
            "(?=.*[@#$!%*?^&+=])"  +  // at least 1 special character
            "([0-9a-zA-Z@#$!%*?^&+=]{6,})"  +  // no white space and at least 6 characters
            "$";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_create_account);
        userCreateAccount_name = findViewById(R.id.userCreateAccount_name_id);
        userAccountEmail = findViewById(R.id.userCreateAccount_email_id);
        userAccountPassword = findViewById(R.id.userCreateAccount_password_id);
        userConfirmPassword = findViewById(R.id.userCreateAccount_confirm_password_id);
        btnCreateAccount = findViewById(R.id.userLogin_btn_create_account_id);

        btnCreateAccount.setOnClickListener(view -> {
            String user_name = userCreateAccount_name.getText().toString();
            String user_email = userAccountEmail.getText().toString();
            String user_password = userAccountPassword.getText().toString();


            if ( confirmedInput()==1) {
                SharedPreferences sharedPreferences = getSharedPreferences(MY_SHARED_PREF, Context.MODE_PRIVATE);
                SharedPreferences.Editor myEditor = sharedPreferences.edit();

                myEditor.putString("userName", user_name);
                myEditor.putString("password", user_password);
                myEditor.putString("email", user_email);

                myEditor.apply();

                UserCreateAccountActivity.this.finish();
            }


        });
    }

    public Boolean emailValidation(){

        String email = userAccountEmail.getText().toString().trim();
        Pattern pattern = Pattern.compile(EMAIL_ADDRESS_REGEX);
        Matcher matcher = pattern.matcher(email);

        if(email.isEmpty()){
            userAccountEmail.setError("Please, fill the empty field");
            return false;
        } else if (!(matcher.matches())){
            userAccountEmail.setError("Wrong email address");
            return false;
        }else {
            userAccountEmail.setError(null);
            return true;
        }

    }

    public Boolean passwordValidation() {
        String password = userAccountPassword.getText().toString().trim();
        String confirmedPassword = userConfirmPassword.getText().toString().trim();
        Pattern pattern = Pattern.compile(PASSWORD_REGEX);
        Matcher matcher = pattern.matcher(password);

        if (password.isEmpty()) {
            userAccountPassword.setError("Please, fill the empty password field");
            return false;
        } else if (!(matcher.matches())) {
            userAccountPassword.setError("Wrong password, must be at least 6 characters that contains:" +
                    "\nany character of [a-z or A-Z]" +
                    "\nat least 1 digit [0-9]" +
                    "\nat least 1 special character [@#$%^&+=]" +
                    "\nno white space");
            return false;

        } else if (!(confirmedPassword.equals(password))) {
            userAccountPassword.setError("confirmed password does not matches, please check your password");
            userAccountPassword.setText("");
            return false;
        } else {
            userAccountPassword.setError(null);
            return true;
        }
    }

    public int confirmedInput(){
        if (emailValidation() && passwordValidation()){
            return 1;
        }else {
            return 0;
        }
    }
}