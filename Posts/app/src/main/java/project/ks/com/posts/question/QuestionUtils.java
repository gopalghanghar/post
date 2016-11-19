package project.ks.com.posts.question;

import android.util.Log;

import java.io.IOException;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import project.ks.com.posts.bean.Post;
import project.ks.com.posts.bean.Question;
import project.ks.com.posts.bean.QuestionList;
import project.ks.com.posts.bean.TagList;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.RetriveUserData;
import project.ks.com.posts.util.Utils;

/**
 * Created by Gopal on 13-Oct-16.
 */
public class QuestionUtils extends Utils{


    public static QuestionList getQuestionList(){

        Log.i("QuestionUtils","Method : getQuestionList --> Enter");

        HttpResponse httpResponse = null;
        String responseBody = "";
        QuestionList questionList = null;

        try {

            httpResponse = Utils.postRequest(AppConstant.QUESTION_LIST_MAPPING ,null);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            Log.i("responseBody ", "getQuestionList: responseBody---->"+responseBody);
            if(!Utils.isEmpty(responseBody)) {
                questionList = Utils.generateObjectFromJSON(responseBody,QuestionList.class);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("QuestionUtils","Method : getQuestionList --> Exit");
        return questionList;
    }

    /**
     * This method is responsible for add new question in <b>QStack</b> application.
     *
     * @param question
     * @return
     */
    public static Question postQuestion(Question question){

        Log.i("QuestionUtils","Method : postQuestion --> Enter");

        HttpResponse httpResponse = null;
        String responseBody = "";
        String requestData = new String(Utils.generateJSONFromObject(question));

        try {

            httpResponse = Utils.postRequest(AppConstant.QUESTION_ADD_MAPPING ,requestData);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            Log.i("responseBody ", "postQuestion: responseBody---->" + responseBody);
            if(!Utils.isEmpty(responseBody)) {

                question = Utils.generateObjectFromJSON(responseBody,Question.class);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("QuestionUtils","Method : postQuestion --> Exit");
        return question;
    }
}
