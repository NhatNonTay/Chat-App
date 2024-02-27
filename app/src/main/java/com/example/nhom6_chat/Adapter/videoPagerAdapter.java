package com.example.nhom6_chat.Adapter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.nhom6_chat.R;
import com.example.nhom6_chat.videoModel;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

import java.util.List;

public class videoPagerAdapter extends RecyclerView.Adapter<VideoViewHolder> {

    private ViewPager2 viewPager2;
    private List<String> videoUrls;

    private VideoView currentVideoView;

    public videoPagerAdapter(List<String> videoUrls, ViewPager2 viewPager2) {
        this.videoUrls = videoUrls;
    }
    public videoPagerAdapter(List<String> videoUrls) {
        this.videoUrls = videoUrls;
    }

    @NonNull
    @Override
    public VideoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo ViewHolder cho video
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_video, parent, false);
        return new VideoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoViewHolder holder, int position) {
        // Thiết lập videoUrl cho ViewHolder
        String videoUrl = videoUrls.get(position);
        holder.setVideoUrl(videoUrl);

        // Lắng nghe sự kiện khi video được tạo ra
        holder.setOnVideoCreatedListener(new VideoViewHolder.OnVideoCreatedListener() {
            @Override
            public void onVideoCreated(VideoView videoView) {
                // Lưu trữ VideoView hiện tại
                currentVideoView = videoView;
            }
        });
    }

    @Override
    public int getItemCount() {
        return videoUrls.size();
    }
    public void stopAllVideos() {
        // Dừng video hiện tại
        if (currentVideoView != null && currentVideoView.isPlaying()) {
            currentVideoView.stopPlayback();
        }
    }
}


