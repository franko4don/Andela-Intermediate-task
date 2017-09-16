package breadboy.com.tribe.github.interfaces;

/**
 * Created by FRANKCHUKY on 9/14/2017.
 */

import breadboy.com.tribe.github.models.GitHubUser;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Query;

public interface GitHubObjectLocationAPI {

    /*
     * Retrofit get annotation with our URL
     * And our method that will return us details of student.
    */
    @GET("users")
    Call<GitHubUser> getGitHubUserDetails(@Query(encoded = true, value = "q") String location);
}