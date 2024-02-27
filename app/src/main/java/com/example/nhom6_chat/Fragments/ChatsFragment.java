package com.example.nhom6_chat.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.nhom6_chat.Adapter.UsersAdapter;
import com.example.nhom6_chat.Models.Users;
import com.example.nhom6_chat.R;
import com.example.nhom6_chat.UpLoadVideoActivity;
import com.example.nhom6_chat.databinding.FragmentChatsBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ChatsFragment extends Fragment {




    public ChatsFragment() {
    }

    FragmentChatsBinding binding;
    ArrayList<Users> list = new ArrayList<>();
    FirebaseDatabase database;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        binding = FragmentChatsBinding.inflate(inflater,container, false);//binding ánh xạ các thành phần giao diện từ file XML layout của Fragment, inflate để chuyển đổi file XML layout thành đối tượng FragmentChatsBinding.
        database = FirebaseDatabase.getInstance();//cung cấp các phương thức để tương tác với cơ sở dữ liệu thời gian thực của Firebase

        // Khởi tạo đối tượng UsersAdapter và thiết lập RecyclerView
        UsersAdapter adapter = new UsersAdapter(list, getContext());
        binding.chatRecyclerView.setAdapter(adapter);

        // Thiết lập LayoutManager cho RecyclerView
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        binding.chatRecyclerView.setLayoutManager(layoutManager);

        database.getReference().child("Users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();//Xóa tất cả các phần tử khỏi danh sách để chuẩn bị cho việc thêm dữ liệu mới.

                // Lặp qua tất cả các children trong "Users"
                for(DataSnapshot dataSnapshot : snapshot.getChildren()){// Lấy dữ liệu từ dataSnapshot và chuyển đổi thành đối tượng Users
                    Users users = dataSnapshot.getValue(Users.class);
                    // Thiết lập UserId cho đối tượng Users từ key của dataSnapshot
                    users.setUserId(dataSnapshot.getKey());
                    if (!users.getUserId().equals(FirebaseAuth.getInstance().getUid())){
                        list.add(users);
                    }
                }
                // Thông báo cho adapter biết rằng dữ liệu đã thay đổi và cần cập nhật giao diện
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        return binding.getRoot();
    }
}