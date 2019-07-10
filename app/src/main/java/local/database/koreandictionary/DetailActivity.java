package local.database.koreandictionary;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toolbar;

import local.database.koreandictionary.data.entity.Word;

public class DetailActivity extends AppCompatActivity {
    TextView korean,english,khmer,example;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        korean = findViewById(R.id.dKorean);
        english = findViewById(R.id.dEnglish);
        khmer = findViewById(R.id.dKhmer);
        example = findViewById(R.id.dExample);
        if (getIntent() !=null) {
            final Word word = (Word) getIntent().getSerializableExtra("word");
            korean.setText(word.getKorean());
            english.setText(word.getEnglish());
            khmer.setText(word.getKhmer());
            example.setText(word.getExample());
        }
    }
}
