package com.erns.canvaspre.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erns.canvaspre.R;
import com.erns.canvaspre.canvas.RoomView;
import com.erns.canvaspre.controller.EventViewModel;
import com.erns.canvaspre.model.database.AppDatabase;
import com.erns.canvaspre.model.database.GalleryRepository;
import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomAndVertex;
import com.erns.canvaspre.utils.ExecuteTask;

import java.util.List;
import java.util.UUID;

public class RoomFragment extends Fragment implements RoomFragmentListener {

    private static final String ARG_PARAM1 = "param1";
    private int roomIdParam;
    private final RoomFragmentListener roomFragmentListener = this;
    private GalleryRepository repository;
    private RoomView roomView;

    public RoomFragment() {
        // Required empty public constructor
    }

    public static RoomFragment newInstance(int roomId) {
        RoomFragment fragment = new RoomFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, roomId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            roomIdParam = getArguments().getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_room, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EventViewModel eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        repository = new GalleryRepository(AppDatabase.getInstance(requireContext()));

        roomView = view.findViewById(R.id.roomview);
        roomView.setEventViewModel(eventViewModel);

        loadRoomsVertexes(roomIdParam);

    }

    private void loadRoomsVertexes(int roomId) {
        ExecuteTask executeTask = new ExecuteTask();
        executeTask.asyncTask(() ->
                roomFragmentListener.onResultRoomVertex(repository.getRoomWithVertexByRoomId(roomId))
        );
    }

    private void loadPictures(int roomId) {
        ExecuteTask executeTask = new ExecuteTask();
        executeTask.asyncTask(() ->
                roomFragmentListener.onResultPicture(repository.getPicturesByRoomId(roomId))
        );
    }
    private void loadDoors() {
        ExecuteTask task = new ExecuteTask();
        task.asyncTask(() ->
                roomFragmentListener.onResultDoors(repository.getDoors())
        );
    }

    @Override
    public void onResultRoomVertex(RoomAndVertex data) {
        if (roomView != null) {
            roomView.setRoom(data);
            loadPictures(roomIdParam);
            loadDoors();
        }
    }

    @Override
    public void onResultPicture(List<PictureEntity> data) {
        if (roomView != null) {
            roomView.setPictures(data);

        }
    }

    @Override
    public void onResultDoors(List<DoorEntity> data) {
        if (roomView != null) {
            roomView.setDoors(data);
        }
    }
}