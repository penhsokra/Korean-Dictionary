package local.database.koreandictionary;

import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.icu.text.IDNA;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.ListPopupWindow;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import local.database.koreandictionary.adapter.WordAdapter;
import local.database.koreandictionary.data.WordDatabase;
import local.database.koreandictionary.data.entity.Word;

public class MainActivity extends AppCompatActivity implements WordAdapter.CurrentPosition{
    FloatingActionButton btnGotoadd;
    private RecyclerView wordBox;
    WordDatabase wordDatabase;
    WordAdapter wordAdapter;
    private List<Word> wordList = new ArrayList<>();
    Word words;
    private int position;
    private TextView notFound,getNotFound;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notFound = findViewById(R.id.notFound);
        getNotFound = findViewById(R.id.notFoundWord);
        btnGotoadd = findViewById(R.id.btnGotoAdd);
        notFound.setVisibility(View.GONE);
        btnGotoadd.setOnClickListener(v->{
            Intent intent = new Intent(this,InsertActivity.class);
            intent.putExtra("getWord",""+getNotFound.getText().toString());
            startActivity(intent);
        });

        wordDatabase = WordDatabase.getInstance(this);
        intiUI();
        getResult();

        Log.e("00000",""+wordList);
    }

    private void intiUI(){
        wordBox = findViewById(R.id.wordBox);
        wordBox.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        wordBox.setLayoutManager(new LinearLayoutManager(this));
        wordAdapter = new WordAdapter(this,wordList);
        wordBox.setAdapter(wordAdapter);

    }

    private void getResult(){
        List<Word> words = wordDatabase.wordDao().getallWord();
        wordAdapter.addMoreItem(words);
    }

    @Override
    public void CurrentPosition(int position,Word word) {
        this.position = position;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Do you want to delete this word ?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               try {
                   WordDatabase.getInstance(getApplicationContext()).wordDao().delete(word);
                   wordList.remove(word);
                   wordAdapter.notifyItemRemoved(position);
               }catch (Exception e) {
                   Log.e("000000",e.toString());
               }
             }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog ad = builder.create();
        ad.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu,menu);

        MenuItem search = menu.findItem(R.id.searchWord);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                doSearch(s);
                return true;
            }
        });
        return true;
    }

    List<Word> newWord = new ArrayList<>();
    private void doSearch(String s){
        newWord = wordDatabase.wordDao().getallWord();
        s = s.toLowerCase();
        if(s.isEmpty()){
            getNotFound.setText("");
            notFound.setVisibility(View.GONE);
            wordList.addAll(newWord);
        }
        wordList.clear();
        for (Word word : newWord){
            if (word.getKorean().contains(s)){
                getNotFound.setText("");
                notFound.setVisibility(View.GONE);
                wordList.add(word);
            }
        }
        if (wordList.size() == 0){
            getNotFound.setText(s.toString());
            notFound.setVisibility(View.VISIBLE);
            wordAdapter.notifyDataSetChanged();
        }
        wordAdapter.notifyDataSetChanged();
    }
}
