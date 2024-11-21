package com.erns.canvaspre.model.database;

import android.util.Log;

import androidx.lifecycle.LiveData;

import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomAndVertex;
import com.erns.canvaspre.model.ent.RoomEntity;
import com.erns.canvaspre.model.ent.VertexEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryRepository {
    private final AppDatabase appDatabase;

    public GalleryRepository(AppDatabase appDatabase) {
        this.appDatabase = appDatabase;
    }



    public List<PictureEntity> getPictures() {
        return appDatabase.pictureDao().getAll();
    }


    public List<VertexEntity> getVertexes() {
        return appDatabase.vertexDao().getAll();
    }

    public List<RoomEntity> getRoomById(int roomId) {
        return appDatabase.roomVertexDao().getByRoomId(roomId);
    }

    public List<RoomAndVertex> getRoomWithVertexes() {
        return appDatabase.roomVertexDao().getRoomWithVertex();
    }

    public RoomAndVertex getRoomWithVertexByRoomId(int roomId) {
        return appDatabase.roomVertexDao().getRoomWithVertexByRoomId(roomId);
    }

    public List<DoorEntity> getDoors() {
        return appDatabase.doorDao().getAll();
    }

    public List<PictureEntity> getPicturesByRoomId(int roomId) {
        return appDatabase.pictureDao().getPicturesByRoomId(roomId);
    }
    public PictureEntity getPictureById(int pictureId) {
        Log.d("TAG","GalleryRepository pictureId:"+pictureId);
        return appDatabase.pictureDao().getPictureById(pictureId);
    }

    public void addPictures(List<PictureEntity> pictureEntityList) {
        appDatabase.pictureDao().insert(pictureEntityList);
    }
    public void addDoors(List<DoorEntity> doorEntityList) {
        appDatabase.doorDao().insert(doorEntityList);
    }

    public void addRooms(List<RoomEntity> roomEntityList) {
        appDatabase.roomVertexDao().insert(roomEntityList);
    }

    public void addVertexes(List<VertexEntity> vertexEntityList) {
        appDatabase.vertexDao().insert(vertexEntityList);
    }
}
