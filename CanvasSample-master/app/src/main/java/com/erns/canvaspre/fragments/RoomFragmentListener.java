package com.erns.canvaspre.fragments;

import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomAndVertex;

import java.util.List;

public interface RoomFragmentListener {
    void onResultRoomVertex(RoomAndVertex data);
    void onResultPicture(List<PictureEntity>  data);
    void onResultDoors(List<DoorEntity> data);
}
