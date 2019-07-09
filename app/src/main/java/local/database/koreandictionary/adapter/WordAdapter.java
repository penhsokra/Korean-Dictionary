package local.database.koreandictionary.adapter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import local.database.koreandictionary.DetailActivity;
import local.database.koreandictionary.R;
import local.database.koreandictionary.UpdateActivity;
import local.database.koreandictionary.data.entity.Word;

public class WordAdapter extends RecyclerView.Adapter<WordAdapter.ViewHolder> {
    private AppCompatActivity context;
    private List<Word> wordList;
    private CurrentPosition currentPosition;
    private static final int EDIT_REQUEST_CODE=111;

    public WordAdapter(AppCompatActivity context, List<Word> wordList) {
        this.context = context;
        this.wordList = wordList;
        this.currentPosition = (CurrentPosition) context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.word_list_layout,viewGroup,false);
        return new WordAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        PopupMenu popupMenu = new PopupMenu(context,viewHolder.itemView);
        popupMenu.getMenuInflater().inflate(R.menu.action_menu,popupMenu.getMenu());

        Word word = wordList.get(position);
        viewHolder.txtKorean.setText(word.getKorean());
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {

            @Override
            public boolean onLongClick(View v) {
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()){
                            case R.id.btnDelete:
                                currentPosition.CurrentPosition(viewHolder.getAdapterPosition(),word);
                                return true;
                            case R.id.btnEdit:
                                Word word = wordList.get(viewHolder.getAdapterPosition());
                                Intent intent = new Intent(viewHolder.itemView.getContext(), UpdateActivity.class);
                                intent.putExtra("word",word);
                                context.startActivity(intent);
                                return true;
                            default:return false;
                        }
                    }
                });
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return wordList.size();
    }

     class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView txtKorean;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtKorean = itemView.findViewById(R.id.txtKorean);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            Word word = wordList.get(getAdapterPosition());
            Intent intent = new Intent(itemView.getContext(), DetailActivity.class);
            intent.putExtra("word",word);
            context.startActivity(intent);
        }
    }

    public static interface CurrentPosition{
        void CurrentPosition(int position,Word word);
    }

    public void addMoreItem(List<Word> books){
        int previousSize = getItemCount();
        this.wordList.addAll(books);
        notifyItemRangeInserted(previousSize-1,this.wordList.size());
    }
}
