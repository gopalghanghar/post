package project.ks.com.posts.answer;

import android.util.Log;

import java.io.IOException;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import project.ks.com.posts.bean.Answer;
import project.ks.com.posts.bean.Post;
import project.ks.com.posts.bean.Question;
import project.ks.com.posts.bean.QuestionList;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

/**
 * Created by Gopal on 08-Nov-16.
 */
public class AnswerUtils {

    public static Post getPostDetail(Question question){

        Log.i("AnswerUtils","Method : getPostDetail --> Enter");

        HttpResponse httpResponse = null;
        String responseBody = "";
        Post post = null;

        String requestData = Utils.generateJSONFromObject(question);

        try {

            httpResponse = Utils.postRequest(AppConstant.POST_MAPPING + "/" + question.getId() ,requestData);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            Log.i("AnswerUtils","Method : getPostDetail : responseBody---->"+responseBody);
            if(!Utils.isEmpty(responseBody)) {
                post = Utils.generateObjectFromJSON(responseBody,Post.class);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("AnswerUtils","Method : getPostDetail --> Exit");
        return post;
    }

    public static Answer postAnswer(Answer answer){

        Log.i("AnswerUtils","Method : postAnswer --> Enter");

        HttpResponse httpResponse = null;
        String responseBody = "";

        String requestData = Utils.generateJSONFromObject(answer);

        try {

            httpResponse = Utils.postRequest(AppConstant.ANSWER_ADD_MAPPING, requestData);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            Log.i("AnswerUtils","Method : postAnswer : responseBody---->"+responseBody);
            if(!Utils.isEmpty(responseBody)) {
                answer = Utils.generateObjectFromJSON(responseBody,Answer.class);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("AnswerUtils","Method : postAnswer --> Exit");
        return answer;
    }
}
