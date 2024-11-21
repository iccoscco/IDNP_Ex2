package com.erns.canvaspre.fragments;

import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomAndVertex;

import java.util.List;

public interface GalleryFragmentListener {
    void onResultRoomVertex(List<RoomAndVertex> data);
    void onResultDoors(List<DoorEntity> data);
    void onResultPictures(List<PictureEntity> data);
}
