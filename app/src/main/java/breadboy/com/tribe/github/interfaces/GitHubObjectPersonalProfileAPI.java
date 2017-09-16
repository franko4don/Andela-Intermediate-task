package breadboy.com.tribe.github.interfaces;

import breadboy.com.tribe.github.models.GitHubUserProfile;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;


/**
 * Created by FRANKCHUKY on 9/16/2017.
 */

public interface GitHubObjectPersonalProfileAPI {
    @GET("{user}")
    Call<GitHubUserProfile> getGitHubUserProfile(@Path("user") String user);
}
