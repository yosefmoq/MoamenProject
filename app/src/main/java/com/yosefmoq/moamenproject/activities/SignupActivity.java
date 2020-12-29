package com.yosefmoq.moamenproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yosefmoq.moamenproject.MainActivity;
import com.yosefmoq.moamenproject.R;
import com.yosefmoq.moamenproject.models.User;
import com.yosefmoq.moamenproject.utils.Session;
import com.yosefmoq.moamenproject.utils.Validator;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class SignupActivity extends AppCompatActivity {
    SweetAlertDialog sweetAlertDialog;
    FirebaseFirestore firebaseFirestore;
    String email, password, username, confirmPassword;
    EditText etEmail, etPassword, etUsername, etConfirmPassword;
    Button btnSignup;
    TextView tvSignin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sweetAlertDialog = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        setContentView(R.layout.activity_signup);
        findViews();
    }

    private void findViews() {
        firebaseFirestore = FirebaseFirestore.getInstance();
        etEmail = findViewById(R.id.etEmail);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etConfirmPassword = findViewById(R.id.etConfirmPassword);
        tvSignin = findViewById(R.id.tvSignIn);
        btnSignup = findViewById(R.id.btnLogin);
        tvSignin.setOnClickListener(view -> {
            startActivity(new Intent(this, SigninActivity.class));
            finish();
        });
        btnSignup.setOnClickListener(view -> {
            email = etEmail.getText().toString();
            password = etPassword.getText().toString();
            confirmPassword = etConfirmPassword.getText().toString();
            username = etUsername.getText().toString();
            if (!Validator.isValidUsername(username)) {
                etUsername.setError("Username is not valid");
                return;
            }
            if (!Validator.isEmailValid(email)) {
                etEmail.setError("Email Is not Valid");
                return;
            }
            if (!Validator.isValidPassword(password)) {
                etPassword.setError("Password is not valid");
                return;
            }
            if (!Validator.isValidConfirmPassword(password, confirmPassword)) {
                etConfirmPassword.setError("Confirm password is not valid");
                return;
            }
            register(email, username, password);

        });
    }

    private void register(String email, String username, String password) {
        User user = new User();
        sweetAlertDialog.show();
        firebaseFirestore.collection("users").get().addOnCompleteListener(runnable -> {
            int id = Objects.requireNonNull(runnable.getResult()).getDocuments().size() + 1;
            user.setId(id);
            user.setName(username);
            user.setEmail(email);
            user.setPassword(password);
            firebaseFirestore.collection("users").document(String.valueOf(id)).set(user).addOnCompleteListener(runnable1 -> {
                sweetAlertDialog.hide();
                Session.getInstance(this).getLocalSave().setUserInfo(user);
                Session.getInstance(this).getLocalSave().setLoginAsGuest(true);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            });
        });
    }
}