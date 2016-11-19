package project.ks.com.posts.answer;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.Answer;
import project.ks.com.posts.bean.Question;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.question.QuestionUtils;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

public class PostAnswerActivity extends AppCompatActivity {

    EditText mAnswerBody;
    Button mPostAnswerBtn;

    SharedPreferences settings ;
    Question question = null;
    String questionJson = null;
    User user = null;
    Answer answer = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_answer);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        settings = this.getSharedPreferences(AppConstant.LOGIN_PREFERENCES, MODE_PRIVATE);

        questionJson = getIntent().getStringExtra(AppConstant.QUESTION_JSON);
        question = Utils.generateObjectFromJSON(questionJson, Question.class);
        user = Utils.getUserData(settings.getString(AppConstant.USER_JSON, null));

        mAnswerBody = (EditText) findViewById(R.id.postAnswerBody);
        mPostAnswerBtn = (Button) findViewById(R.id.postQuestionBtn);

        mPostAnswerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAnswerBody.setError(null);

                if(Utils.isEmpty(mAnswerBody.getText().toString().trim())){
                    mAnswerBody.setError("Answer required");
                    mAnswerBody.requestFocus();

                } else {

                    answer = new Answer(question.getId(),mAnswerBody.getText().toString().trim(),Utils.getQuestionUser(user),AppConstant.DMY_HHMM_DATE_FORMAT.format(new Date()));
                    PostAnswerTask postAnswerTask = new PostAnswerTask();
                    postAnswerTask.execute();
                }
            }
        });

    }

    public class PostAnswerTask extends AsyncTask<Void, Void, Boolean> {

        public PostAnswerTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            answer = AnswerUtils.postAnswer(answer);
            if(answer != null){
                return true;
            }
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            Log.i("Answer", "onPostExecute:");
            if(success){

                Question question = new Question();
                question.setId(answer.getId());
                Intent intent = new Intent(getApplicationContext(),ViewQuestionActivity.class);
                intent.putExtra(AppConstant.QUESTION_JSON,questionJson);

                startActivity(intent);

            }else {

                Toast.makeText(getApplicationContext(), "Answer not added", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected void onCancelled() {

        }

    }


}
