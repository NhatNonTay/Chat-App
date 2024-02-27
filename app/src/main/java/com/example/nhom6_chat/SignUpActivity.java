package com.example.nhom6_chat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.nhom6_chat.Models.Users;
import com.example.nhom6_chat.databinding.ActivitySignUpBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    ActivitySignUpBinding binding;
    private FirebaseAuth mAuth;

    FirebaseDatabase database;

    private AlertDialog.Builder builder;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());//chuyển đổi file layout XML thành các đối tượng Java,dễ dàng tương tác với các phần tử trong layout
        setContentView(binding.getRoot());//trả về View chính của layout

        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        builder = new AlertDialog.Builder(this);//xây dựng,tùy chỉnh cửa sổ thông báo.
        builder.setTitle("Tạo tài khoản");
        builder.setMessage("Chúng tôi đang tạo tài khoản của bạn.");
        final AlertDialog alertDialog = builder.create();
        binding.btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!binding.txtUsername.getText().toString().isEmpty() && !binding.txtEmail.getText().toString().isEmpty() && !binding.txtPassWord.getText().toString().isEmpty()) {
                    builder.show();
                    mAuth.createUserWithEmailAndPassword(binding.txtEmail.getText().toString(), binding.txtPassWord.getText().toString())//Dùng Firebase Authentication để tạo tài khoản với email và mật khẩu được nhập từ người dùng.
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    alertDialog.dismiss();
                                    if (task.isSuccessful()) { //Nếu tài khoản được tạo thành công, tiến hành lưu thông tin người dùng vào Firebase Realtime Database.
                                        Users user = new Users(binding.txtUsername.getText().toString(),binding.txtEmail.getText().toString(),binding.txtPassWord.getText().toString());
                                        String id = task.getResult().getUser().getUid();
                                        database.getReference().child("Users").child(id).setValue(user);//Lấy tham chiếu đến cơ sở dữ liệu Firebase.
                                        Toast.makeText(SignUpActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish(); // Kết thúc SignUpActivity để người dùng không thể quay lại màn hình đăng ký
                                    } else {
                                        Toast.makeText(SignUpActivity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } else {
                    Toast.makeText(SignUpActivity.this, "Thông tin không xác thực", Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.txtAlreadyHaveAccout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,SignInActivity.class);
                startActivity(intent);
            }
        });
    }
}