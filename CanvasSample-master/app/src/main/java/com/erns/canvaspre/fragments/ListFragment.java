package com.erns.canvaspre.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.erns.canvaspre.R;
import com.erns.canvaspre.adapters.PictureListAdapter;
import com.erns.canvaspre.model.database.AppDatabase;
import com.erns.canvaspre.model.database.FileRepository;
import com.erns.canvaspre.model.database.GalleryRepository;
import com.erns.canvaspre.model.ent.PictureEntity;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    private RecyclerView recyclerView;
    private PictureListAdapter adapter;
    private List<PictureEntity> pictureList = new ArrayList<>();
    private GalleryRepository repository;

    public static ListFragment newInstance() {
        return new ListFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerViewPictures);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        adapter = new PictureListAdapter(pictureList, picture -> {
            // Manejar el clic en un elemento
            Log.d("PictureListFragment", "Clicked picture: " + picture.title);
            // Puedes abrir un fragmento detallado o realizar otra acción
        });
        recyclerView.setAdapter(adapter);

        // Inicializar repository
        repository = new GalleryRepository(AppDatabase.getInstance(requireContext())); // Asegúrate de inicializarlo correctamente

        loadPictures();
    }


    private void loadPictures() {
        new Thread(() -> {
            try {
                List<PictureEntity> pictures = repository.getPictures(); // Recupera todas las pinturas
                Log.d("PictureListFragment", "Pictures loaded: " + pictures.size());
                requireActivity().runOnUiThread(() -> {
                    pictureList.clear(); // Limpia la lista actual (si es necesario)
                    pictureList.addAll(pictures); // Agrega las nuevas pinturas
                    adapter.notifyDataSetChanged(); // Notifica al adaptador que los datos han cambiado
                });
            } catch (Exception e) {
                Log.e("PictureListFragment", "Error loading pictures: ", e);
            }
        }).start();
    }

}
