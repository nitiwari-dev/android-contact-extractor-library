package contacts.model.db;


import com.google.myjson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Jiobaseaccount model
 * <p>
 * Created by Nitesh on 21-01-2017.
 */

public class JioBaseAccountModel {

    @SerializedName("items")
    public List<JioAccountModel> jioAccountModel = new ArrayList<>();

    public static class JioAccountModel {

        @SerializedName("type")
        public String type;

        @SerializedName("name")
        public String name;

        public JioAccountModel(String type, String name) {
            this.type = type;
            this.name = name;
        }
    }
}
