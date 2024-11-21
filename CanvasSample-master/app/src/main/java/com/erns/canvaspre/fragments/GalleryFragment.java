package com.erns.canvaspre.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.erns.canvaspre.R;
import com.erns.canvaspre.canvas.GalleryView;
import com.erns.canvaspre.controller.EventViewModel;
import com.erns.canvaspre.model.database.AppDatabase;
import com.erns.canvaspre.model.database.GalleryRepository;
import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomAndVertex;
import com.erns.canvaspre.utils.ExecuteTask;

import java.util.List;


public class GalleryFragment extends Fragment implements GalleryFragmentListener {
    private GalleryView galleryview;
    private final GalleryFragmentListener galleryFragmentListener = this;
    private GalleryRepository repository;
    private EventViewModel eventViewModel;

    public GalleryFragment() {
        // Required empty public constructor
    }

    public static GalleryFragment newInstance() {
        GalleryFragment fragment = new GalleryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new GalleryRepository(AppDatabase.getInstance(requireContext()));
        galleryview = view.findViewById(R.id.galleryview);
        //galleryview.setScaleFactor(100f);
        galleryview.setRadiusPicture(30);

        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        galleryview.setEventViewModel(eventViewModel);

        loadRoomsVertexes();

    }

    private void loadPictures() {
        ExecuteTask task = new ExecuteTask();
        task.asyncTask(() ->
                galleryFragmentListener.onResultPictures(repository.getPictures())
        );

    }

    private void loadRoomsVertexes() {
        ExecuteTask task = new ExecuteTask();
        task.asyncTask(() ->
                galleryFragmentListener.onResultRoomVertex(repository.getRoomWithVertexes())
        );

    }

    private void loadDoors() {
        ExecuteTask task = new ExecuteTask();
        task.asyncTask(() ->
                galleryFragmentListener.onResultDoors(repository.getDoors())
        );
    }

    @Override
    public void onResultPictures(List<PictureEntity> data) {
        if (galleryview != null) {
            galleryview.setPictures(data);
        }
    }

    @Override
    public void onResultRoomVertex(List<RoomAndVertex> data) {
        if (galleryview != null) {
            galleryview.setRooms(data);
            loadDoors();
            loadPictures();
        }
    }

    @Override
    public void onResultDoors(List<DoorEntity> data) {
        if (galleryview != null) {
            galleryview.setDoors(data);
        }
    }

}