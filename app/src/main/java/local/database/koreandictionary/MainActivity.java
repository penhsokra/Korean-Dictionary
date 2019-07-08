package local.database.koreandictionary;

import android.content.Intent;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.Toast;

import java.util.List;

import local.database.koreandictionary.adapter.WordAdapter;
import local.database.koreandictionary.data.WordDatabase;
import local.database.koreandictionary.data.entity.Word;

public class MainActivity extends AppCompatActivity implements WordAdapter.CurrentPosition{
    FloatingActionButton btnGotoadd;
    private RecyclerView wordBox;
    WordDatabase wordDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGotoadd = findViewById(R.id.btnGotoAdd);
        btnGotoadd.setOnClickListener(v->{
            Intent intent = new Intent(this,InsertActivity.class);
            startActivity(intent);
        });

        getWord();
    }
    private void getWord(){
        class GetWord extends AsyncTask<Void,Void, List<Word>>{

            @Override
            protected List<Word> doInBackground(Void... voids) {
                List<Word> wordList = WordDatabase.getInstance(getApplicationContext()).wordDao().getallWord();
                return wordList;

            }

            @Override
            protected void onPostExecute(List<Word> words) {
                super.onPostExecute(words);
                wordBox = findViewById(R.id.wordBox);
                wordBox.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
                WordAdapter wordAdapter = new WordAdapter(MainActivity.this,words);
                wordBox.setAdapter(wordAdapter);


            }
        }
    GetWord getWord = new GetWord();
    getWord.execute();
    }


    @Override
    public void CurrentPosition(int position) {

    }
}
