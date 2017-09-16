package breadboy.com.tribe.github.interfaces;

import java.util.List;

import breadboy.com.tribe.github.models.GitHubUser;
import retrofit.Call;
import retrofit.http.GET;

/**
 * Created by FRANKCHUKY on 9/14/2017.
 */

public interface GitHubArrayPersonalAPI {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
    */
    @GET("api/RetrofitAndroidArrayResponse")
    Call<List<GitHubUser>> getGitHubDetails();
}
