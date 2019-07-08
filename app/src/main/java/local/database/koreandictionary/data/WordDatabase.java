package local.database.koreandictionary.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import local.database.koreandictionary.data.dao.WordDao;
import local.database.koreandictionary.data.entity.Word;

@Database(version = 1,entities = {Word.class})
public abstract class WordDatabase extends RoomDatabase {
    static final String DB_NAME="word_db";
    public abstract WordDao wordDao();
    private static WordDatabase wordDatabase;

    public static WordDatabase getInstance(Context context){
        if (wordDatabase == null) {
            wordDatabase = Room.databaseBuilder(context,WordDatabase.class,DB_NAME).allowMainThreadQueries().build();
        }
        return wordDatabase;
    }

}
