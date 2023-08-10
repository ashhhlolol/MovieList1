package sg.edu.rp.c346.id22000765.movielist1;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    Button btnShowMovies;
    Spinner spinnerFilter;
    ListView lv;
    ArrayList<Movie> movieList;
    ArrayList<Movie> filteredMovieList;
    CustomAdapter customAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        sg.edu.rp.c346.id22000765.movielist.DBHelper db = new sg.edu.rp.c346.id22000765.movielist.DBHelper(DisplayActivity.this);
        movieList.clear();
        movieList.addAll(db.getMovies());
        db.close();
        customAdapter.notifyDataSetChanged(); // Notify the adapter that data has changed
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        btnShowMovies = findViewById(R.id.btnMovieWithRating);
        spinnerFilter = findViewById(R.id.spinnerRatingFilter);
        lv = findViewById(R.id.listViewMovies);

        sg.edu.rp.c346.id22000765.movielist.DBHelper db = new sg.edu.rp.c346.id22000765.movielist.DBHelper(DisplayActivity.this);
        movieList = db.getMovies();
        db.close();

        customAdapter = new CustomAdapter(this, R.layout.row, movieList);
        lv.setAdapter(customAdapter);

        btnShowMovies.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String selectedItem = spinnerFilter.getSelectedItem().toString();
                filteredMovieList = new ArrayList<>();
                for (Movie movie : movieList) {
                    if (movie.getRating().equals(selectedItem)) {
                        filteredMovieList.add(movie);
                    }
                }
                customAdapter = new CustomAdapter(DisplayActivity.this, R.layout.row, filteredMovieList);
                lv.setAdapter(customAdapter);
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie;
                if (filteredMovieList != null && filteredMovieList.size() > position) {
                    movie = filteredMovieList.get(position);
                } else {
                    movie = movieList.get(position);
                }
                Intent intent = new Intent(DisplayActivity.this, EditActivity.class);
                intent.putExtra("movie", movie);
                startActivity(intent);
            }
        });
    }
}
