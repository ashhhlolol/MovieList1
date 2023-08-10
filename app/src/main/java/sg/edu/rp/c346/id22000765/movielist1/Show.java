package sg.edu.rp.c346.id22000765.movielist1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class Show extends AppCompatActivity {
    Button btnShowMovies;
    Spinner spinner;
    ListView lv;
    ArrayList<Movie> movieList;
    ArrayList<Movie> movieList2;
    CustomAdapter customAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        sg.edu.rp.c346.id22000765.movielist.DBHelper db = new sg.edu.rp.c346.id22000765.movielist.DBHelper(Show.this);
        movieList.clear();
        movieList.addAll(db.getMovies());
        db.close();
        customAdapter = new CustomAdapter(Show.this, R.layout.row, movieList);
        lv.setAdapter(customAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show);

        btnShowMovies = findViewById(R.id.btnMovieWithRating);
        lv = findViewById(R.id.listViewMovies);

        sg.edu.rp.c346.id22000765.movielist.DBHelper db = new sg.edu.rp.c346.id22000765.movielist.DBHelper(Show.this);
        movieList = db.getMovies();
        db.close();

        customAdapter = new CustomAdapter(this, R.layout.row, movieList);
        lv.setAdapter(customAdapter);

        btnShowMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = spinner.getSelectedItem().toString();
                movieList2 = new ArrayList<>();

                // filter movies based on selected rating
                for (int i = 0; i < movieList.size(); i++) {
                    if (movieList.get(i).getRating().equals(selectedItem) && selectedItem.equals("PG13")) {
                        movieList2.add(movieList.get(i));
                    }
                }

                customAdapter = new CustomAdapter(Show.this, R.layout.row, movieList2);
                lv.setAdapter(customAdapter);
            }
        });


        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie;
                if (movieList2 != null && movieList2.size() > position) {
                    movie = movieList2.get(position);
                } else {
                    movie = movieList.get(position);
                }
                Intent intent = new Intent(Show.this, EditActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
    }
}