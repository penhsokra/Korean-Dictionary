package local.database.koreandictionary;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

import local.database.koreandictionary.adapter.WordAdapter;
import local.database.koreandictionary.data.WordDatabase;
import local.database.koreandictionary.data.entity.Word;

public class InsertActivity extends AppCompatActivity {
    private EditText iptKorean,iptEnglish,iptKhmer,iptExample;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        iptKorean = findViewById(R.id.iptKorean);
        iptEnglish = findViewById(R.id.iptenglish);
        iptKhmer = findViewById(R.id.iptkhmer);
        iptExample = findViewById(R.id.iptexample);
        btnSave = findViewById(R.id.btnInsert);

        btnSave.setOnClickListener(v->{
            insertWord();
        });
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
                Word word = new Word();
                word.setId(0);
                word.setKorean(korean);
                word.setEnglish(english);
                word.setKhmer(khmer);
                word.setExample(example);

                WordDatabase.getInstance(getApplicationContext()).wordDao().insert(word);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(InsertActivity.this, "Saved"+example, Toast.LENGTH_SHORT).show();
            }
        }

        InsertWord save = new InsertWord();
        save.execute();
    }
}
