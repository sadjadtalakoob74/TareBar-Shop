package ir.sadjadtalakoob.tarebar_shop;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * created by STala
 */

public interface GetDataService2 {

    @GET("/504Course4.json")
    //@GET("/u/0/uc?id=1hzUtxjyzH7cwbbNLXwJXabcCjD6Bjp-f&export=download")
    //@GET("/sadjadtalakoob74/AndroidEducationApp/master/gradle/wrapper/course.json")
    //@GET("/sadjadtalakoob74/Torch_of_Knowledge/master/gradle/wrapper/course.json?token=AKUT7W3CDP57PKF3XLWIXADAINK4Y")
    Call<List<Course>> getAllCourses();
    /*@GET("/504Course2.json")
    Call<List<Vocabs>> getAllVocabs();*/

}
