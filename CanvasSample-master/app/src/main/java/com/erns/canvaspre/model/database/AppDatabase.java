package com.erns.canvaspre.model.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.erns.canvaspre.model.dao.DoorDao;
import com.erns.canvaspre.model.dao.PictureDao;
import com.erns.canvaspre.model.dao.RoomDao;
import com.erns.canvaspre.model.dao.VertexDao;
import com.erns.canvaspre.model.ent.DoorEntity;
import com.erns.canvaspre.model.ent.PictureEntity;
import com.erns.canvaspre.model.ent.RoomEntity;
import com.erns.canvaspre.model.ent.VertexEntity;

@Database(version = 11,
        entities = {
                DoorEntity.class,
                PictureEntity.class,
                RoomEntity.class,
                VertexEntity.class
        }
)
public abstract class AppDatabase extends RoomDatabase {
    public abstract DoorDao doorDao();
    public abstract PictureDao pictureDao();
    public abstract RoomDao roomVertexDao();
    public abstract VertexDao vertexDao();

    private static AppDatabase INSTANCE = null;

    public static AppDatabase getInstance(Context context) {
        synchronized (context) {
            AppDatabase instance = INSTANCE;

            if (instance == null) {
                instance = Room.databaseBuilder(
                                context,
                                AppDatabase.class,
                                "database-name"
                        ).fallbackToDestructiveMigration()
                        .build();

                INSTANCE = instance;

            }

            return instance;
        }
    }
}