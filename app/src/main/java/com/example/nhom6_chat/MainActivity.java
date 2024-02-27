package com.example.nhom6_chat;

import com.example.nhom6_chat.Adapter.FragmentsAdapter;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.nhom6_chat.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        mAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = binding.toolbar;
        setSupportActionBar(toolbar);

        binding.viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        final int itemId = item.getItemId();

        if (itemId == R.id.settings) {
            //Toast.makeText(this, "Setting is clicked", Toast.LENGTH_SHORT).show();
            Intent intent5 = new Intent(MainActivity.this,SettingsActivity.class);
            startActivity(intent5);
        } else if (itemId == R.id.groupChat) {
            // Toast.makeText(this, "Group Chat is Started", Toast.LENGTH_SHORT).show();
            Intent intent4 = new Intent(MainActivity.this, GroupChatActivity.class);
            startActivity(intent4);
        } else if (itemId == R.id.meeting) {
            // Toast.makeText(this, "Group Chat is Started", Toast.LENGTH_SHORT).show();
            Intent intent3 = new Intent(MainActivity.this, Meeting.class);
            startActivity(intent3);
        }else if (itemId == R.id.Location) {
            // Toast.makeText(this, "Group Chat is Started", Toast.LENGTH_SHORT).show();
            Intent intent2 = new Intent(MainActivity.this, MapActivity.class);
            startActivity(intent2);
        }else if (itemId == R.id.Game) {
            // Toast.makeText(this, "Group Chat is Started", Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(MainActivity.this, GameActivity.class);
            startActivity(intent1);
        }else if (itemId == R.id.logout) {
            mAuth.signOut();
            Intent intent = new Intent(MainActivity.this, SignInActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}