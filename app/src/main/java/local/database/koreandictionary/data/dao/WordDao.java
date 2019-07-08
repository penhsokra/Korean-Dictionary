package local.database.koreandictionary.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.util.Log;

import java.util.List;

import local.database.koreandictionary.data.entity.Word;

@Dao
public interface WordDao {
    @Query("SELECT * FROM Word")
    List<Word>getallWord();

    @Insert
    long insert(Word word);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long[] insetAll(List<Word> words);

    @Update
    void update(Word word);

    @Delete
    void delete(Word word);

}
