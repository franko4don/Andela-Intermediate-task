package breadboy.com.tribe.github;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Spinner;
import android.widget.Toolbar;

public class SearchActivity extends AppCompatActivity {
    Intent main;
    Spinner location,language;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        location = (Spinner)findViewById(R.id.location);
        language = (Spinner)findViewById(R.id.language);
        main = new Intent(this, MainActivity.class);
        ActionBar ab = getSupportActionBar();
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a4343a40")));

    }

    public void search(View view){
        main.putExtra("location", String.valueOf(location.getSelectedItem()));
        main.putExtra("language", String.valueOf(language.getSelectedItem()));
        startActivity(main);
    }
}
