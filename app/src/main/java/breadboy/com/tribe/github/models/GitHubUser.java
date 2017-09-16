package breadboy.com.tribe.github.models;

import java.util.List;

public class GitHubUser
{

    private int total_count;
    private List<GitHubUsersDetails> items;

    public int getTotal_count() {
        return total_count;
    }

    public void setTotal_count(int total_count) {
        this.total_count = total_count;
    }

    public List<GitHubUsersDetails> getItem() {
        return items;
    }

    public void setItem(List<GitHubUsersDetails> item) {
        this.items = item;
    }
}

