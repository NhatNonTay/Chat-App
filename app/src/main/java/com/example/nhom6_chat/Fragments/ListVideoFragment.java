package com.example.nhom6_chat.Fragments;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nhom6_chat.Adapter.videoPagerAdapter;
import com.example.nhom6_chat.R;
import com.example.nhom6_chat.UpLoadVideoActivity;
import com.example.nhom6_chat.videoModel;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ListVideoFragment extends Fragment {

    private videoPagerAdapter adapter;

    private MediaPlayer mediaPlayer;

  ViewPager2 viewPager2;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View view = inflater.inflate(R.layout.fragment_list_video, container, false);

    // Khởi tạo ViewPager2 của bạn
    viewPager2 = view.findViewById(R.id.vpager);



      viewPager2.setOrientation(ViewPager2.ORIENTATION_VERTICAL); // Đặt hướng lướt là dọc



      // Thêm mã khởi tạo còn lại cho fragment của bạn
    List<String> videoUrls = new ArrayList<>(); // Khởi tạo danh sách videoUrls

    videoPagerAdapter adapter = new videoPagerAdapter(videoUrls, viewPager2);
    viewPager2.setAdapter(adapter);


    return view;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageRef = storage.getReference().child("videos");
    storageRef.listAll()
            .addOnSuccessListener(listResult -> {
              for (StorageReference item : listResult.getItems()) {
                // Lấy đường dẫn của video
                String videoUrl = item.getDownloadUrl().toString();
                // Thực hiện các thao tác với videoUrl (ví dụ: hiển thị trong ViewPager2)
              }
            })
            .addOnFailureListener(exception -> {
              // Xử lý lỗi nếu có
            });
    List<String> videoUrls = new ArrayList<>(); // Thêm danh sách videoUrls
    storageRef.listAll()
            .addOnSuccessListener(listResult -> {
                for (StorageReference item : listResult.getItems()) {
                    // Lấy đường dẫn của video và thêm vào danh sách
                    item.getDownloadUrl().addOnSuccessListener(uri -> {
                        String videoUrl = uri.toString();
                        videoUrls.add(videoUrl);

                        // Kiểm tra xem đã thêm đủ đường dẫn chưa
                        if (videoUrls.size() == listResult.getItems().size()) {
                            // Tất cả các đường dẫn đã được lấy, tạo Adapter và thiết lập danh sách videoUrls
                            videoPagerAdapter adapter = new videoPagerAdapter(videoUrls);
                            viewPager2.setAdapter(adapter);
                        }
                    }).addOnFailureListener(exception -> {
                        // Xử lý lỗi khi lấy đường dẫn
                    });
                }
            })
            .addOnFailureListener(exception -> {
                // Xử lý lỗi nếu có
            });
  }
    public void onPause() {
        super.onPause();
        // Gọi stopAllVideos khi Fragment bị tạm dừng
        if (adapter != null) {
            adapter.stopAllVideos();
        }
    }

}