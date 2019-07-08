package local.database.koreandictionary;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import local.database.koreandictionary.data.WordDatabase;
import local.database.koreandictionary.data.entity.Word;

public class UpdateActivity extends AppCompatActivity {
    private EditText iptKorean,iptEnglish,iptKhmer,iptExample;
    Button btnSave;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        iptKorean = findViewById(R.id.iptKorean);
        iptEnglish = findViewById(R.id.iptenglish);
        iptKhmer = findViewById(R.id.iptkhmer);
        iptExample = findViewById(R.id.iptexample);
        btnSave = findViewById(R.id.btnSave);

        if (getIntent() !=null) {
            final Word word = (Word) getIntent().getSerializableExtra("word");
            iptKorean.setText(word.getKorean());
            iptEnglish.setText(word.getEnglish());
            iptKhmer.setText(word.getKhmer());
            iptExample.setText(word.getExample());

            btnSave.setOnClickListener(v -> {
                saveWord(word);
            });
        }

    }

    public void saveWord(final Word word){
        final int id = word.getId();
        final String korean = iptKorean.getText().toString().trim();
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
        }

        class SaveWord extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                Word word = new Word();
                word.setId(id);
                word.setKorean(korean);
                word.setEnglish(english);
                word.setKhmer(khmer);
                word.setExample(example);
                WordDatabase.getInstance(getApplicationContext()).wordDao().update(word);
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                finish();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                Toast.makeText(UpdateActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        }

        SaveWord saveChange = new SaveWord();
        saveChange.execute();
    }
}
