package com.erns.canvaspre.fragments;

import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.erns.canvaspre.R;
import com.erns.canvaspre.controller.EventViewModel;
import com.erns.canvaspre.model.database.AppDatabase;
import com.erns.canvaspre.model.database.FileRepository;
import com.erns.canvaspre.model.database.GalleryRepository;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.utils.ExecuteTask;

public class PictureFragment extends Fragment implements PictureFragmentListener {
    private static final String ARG_PARAM2 = "param2";
    private EventViewModel eventViewModel;
    private final PictureFragmentListener pictureFragmentListener = this;
    private GalleryRepository repository;
    private TextView txtTitle;
    private TextView txtDescription;
    private ImageView imageView;
    private int roomId = 200;//NOT FOUND
    private int pictureId;

    public PictureFragment() {
        // Required empty public constructor
        //this.roomViewListener = roomViewListener;
    }

    public static PictureFragment newInstance(int pictureId) {
        PictureFragment fragment = new PictureFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM2, pictureId);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            pictureId = getArguments().getInt(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_picture, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        repository = new GalleryRepository(AppDatabase.getInstance(requireContext()));
        ImageButton btnCerrar = view.findViewById(R.id.btnCerrar);
        txtTitle = view.findViewById(R.id.txtTitle);
        txtDescription = view.findViewById(R.id.txtDescription);
        imageView = view.findViewById(R.id.imageView);

        btnCerrar.setOnClickListener(btnCerrar_onClick);
        eventViewModel = new ViewModelProvider(requireActivity()).get(EventViewModel.class);
        Log.d("TAG", "fragment_picture:" + pictureId);
        loadPictures(pictureId);
    }

    private final View.OnClickListener btnCerrar_onClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d("TAG", "close fragment");
            eventViewModel.setCloseFragment(roomId);
        }
    };

    @Override
    public void onResultPicture(PictureEntity pictureEntity) {
        roomId = pictureEntity.roomId;
        FileRepository fileRepository=new FileRepository(getContext());
        Bitmap bitmap=fileRepository.getPicture(pictureEntity.link);
        setText(txtTitle, txtDescription,imageView, pictureEntity,bitmap);
    }

    private void setText(final TextView _txtTitle, final TextView _txtDescription, final ImageView _imageView, final PictureEntity _pictureEntity,final Bitmap _bitmap) {
        requireActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                _txtTitle.setText(_pictureEntity.title);
                _txtDescription.setText(_pictureEntity.description);
                _imageView.setImageBitmap(_bitmap);
            }
        });
    }

    private void loadPictures(int id) {
        Log.d("TAG", "------close fragment:" + id);
        ExecuteTask executeTask = new ExecuteTask();
        executeTask.asyncTask(() ->
                {
                    Log.d("TAG", "loadPictures------close fragment:" + id);
                    pictureFragmentListener.onResultPicture(repository.getPictureById(id));
                }
        );
    }
}