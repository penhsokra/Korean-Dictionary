package local.database.koreandictionary.data.entity;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Word implements Serializable{
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String korean;
    private String english;
    private String khmer;
    private String example;
    public Word(){}
    public Word(int id, String korean, String english, String khmer, String example) {
        this.id = id;
        this.korean = korean;
        this.english = english;
        this.khmer = khmer;
        this.example = example;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKorean() {
        return korean;
    }

    public void setKorean(String korean) {
        this.korean = korean;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getKhmer() {
        return khmer;
    }

    public void setKhmer(String khmer) {
        this.khmer = khmer;
    }

    public String getExample() {
        return example;
    }

    public void setExample(String example) {
        this.example = example;
    }

    @Override
    public String toString() {
        return "Word{" +
                "id=" + id +
                ", korean='" + korean + '\'' +
                ", english='" + english + '\'' +
                ", khmer='" + khmer + '\'' +
                ", example='" + example + '\'' +
                '}';
    }
}
