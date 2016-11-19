package project.ks.com.posts.answer;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.Answer;
import project.ks.com.posts.bean.Post;
import project.ks.com.posts.bean.Question;
import project.ks.com.posts.bean.QuestionList;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.question.DividerItemDecoration;
import project.ks.com.posts.question.QuestionUtils;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

public class ViewQuestionActivity extends AppCompatActivity {

    TextView questionTitle, questionDetail, questionDate, questionUsername;
    ImageView questionUserImage;
    RecyclerView answer_recRecyclerView ;
    AnswerAdapter mAdapter;
    Post post = new Post();
    List<Answer> answerList = new ArrayList<>();
    Question question = new Question();
    View mProcessView, mPostDetailView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_question);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final String questionJson = getIntent().getStringExtra(AppConstant.QUESTION_JSON);
        question = Utils.generateObjectFromJSON(questionJson, Question.class);

        questionTitle = (TextView) findViewById(R.id.viewQuestionTitle);
        questionDetail = (TextView) findViewById(R.id.viewQuestionDetail);
        questionDate = (TextView) findViewById(R.id.viewQuestionDate);
        questionUsername = (TextView) findViewById(R.id.viewQuestionUsername);
        questionUserImage = (ImageView) findViewById(R.id.viewQuestionUserImage);

        Log.i("3", "question data set");

        answer_recRecyclerView = (RecyclerView) findViewById(R.id.answer_recycler_view);
        answer_recRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        answer_recRecyclerView.setNestedScrollingEnabled(false);

        PostDataTask postDataTask = new PostDataTask(question);
        postDataTask.execute();

        Log.i("Question ", question.toString());

        mAdapter = new AnswerAdapter(answerList);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        answer_recRecyclerView.setLayoutManager(mLayoutManager);
        answer_recRecyclerView.setItemAnimator(new DefaultItemAnimator());
        answer_recRecyclerView.setAdapter(mAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/

                Intent intent = new Intent(view.getContext(),PostAnswerActivity.class);
                intent.putExtra(AppConstant.QUESTION_JSON,questionJson);
                startActivity(intent);
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        answer_recRecyclerView.addOnItemTouchListener(new RecyclerTouchListener(getApplicationContext(), answer_recRecyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Answer answer = answerList.get(position);
                Toast.makeText(getApplicationContext(), answer.getAnswer() + " is selected!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLongClick(View view, int position) {
                Answer answer = answerList.get(position);
                Toast.makeText(getApplicationContext(), answer.getAnswer() + " is long pressed!", Toast.LENGTH_SHORT).show();

            }
        }));

    }

    public void setContent(){

        questionTitle.setText(question.getTitle());
        questionDetail.setText(question.getDetail());
        questionDate.setText(question.getCraetedDate());
        questionUsername.setText(question.getCreatedBy().getFirstName());

        /*        int imageId = getResources().getIdentifier(question.getCreatedBy().getProfile(), "mipmap", AppConstant.PACKAGE_NAME);

        if(imageId > 0){
            questionUserImage.setImageResource(imageId);
        }*/
    }


    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private ViewQuestionActivity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final ViewQuestionActivity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }

        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

    public class PostDataTask extends AsyncTask<String, Void, Boolean> {

        Question questionDetail ;

        public PostDataTask() {
        }

        public PostDataTask(Question question) {
            this.questionDetail = question;
        }

        @Override
        protected Boolean doInBackground(String... params) {

            Post post = AnswerUtils.getPostDetail(questionDetail);

            if(post != null){

                question = post.getQuestion();

                if(!post.getAnswers().isEmpty()){
                    answerList.addAll(post.getAnswers());
                    Log.i("answerLIstSIXE", "doInBackground: answer list size :"+answerList.size());
                    return true;
                }
            }

            return false;

        }

        @Override
        protected void onPostExecute(Boolean result) {

            Log.d("Question Data", "onPostExecute: ");
            //showProgress(false);
            setContent();
            mAdapter.notifyDataSetChanged();

        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mPostDetailView.setVisibility(show ? View.GONE : View.VISIBLE);
            mPostDetailView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mPostDetailView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProcessView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProcessView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProcessView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mProcessView.setVisibility(show ? View.VISIBLE : View.GONE);
            mPostDetailView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
