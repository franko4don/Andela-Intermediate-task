package breadboy.com.tribe.github;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import breadboy.com.tribe.github.interfaces.GitHubObjectPersonalProfileAPI;
import breadboy.com.tribe.github.models.GitHubUserProfile;
import breadboy.com.tribe.github.transforms.CircleTransform;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ProfileView extends AppCompatActivity {
    private String user, githubUrl;
    private ImageView imageview;
    private TextView numberOfRepository, followers, following, name, login, repos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_view);
        TextView star = (TextView)findViewById(R.id.star);
        TextView text = (TextView)findViewById(R.id.text);
        imageview = (ImageView)findViewById(R.id.avatar);
        repos = (TextView)findViewById(R.id.repo);
        numberOfRepository = (TextView)findViewById(R.id.number_of_repo);
        name = (TextView)findViewById(R.id.name);
        login = (TextView)findViewById(R.id.login);
        followers = (TextView)findViewById(R.id.followers);
        following = (TextView)findViewById(R.id.following);
        Button share = (Button)findViewById(R.id.share);
        ActionBar ab = getSupportActionBar();
        ab.setTitle("Personal Profile");
        ab.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#a4343a40")));
        Intent intent = getIntent();
        user = intent.getStringExtra(MainActivity.KEY);


        TextView circle = (TextView)findViewById(R.id.circle);
        circle.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        star.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        text.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        share.setTypeface(FontManager.getTypeface(getApplicationContext(), FontManager.FONTAWESOME));
        getListOfGitHubUsers();
//        Toast.makeText(getApplicationContext(),user,Toast.LENGTH_SHORT).show();
    }

    public void getListOfGitHubUsers() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/users/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubObjectPersonalProfileAPI service = retrofit.create(GitHubObjectPersonalProfileAPI.class);

        Call<GitHubUserProfile> call = service.getGitHubUserProfile(user);

        call.enqueue(new Callback<GitHubUserProfile>() {
            @Override
            public void onResponse(Response<GitHubUserProfile> response, Retrofit retrofit) {

                try {

                    setupProfile(response);

                    Log.d("onResponse","username :  " + response.body().getFollowersUrl());


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

    private void setupProfile(Response<GitHubUserProfile> response){
//        numberOfRepository.setText(response.body().getPublicRepos().toString());
        githubUrl = response.body().getHtmlUrl();
        numberOfRepository.setText( Html.fromHtml("<a href=\""+response.body().getHtmlUrl()+"\">Visit my Github</a>"));
        numberOfRepository. setMovementMethod(LinkMovementMethod.getInstance());
        login.setText(response.body().getLogin());
        name.setText(response.body().getName());
        followers.setText(response.body().getFollowers().toString());
        following.setText(response.body().getFollowing().toString());
        repos.setText(response.body().getPublicRepos().toString());
        Picasso.with(getApplicationContext()).load(response.body().getAvatarUrl()).transform(new CircleTransform()).into(imageview);
    }

    public void share(View view){
        String text = "Check out this awesome developer @"+login.getText().toString()+", "+githubUrl;
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
