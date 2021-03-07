package ir.sadjadtalakoob.tarebar_shop;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by praka on 12/24/2017.
 */

public class RetrofitClientInstance {

    private static Retrofit retrofit;
    //private static final String BASE_URL = "https://drive.google.com";
    //private static final String BASE_URL = "https://raw.githubusercontent.com/";
    private static final String BASE_URL = "https://torchofknowledge.ir";
    //private static final String BASE_URL = "https://uceedf6ae9fd70049fe22753b83e.dl.dropboxusercontent.com";

    public static Retrofit getRetrofitInstance() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
