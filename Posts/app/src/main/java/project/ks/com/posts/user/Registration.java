package project.ks.com.posts.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.Date;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

public class Registration extends AppCompatActivity {


    Button next;
    EditText firstName,lastName,email,password,confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        firstName = (EditText) findViewById(R.id.regFirstName);
        lastName = (EditText) findViewById(R.id.regLastName);
        email = (EditText) findViewById(R.id.regEmail);
        password = (EditText) findViewById(R.id.regPassword);
        confirmPassword = (EditText) findViewById(R.id.regConfirmPassword);
        next = (Button) findViewById(R.id.regNextUserImgBtn);

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String firstNameValue = firstName.getText().toString();
                String lastNameValue = lastName.getText().toString();
                String emailValue = email.getText().toString();
                String passwordValue = password.getText().toString();
                String confirmPasswordValue = confirmPassword.getText().toString();

                firstName.setError(null);
                lastName.setError(null);
                email.setError(null);
                password.setError(null);
                confirmPassword.setError(null);

                boolean cancel = false;
                View focusView = null;

                if(TextUtils.isEmpty(firstNameValue)){
                    firstName.setError("First name required");
                    focusView = firstName;
                    cancel = true;
                }else

                if(TextUtils.isEmpty(lastNameValue)){
                    lastName.setError("Last name required");
                    focusView = lastName;
                    cancel = true;
                } else

                if(TextUtils.isEmpty(emailValue)){
                    email.setError("email required");
                    focusView = email;
                    cancel = true;
                } else

                if (!Utils.isEmailValid(emailValue)){
                    email.setError("enter valid email");
                    focusView = email;
                    cancel = true;
                } else

                if(TextUtils.isEmpty(passwordValue) || !Utils.isPasswordValid(passwordValue)){
                    password.setError("Invalid password");
                    focusView = password;
                    cancel = true;
                } else

                if(!passwordValue.equals(confirmPasswordValue)){
                    confirmPassword.setError("Password not match");
                    focusView = confirmPassword;
                    cancel = true;
                }

                if(cancel){
                    focusView.requestFocus();
                }else{

                    User user = new User(firstNameValue,lastNameValue,emailValue,passwordValue,null,null, AppConstant.DEFAULT_DMY_DATE_FORMAT.format(new Date()));
                    Intent intent = new Intent(Registration.this,SelectUserProfile.class);
                    intent.putExtra(AppConstant.USER_JSON, Utils.generateJSONFromObject(user));
                    startActivity(new Intent(intent));
                    finish();
                }

            }
        });
    }

}
