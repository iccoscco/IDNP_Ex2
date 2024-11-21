package com.erns.canvaspre.fragments;

import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.erns.canvaspre.R;
import com.erns.canvaspre.controller.EventViewModel;
import com.erns.canvaspre.model.database.AppDatabase;
import com.erns.canvaspre.model.database.FileRepository;
import com.erns.canvaspre.model.database.GalleryRepository;
import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomAndVertex;
import com.erns.canvaspre.model.ent.RoomEntity;
import com.erns.canvaspre.model.ent.VertexEntity;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Executors;


public class HomeFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;
    private Button btnFillRooms;
    private Button btnReadRooms;
    private FileRepository fileRepository;
    private GalleryRepository repository;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
//        View view = inflater.inflate(R.layout.fragment_home, container, false);
//
//        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fileRepository = new FileRepository(getContext());
        repository = new GalleryRepository(AppDatabase.getInstance(requireContext()));
        btnFillRooms = view.findViewById(R.id.btnFillRooms);
        btnReadRooms = view.findViewById(R.id.btnReadRooms);

        btnFillRooms.setOnClickListener(btnFillRoomsOnClick);
        btnReadRooms.setOnClickListener(btnReadRoomsOnClick);

    }

    private final View.OnClickListener btnFillRoomsOnClick = v -> {

        String[] vertexFiles = new String[]{"RoomVertex001.txt", "RoomVertex002.txt", "RoomVertex003.txt"};

        List<VertexEntity> vertexEntityList = fileRepository.getVertexes(vertexFiles);
        Executors.newSingleThreadExecutor().execute(() -> {
                    repository.addVertexes(vertexEntityList);
                }
        );

        String doorsFilename = "Doors.txt";
        List<DoorEntity> doorEntityList = fileRepository.getDoors(doorsFilename);
        Executors.newSingleThreadExecutor().execute(() -> {
                    repository.addDoors(doorEntityList);
                }
        );

        String picturesFilename = "Pictures.txt";
        List<PictureEntity> pictureEntityList = fileRepository.getPictures(picturesFilename);
        Executors.newSingleThreadExecutor().execute(() -> {
                    repository.addPictures(pictureEntityList);
                }
        );

        String roomFilename = "Rooms.txt";
        List<RoomEntity> roomEntityList = fileRepository.getRooms(roomFilename);
        Executors.newSingleThreadExecutor().execute(() -> {
                    repository.addRooms(roomEntityList);
                }
        );

    };

    private final View.OnClickListener btnReadRoomsOnClick = v -> {

        GalleryRepository repository = new GalleryRepository(AppDatabase.getInstance(getContext()));

        Executors.newSingleThreadExecutor().execute(() -> {
                    List<RoomAndVertex> roomAndVertexList = repository.getRoomWithVertexes();
                    roomAndVertexList.forEach(obj -> {
                        Log.d("Room:", obj.roomEntity.label);
                        obj.vertexEntityList.forEach(t -> {
                            Log.d("Vertex", t.getRoomId() + "," + t.getX() + "," + t.getY());
                        });
                    });

                    List<PictureEntity> pictureEntityList = repository.getPictures();
                    pictureEntityList.forEach(obj -> {

                        Log.d("Vertex", obj.roomId + "," + obj.pictureId + "," + obj.title);

                    });
                }
        );

    };

}