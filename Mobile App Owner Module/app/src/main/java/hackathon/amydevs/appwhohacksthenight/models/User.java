package hackathon.amydevs.appwhohacksthenight.models;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
