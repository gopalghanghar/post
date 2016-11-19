package project.ks.com.posts.util;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import project.ks.com.posts.bean.QuestionList;
import project.ks.com.posts.question.QuestionUtils;

/**
 * Created by Gopal on 04-Oct-16.
 */
public class RetriveUserData extends AsyncTask<String, Void, HttpResponse> {

    @Override
    protected HttpResponse doInBackground(String... urls) {

        Log.i("Question data","Method : doInBackground --> Enter");

        String responseBody = "";
        QuestionList questionList = null;
        HttpResponse httpResponse = null;

        try {
            httpResponse = Utils.postRequest(AppConstant.QUESTION_MODULE+AppConstant.LIST,null);

            Log.i("response status ", "status : "+httpResponse.getStatusLine().getStatusCode());

            if(httpResponse.getStatusLine().getStatusCode()>=200 && httpResponse.getStatusLine().getStatusCode()<300){

                responseBody = EntityUtils.toString(httpResponse.getEntity());

                QuestionList list = Utils.generateObjectFromJSON(responseBody,QuestionList.class);

                Log.i("response", "postRequest  : "+list.toString());

            } else {

                responseBody = EntityUtils.toString(httpResponse.getEntity());

                Log.i("response not OK", "postRequest  : "+responseBody);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.i("RetriveUserData","Method : doInBackground --> Exit");
        return httpResponse;
    }
}
