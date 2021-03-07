package ir.sadjadtalakoob.tarebar_shop;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * created by STala
 */

public interface GetDataService {

    @GET("/504Course4.json")
    //@GET("/cd/0/get/BKK-EEuEm2L88-MPBAtrqEEWEBvSQbMEjMjdc8TfxdXq5iTDALz-DgK73nwlVun6UZ17vkuKFrmzhl6zzWqCz5Xx5Y0wS993nBObIeohOnGFUfA0P5BGuDYad3XTEE9XYMiUWpVQ78H6rFJXo7v2s6ZH/file?_download_id=362908543404791435812314022307673102149201142573831722615093811&_notify_domain=www.dropbox.com&dl=1")
    //@GET("/u/0/uc?id=1hzUtxjyzH7cwbbNLXwJXabcCjD6Bjp-f&export=download")
    //@GET("/sadjadtalakoob74/AndroidEducationApp/master/gradle/wrapper/course.json")
    //@GET("/sadjadtalakoob74/Torch_of_Knowledge/master/gradle/wrapper/course.json?token=AKUT7W3CDP57PKF3XLWIXADAINK4Y")
    Call<List<Course>> getAllCourses();
    /*@GET("/504Course2.json")
    Call<List<Vocabs>> getAllVocabs();*/

}
