package breadboy.com.tribe.github;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.picasso.Picasso;

import breadboy.com.tribe.github.interfaces.GitHubObjectLocationAPI;
import breadboy.com.tribe.github.models.GitHubUser;
import breadboy.com.tribe.github.transforms.CircleTransform;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{
    int img = R.drawable.avatar;
    String avatar[];
    long id[];
    String login[];
    public static final String KEY = "login";
    ListView listView;
    private String location, language;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        customAdapter = new CustomAdapter();
        listView = (ListView)findViewById(R.id.listview);
        listView.setOnItemClickListener(this);
        Intent intent = getIntent();
        location = intent.getStringExtra("location");
        language = intent.getStringExtra("language");
        ActionBar ab = getSupportActionBar();
        View abview = getLayoutInflater().inflate(R.layout.abs_layout,null);
        ActionBar.LayoutParams params = new ActionBar.LayoutParams(//Center the textview in the ActionBar !
                ActionBar.LayoutParams.WRAP_CONTENT,
                ActionBar.LayoutParams.MATCH_PARENT,
                Gravity.CENTER_HORIZONTAL);
        TextView title = (TextView)abview.findViewById(R.id.mytitle);
        title.setText(location+" "+language+" developers");
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a4343a40")));
        ab.setCustomView(abview, params);
        ab.setDisplayShowCustomEnabled(true);
        ab.setDisplayShowTitleEnabled(false);



        Toast.makeText(getApplicationContext(),"Location:"+location+" language:"+language,Toast.LENGTH_SHORT).show();
        getListOfGitHubUsers("location:"+location+"+language:"+language);
    }

    public void getListOfGitHubUsers(String location) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/search/")

                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubObjectLocationAPI service = retrofit.create(GitHubObjectLocationAPI.class);

        Call<GitHubUser> call = service.getGitHubUserDetails(location);

        call.enqueue(new Callback<GitHubUser>() {
            @Override
            public void onResponse(Response<GitHubUser> response, Retrofit retrofit) {

                try {

                        Log.e("Errors ",String.valueOf(response.raw()));
                        int length = response.body().getItem().size();
                        avatar = new String[length];
                        id = new long[length];
                        login = new String[length];

                        for (int i = 0; i < length; i++){
                            avatar[i] = response.body().getItem().get(i).getAvatarUrl();
                            id[i] = response.body().getItem().get(i).getId();
                            login[i] = response.body().getItem().get(i).getLogin();
                            Log.e("onResponse","login :  " + login[i] + "id: "+id[i]);
                        }
                    listView.setAdapter(customAdapter);


                } catch (Exception e) {
                    Log.e("onResponse", "There is an error  "+ e.getMessage());
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Throwable t) {
                Log.d("onFailure", t.toString());
            }
        });
    }

    class CustomAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return id.length;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            convertView = getLayoutInflater().inflate(R.layout.customactivity,null);
            ImageView imageview = (ImageView)convertView.findViewById(R.id.profilepix);
            TextView text = (TextView)convertView.findViewById(R.id.github);
            TextView chevron = (TextView)convertView.findViewById(R.id.view);
            TextView username = (TextView)convertView.findViewById(R.id.login);
            TextView repo_id = (TextView)convertView.findViewById(R.id.repo_id);
            username.setText(login[position]);
            repo_id.setText(Long.toString(id[position]));
            Picasso.with(getApplicationContext()).load(avatar[position]).transform(new CircleTransform()).into(imageview);
            text.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
            chevron.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
            return convertView;
        }

    }
    @Override
    public void onItemClick(AdapterView<?> parent, View convertView, int position, long arg3) {
        TextView login = (TextView)convertView.findViewById(R.id.login);
        Intent intent = new Intent(MainActivity.this, ProfileView.class);
        intent.putExtra(KEY, login.getText().toString());
        startActivity(intent);
//        Toast.makeText(getApplicationContext(), "You clicked on position : " + login.getText().toString() + " and id : " + arg3, Toast.LENGTH_LONG).show();
    }


}
