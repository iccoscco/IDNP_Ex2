package com.erns.canvaspre.model.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.erns.canvaspre.model.ent.RoomAndVertex;
import com.erns.canvaspre.model.ent.RoomEntity;
import com.erns.canvaspre.model.ent.VertexEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Dao
public interface RoomDao {
    @Insert
    void insert(List<RoomEntity> roomEntityList);

    @Query("select * from rooms")
    List<RoomEntity> getAll();

    @Query("select * from rooms where roomId=:roomId")
    List<RoomEntity> getByRoomId(int roomId);

    @Query("select * from rooms")
    List<RoomAndVertex> getRoomWithVertex();

    @Query("select * from rooms where roomId=:roomId")
   RoomAndVertex getRoomWithVertexByRoomId(int roomId);

//    @Query("select * from rooms inner join vertex on rooms.roomId=vertex.roomId")
//    Map<RoomEntity, List<VertexEntity>> getRoomWithVertex();
}
