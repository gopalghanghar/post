package project.ks.com.posts;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import project.ks.com.posts.answer.ViewQuestionActivity;
import project.ks.com.posts.bean.Question;
import project.ks.com.posts.bean.QuestionList;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.question.DividerItemDecoration;
import project.ks.com.posts.question.QuestionAdapter;
import project.ks.com.posts.question.QuestionUtils;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.RetriveUserData;
import project.ks.com.posts.util.Utils;

/**
 * Created by Gopal on 29-Sep-16.
 */
public class Tab1 extends Fragment {

    private RecyclerView recyclerView ;
    private List<Question> questionList = new ArrayList<>();
    private QuestionAdapter mAdapter;
    private View mQuestionContentLayout ;
    private View mQueLoadProgress;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.tab_1,container,false);

        recyclerView = (RecyclerView) v.findViewById(R.id.recycler_view);
        recyclerView.addItemDecoration(new DividerItemDecoration(v.getContext(), LinearLayoutManager.VERTICAL));

        mQueLoadProgress = v.findViewById(R.id.queLoadProgress);
        mQuestionContentLayout = v.findViewById(R.id.question_content_layout);

        showProgress(true);

        QuestionDataTask questionDataTask = new QuestionDataTask();
        questionDataTask.execute();

        Log.d("Question Data", "onCreateView: size "+questionList.size());

        mAdapter = new QuestionAdapter(questionList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {

                Question question = questionList.get(position);

                Intent intent = new Intent(getActivity().getApplicationContext(),ViewQuestionActivity.class);
                intent.putExtra(AppConstant.QUESTION_JSON,Utils.generateJSONFromObject(question));

                Toast.makeText(getActivity().getApplicationContext(), question.getId() + " is selected!", Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }

            @Override
            public void onLongClick(View view, int position) {

                Question question = questionList.get(position);
                Toast.makeText(getActivity().getApplicationContext(), question.getTitle() + " is long pressed!", Toast.LENGTH_SHORT).show();

            }
        }));

        return v;
    }

    public interface ClickListener {
        void onClick(View view, int position);

        void onLongClick(View view, int position);
    }

    public class QuestionDataTask extends AsyncTask<String, Void, Boolean> {

        public QuestionDataTask() {
        }


        @Override
        protected Boolean doInBackground(String... params) {

            QuestionList questions = QuestionUtils.getQuestionList();

            if(questions != null){

                if(Utils.isListNotNull(questions.getQuestions())){
                    questionList.addAll(questions.getQuestions());
                    return true;
                }
            }

            return false;

        }

        @Override
        protected void onPostExecute(Boolean result) {

            Log.d("Question Data", "onPostExecute: ");

            if(result){
                showProgress(false);
                mAdapter.notifyDataSetChanged();
            }

        }
    }

    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener {

        private GestureDetector gestureDetector;
        private Tab1.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Tab1.ClickListener clickListener) {
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

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mQuestionContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            mQuestionContentLayout.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mQuestionContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mQueLoadProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mQueLoadProgress.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mQueLoadProgress.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {

            mQueLoadProgress.setVisibility(show ? View.VISIBLE : View.GONE);
            mQuestionContentLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }
}
