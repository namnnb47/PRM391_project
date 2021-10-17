package com.example.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.finalproject.ReusableCode.ReusableCodeForAll;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.concurrent.locks.ReadWriteLock;

public class RegisterActivity extends AppCompatActivity {

    private TextView login;
    private Button signup;
    private EditText Pass;
    private EditText cfpass;
    private EditText Name;
    private EditText Email;
    private EditText mobileno;

    FirebaseAuth FAuth;

    private String emailid;
    private String name;
    private String password;
    private String confirmpassword;
    private String mobile;

    private String role = "Customer";
    DatabaseReference databaseReference;
    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        login = (TextView) findViewById(R.id.loginNow);
        signup = (Button) findViewById(R.id.btnSignUp);
        Email = (EditText) findViewById(R.id.txtEmailRegister);
        Name = (EditText) findViewById(R.id.txtNameRegister);
        mobileno = (EditText)findViewById(R.id.txtPhoneNumber);
        Pass = (EditText) findViewById(R.id.txtPasswordRegister);
        cfpass = (EditText) findViewById(R.id.txtPassword_confirmRegister);

        databaseReference = firebaseDatabase.getInstance().getReference("Customer");
        FAuth = FirebaseAuth.getInstance();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
            }
        });

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailid = Email.getText().toString().trim();
                name = Name.getText().toString().trim();
                mobile = mobileno.getText().toString().trim();
                password = Pass.getText().toString().trim();
                confirmpassword = cfpass.getText().toString().trim();
                if(isValid()){

                    final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                    mDialog.setCancelable(false);
                    mDialog.setCanceledOnTouchOutside(false);
                    mDialog.setMessage("Registering Please wait ....");
                    mDialog.show();

                    FAuth.createUserWithEmailAndPassword(emailid,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                String userid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                databaseReference = FirebaseDatabase.getInstance().getReference("Role").child(userid);
                                final HashMap<String,String> hashMap = new HashMap<>();
                                hashMap.put("Role", role);
                                databaseReference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        HashMap<String,String> hashMap1 = new HashMap<>();
                                        hashMap1.put("Email",emailid);
                                        hashMap1.put("Name", name);
                                        hashMap1.put("Phone",mobile);
                                        hashMap1.put("Password",password);
                                        hashMap1.put("Confirm Password", confirmpassword);

                                        FirebaseDatabase.getInstance().getReference("Customer")
                                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                                .setValue(hashMap1).addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                mDialog.dismiss();

                                                FAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){
                                                            AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                                                            builder.setMessage("Registered Successfully,Please Verify your Email");
                                                            builder.setCancelable(false);
                                                            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                                @Override
                                                                public void onClick(DialogInterface dialogInterface, int i) {
                                                                    dialogInterface.dismiss();

                                                                    String phoneNumber = "+84" + mobile.substring(1);
                                                                    Intent intent = new Intent(RegisterActivity.this,VerifyPhoneActivity.class);
                                                                    intent.putExtra("phonenumber",phoneNumber);
                                                                    startActivity(intent);

                                                                }
                                                            });
                                                            AlertDialog alertDialog = builder.create();
                                                            alertDialog.show();
                                                        }else{
                                                            mDialog.dismiss();
                                                            ReusableCodeForAll.ShowAlert(RegisterActivity.this,"Error",task.getException().getMessage());

                                                        }
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }else{
                                mDialog.dismiss();
                                ReusableCodeForAll.ShowAlert(RegisterActivity.this, "Error", task.getException().getMessage());
                            }
                        }
                    });
                }

            }
        });


    }

    String emailpattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    public boolean isValid() {
        Email.setError("");
        Name.setError("");
        Pass.setError("");
        mobileno.setError("");
        cfpass.setError("");


        boolean isValidname = false, isValidemail = false, isvalidpassword = false, isvalidconfirmpassword = false, isvalid = false, isvalidmobileno = false;
        if (TextUtils.isEmpty(name)) {
            Name.setError("Name is required");
        } else {
            isValidname = true;
        }


        if (TextUtils.isEmpty(emailid)) {
            Email.setError("Email is required");
        } else {
            if (emailid.matches(emailpattern)) {
                isValidemail = true;
            } else {
                Email.setError("Enter a valid Email Address");
            }

        }
        if (TextUtils.isEmpty(password)) {
            Pass.setError("Password is required");
        } else {
            if (password.length() < 6) {
                Pass.setError("password too weak");
            } else {
                isvalidpassword = true;
            }
        }
        if (TextUtils.isEmpty(confirmpassword)) {
            cfpass.setError("Confirm Password is required");
        } else {
            if (!password.equals(confirmpassword)) {
                Pass.setError("Password doesn't match");
            } else {
                isvalidconfirmpassword = true;
            }
        }
        if (TextUtils.isEmpty(mobile)) {
            mobileno.setError("Mobile number is required");
        } else {
            if (mobile.length() < 10) {
                mobileno.setError("Invalid mobile number");
            } else {
                isvalidmobileno = true;
            }
        }

        if(isValidname == true && isvalidmobileno == true && isValidemail == true && isvalidpassword == true && isvalidconfirmpassword == true){
            return  true;
        }else{
            return false;
        }

    }
}