
package com.artiomtaliaronak.ipr1ppo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class GameOver extends AppCompatActivity {

    private ListView listView;
    private ArrayAdapter<User> adapter;
    private ArrayList<User> users = new ArrayList<>();
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);

        listView = findViewById(R.id.listView);
        databaseHelper = new DatabaseHelper(this);

        adapter = new UsersAdapter(this);
        listView.setAdapter(adapter);

        users.addAll(databaseHelper.readData());
    }

    private class UsersAdapter extends ArrayAdapter<User>{

        public UsersAdapter(Context context) {
            super(context, android.R.layout.simple_list_item_1, users);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            User user = getItem(position);
            LayoutInflater layoutInflater = getLayoutInflater();
            View view = layoutInflater.inflate(R.layout.score, null);
            TextView textViewUsername = view.findViewById(R.id.textViewUsername);
            TextView textViewScore = view.findViewById(R.id.textViewScore);
            textViewUsername.setText(user.getUsername());
            textViewScore.setText(Integer.toString(user.getScore()));
            return view;
        }
    }

    public void restart(View view){
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
    }


}
