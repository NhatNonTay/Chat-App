package com.example.nhom6_chat.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.nhom6_chat.R;
import com.example.nhom6_chat.UpLoadVideoActivity;

public class UpLoadVideo extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_up_load_video, container, false);

        // Find the button by its ID
        Button btnNavigateToUploadActivity = view.findViewById(R.id.btnNavigateToUploadActivity);

        // Set click listener for the button
        btnNavigateToUploadActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an Intent to start UpLoadVideoActivity
                Intent intent = new Intent(getActivity(), UpLoadVideoActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}