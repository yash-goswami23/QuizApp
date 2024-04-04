package com.example.onequiz.activitys;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.onequiz.fragments.ProfileFragment;
import com.example.onequiz.R;
import com.example.onequiz.databinding.ActivityMainBinding;
import com.example.onequiz.fragments.HomeFragment;
import com.example.onequiz.fragments.LeaderBoardFragment;
import com.example.onequiz.fragments.walletFragment;

import me.ibrahimsn.lib.OnItemSelectedListener;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setSupportActionBar(binding.toolbar);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.content, new HomeFragment());
        transaction.commit();

        binding.bottomBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {
                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (i) {
                    case 0:
                        transaction.replace(R.id.content, new HomeFragment());
                        transaction.commit();
                        break;
                    case 1:
                        transaction.replace(R.id.content, new LeaderBoardFragment());
                        transaction.commit();
                        break;
                    case 2:
                        transaction.replace(R.id.content, new walletFragment());
                        transaction.commit();
                        break;
                    case 3:
                        transaction.replace(R.id.content, new ProfileFragment());
                        transaction.commit();
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.tool_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.wallet){
            Toast.makeText(this, "Wallet", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }
}