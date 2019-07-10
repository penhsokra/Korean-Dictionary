package local.database.koreandictionary;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import local.database.koreandictionary.adapter.WordAdapter;
import local.database.koreandictionary.data.WordDatabase;
import local.database.koreandictionary.data.entity.Word;

public class InsertActivity extends AppCompatActivity {
    private EditText iptKorean,iptEnglish,iptKhmer,iptExample;
    Button btnSave;
    WordDatabase wordDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        wordDatabase = WordDatabase.getInstance(this);
        iptKorean = findViewById(R.id.iptKorean);
        iptEnglish = findViewById(R.id.iptenglish);
        iptKhmer = findViewById(R.id.iptkhmer);
        iptExample = findViewById(R.id.iptexample);
        btnSave = findViewById(R.id.btnInsert);

        if (getIntent() !=null) {
            String getNewWord = getIntent().getExtras().getString("getWord");
            if (getNewWord !=null){
                iptKorean.setText(getNewWord);
            }
            btnSave.setOnClickListener(v -> {
                insertWord();
            });
        }
    }

    public void insertWord(){
        String korean = iptKorean.getText().toString().trim();
        final String english = iptEnglish.getText().toString().trim();
        final String khmer = iptKhmer.getText().toString().trim();
        final String example = iptExample.getText().toString().trim();

        if (korean.isEmpty()){
            iptKorean.setError("Korean word is required");
            iptKorean.requestFocus();
            return;
        }else if(english.isEmpty()){
            iptEnglish.setError("English word is required");
            iptEnglish.requestFocus();
            return;
        }else if(khmer.isEmpty()){
            iptKhmer.setError("Khmer word is required");
            iptKhmer.requestFocus();
            return;
        }else if(example.isEmpty()){
            iptExample.setError("English word is required");
            iptExample.requestFocus();
        }

        class InsertWord extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {

                List<Word> words = new ArrayList<>();
                Word word = new Word();
                //[Word{id=1, korean='1', english='1', khmer='1', example='1'},{id=2, korean='2', english='2', khmer='2', example='2'}];
                   for(int i=1;i<=50;i++){
                       try {
                           word.setId(i);
                           word.setKorean(""+i);
                           word.setEnglish(""+i);
                           word.setKhmer(""+i);
                           word.setExample(""+i);
                           words.add(word);
                       }catch (Exception e){
                           Log.e("1111",""+e.toString());
                       }
                       Log.e("2222",""+word);
                       wordDatabase.wordDao().insetAll(words);

                   }

                /*Word word = new Word();
               // [{"Id":1,"Name":"Sokra","Sex":"Male","Age":25,"Job":"Student"},{"Id":2,"Name":"Dara","Sex": "Male","Age":25,"Job":"Student"}]
                word.setId(0);
                word.setKorean(korean);
                word.setEnglish(english);
                word.setKhmer(khmer);
                word.setExample(example);
                wordDatabase.wordDao().insert(word);*/

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                //finish();
                //Intent intent = ;
                Toast.makeText(InsertActivity.this, "Saved", Toast.LENGTH_SHORT).show();
                iptKorean.requestFocus();
                iptKorean.setText("");
                iptEnglish.setText("");
                iptKhmer.setText("");
                iptExample.setText("");

            }

        }

        InsertWord save = new InsertWord();
        save.execute();
    }

    private Dialog createDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = LayoutInflater.from(this).inflate(R.layout.loading_screen_layout,null);
        builder.setView(view);
        return builder.create();
    }
}
