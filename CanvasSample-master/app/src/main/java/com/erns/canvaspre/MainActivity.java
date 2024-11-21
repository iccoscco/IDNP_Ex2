package com.erns.canvaspre;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.erns.canvaspre.canvas.RoomViewListener;
import com.erns.canvaspre.controller.EventViewModel;
import com.erns.canvaspre.fragments.HomeFragment;
import com.erns.canvaspre.fragments.GalleryFragment;
import com.erns.canvaspre.fragments.ListFragment;
import com.erns.canvaspre.fragments.PictureFragment;
import com.erns.canvaspre.fragments.RoomFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity
        implements RoomViewListener {
    private final String TAG = "MainActivity";
    private EventViewModel eventViewModel;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private GalleryFragment galleryFragment;
    private ListFragment listFragment;
    private PictureFragment pictureFragment;
    private RoomFragment roomFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnItemSelectedListener(onItemSelectedListener);

        eventViewModel = new ViewModelProvider(this).get(EventViewModel.class);
        eventViewModel.onRoomSelected().observe(this, roomSelectedObserver);
        eventViewModel.onPictureSelected().observe(this, pictureSelectedObserver);
        eventViewModel.onCloseFragment().observe(this,closeFragmentObserver);

        fragmentManager = getSupportFragmentManager();

        init();

    }

    private void init() {
        loadFragment(HomeFragment.newInstance("", ""));
    }

    private final Observer<Integer> roomSelectedObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer roomId) {
            Log.d("TAG", "roomId:" + roomId);
            roomFragment = RoomFragment.newInstance(roomId);
            loadFragment(roomFragment);
        }
    };

    private final Observer<Integer> pictureSelectedObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer pictureId) {
            Log.d("TAG", "pictureId:" + pictureId);
            loadFragment(PictureFragment.newInstance(pictureId));
        }
    };

    private final Observer<Integer> closeFragmentObserver = new Observer<Integer>() {
        @Override
        public void onChanged(Integer roomId) {
            Log.d("TAG", "roomId:" + roomId);
            loadFragment(RoomFragment.newInstance(roomId));
        }
    };

    private final NavigationBarView.OnItemSelectedListener onItemSelectedListener = new NavigationBarView.OnItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.menu_home) {
                homeFragment = HomeFragment.newInstance("", "");
                loadFragment(homeFragment);
                return true;
            } else if (item.getItemId() == R.id.menu_map_room) {
                galleryFragment = GalleryFragment.newInstance();
                loadFragment(galleryFragment);
                return true;
            } else if (item.getItemId() == R.id.menu_list) {
                listFragment = ListFragment.newInstance();
                loadFragment(listFragment);
                return true;
            } else {
                return false;
            }
        }
    };

    private void loadFragment(Fragment fragment) {
        if (fragmentManager != null) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainerView, fragment);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void closePictureFragment() {
        Log.d(TAG, "MainActivity open picture ");
        if (galleryFragment == null) {
            galleryFragment = GalleryFragment.newInstance();

        }
        loadFragment(galleryFragment);
    }

//    public void onPause(){
//        Intent serviceIntent=new Intent(....)
//        serviceIntent.putExtra("command","start_foreground");
//        startService(serviceIntent);
//    }
//    public void onResume(){
//        Intent serviceIntent=new Intent(....)
//        serviceIntent.putExtra("command","close_foreground");
//        startService(serviceIntent);
//    }

}