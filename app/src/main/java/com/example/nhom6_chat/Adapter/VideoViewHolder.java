package com.example.nhom6_chat.Adapter;

import android.media.MediaPlayer;
import android.net.Uri;
import android.view.View;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nhom6_chat.R;

// ViewHolder cho video
public class VideoViewHolder extends RecyclerView.ViewHolder {
    private VideoView videoView;
    private OnVideoCreatedListener onVideoCreatedListener;
    public VideoViewHolder(@NonNull View itemView) {
        super(itemView);
        videoView = itemView.findViewById(R.id.videoView);
    }

    public void setVideoUrl(String videoUrl) {
        videoView.setVideoURI(Uri.parse(videoUrl));
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Bắt đầu phát video khi đã chuẩn bị xong
                videoView.start();
            }
        });
    }
    public void setOnVideoCreatedListener(OnVideoCreatedListener listener) {
        this.onVideoCreatedListener = listener;
    }

    public interface OnVideoCreatedListener {
        void onVideoCreated(VideoView videoView);
    }
}
