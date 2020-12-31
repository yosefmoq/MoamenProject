package com.yosefmoq.moamenproject.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;
import com.yosefmoq.moamenproject.MainActivity;
import com.yosefmoq.moamenproject.R;
import com.yosefmoq.moamenproject.models.User;
import com.yosefmoq.moamenproject.utils.Session;
import com.yosefmoq.moamenproject.utils.Validator;

public class SigninActivity extends AppCompatActivity {
    String password, username;
    EditText etUsername, etPassword;
    Button btnLogin;
    TextView tvSignup;
    FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        findViews();
        btnLogin.setOnClickListener(view -> {
            password = etPassword.getText().toString();
            username = etUsername.getText().toString();
            boolean isValidEmail = Validator.isEmailValid(username);
            if (!isValidEmail) {
                etUsername.setError("Please Enter valid email");
                return;
            }

            boolean isValidPassword = Validator.isValidPassword(password);
            if (!isValidPassword) {
                etPassword.setError("Please Enter valid password");
                return;
            }
            login(username, password);

        });
        tvSignup.setOnClickListener(view -> {
            startActivity(new Intent(SigninActivity.this, SignupActivity.class));
            finish();
        });
    }

    private void findViews() {
        etUsername = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        tvSignup = findViewById(R.id.tvSignUp);
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    private void login(String email, String password) {
        firebaseFirestore.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).limit(1).addSnapshotListener((value, error) -> {
            if (value.getDocuments().size() > 0) {
                User user = value.getDocuments().get(0).toObject(User.class);
                Session.getInstance(this).getLocalSave().setUserInfo(user);
                Session.getInstance(this).getLocalSave().setLoginAsGuest(true);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "error in email or password", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void startActivity() {
        String email = "";
        String password = "";
        firebaseFirestore.collection("users").whereEqualTo("email", email).whereEqualTo("password", password).limit(1).addSnapshotListener((value, error) -> {
            if (value.getDocuments().size() > 0) {
                User user = value.getDocuments().get(0).toObject(User.class);
                Session.getInstance(this).getLocalSave().setUserInfo(user);
                Session.getInstance(this).getLocalSave().setLoginAsGuest(true);
                startActivity(new Intent(this, MainActivity.class));
                finish();
            } else {
                Toast.makeText(this, "error in email or password", Toast.LENGTH_SHORT).show();
            }
        });    }

    public void printErrorMessage(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }
}