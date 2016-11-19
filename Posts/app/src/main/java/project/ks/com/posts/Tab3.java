package project.ks.com.posts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.BoolRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import project.ks.com.posts.answer.ViewQuestionActivity;
import project.ks.com.posts.bean.Question;
import project.ks.com.posts.bean.Tag;
import project.ks.com.posts.bean.TagList;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.question.QuestionUtils;
import project.ks.com.posts.user.LoginActivity;
import project.ks.com.posts.user.Registration;
import project.ks.com.posts.user.UserUtils;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

/**
 * Created by Gopal on 30-Sep-16.
 */
public class Tab3 extends Fragment {

    EditText mQuestionTitle,mQuestionDetail;
    MultiAutoCompleteTextView mQuestionTags;
    Button mPostQuestionBtn;
    Button registrationButton,loginButton;

    List<String> tagList = new ArrayList<>();
    ArrayAdapter<String> tagListAdapter ;

    Question question = new Question();
    SharedPreferences settings ;
    User user = null;

    View v , loginRequiredView,askQuestionView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        settings = this.getActivity().getSharedPreferences(AppConstant.LOGIN_PREFERENCES, getContext().MODE_PRIVATE);

        Log.i("Tab3", "onCreateView:----> Enter  islOgin --->" + settings.getBoolean(AppConstant.IS_LOGIN,false));

        v = inflater.inflate(R.layout.tab_3, container, false);

        mQuestionTitle = (EditText) v.findViewById(R.id.askQuestionTitle);
        mQuestionDetail = (EditText) v.findViewById(R.id.askQuestionDetail);
        mPostQuestionBtn = (Button) v.findViewById(R.id.askPostQuestionBtn);
        mQuestionTags = (MultiAutoCompleteTextView) v.findViewById(R.id.askQuestionTags);

        loginRequiredView = v.findViewById(R.id.login_required_content);
        loginRequiredView.setVisibility(View.GONE);

        askQuestionView = v.findViewById(R.id.content_ask_question_layout);
        askQuestionView.setVisibility(View.GONE);

        user = Utils.getUserData(settings.getString(AppConstant.USER_JSON, null));

        TagListTask tagListTask = new TagListTask();
        tagListTask.execute((Void) null);

        mQuestionTags.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        mQuestionTags.setThreshold(1);

        mPostQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

        Boolean isCancel = false;
        View focusView = null;

        mQuestionTitle.setError(null);
        mQuestionTags.setError(null);


        String questionTitle, questionDetail, questionTags;
        question.setTitle(mQuestionTitle.getText().toString());
        question.setDetail(mQuestionDetail.getText().toString());
        questionTags = mQuestionTags.getText().toString();
        List<String> splitTags = Arrays.asList(questionTags.split(","));

        if (Utils.isEmpty(question.getTitle())) {
            mQuestionTitle.setError("Question title required");
            focusView = mQuestionTitle;
            isCancel = true;
        } else if (Utils.isEmpty(questionTags)) {
            mQuestionTags.setError("Tag required");
            focusView = mQuestionTags;
            isCancel = true;
        }

        if (isCancel) {

            focusView.requestFocus();

        } else {

            List<Tag> questionTagList = new ArrayList<Tag>();

            for (String splitTag : splitTags) {
                Tag tag = new Tag(splitTag.trim());
                questionTagList.add(tag);
            }
            question.setTags(questionTagList);

            question.setCreatedBy(Utils.getQuestionUser(user));

            PostQuestionTask postQuestionTask = new PostQuestionTask();
            postQuestionTask.execute();
        }

            }
        });

        registrationButton = (Button) v.findViewById(R.id.registration_required_btn);
        loginButton = (Button) v.findViewById(R.id.login_required_btn);

        registrationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), Registration.class);
                startActivity(intent);

            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                Log.i("tab3", "onClick: _----------> back from login : " + settings.getBoolean(AppConstant.IS_LOGIN,false));
            }
        });


        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.i("tab3", "onStart: ----->> Enter ");

        settings = this.getActivity().getSharedPreferences(AppConstant.LOGIN_PREFERENCES, getContext().MODE_PRIVATE);

        if(settings.getBoolean(AppConstant.IS_LOGIN,false)) {
            askQuestionView.setVisibility(View.VISIBLE);
            loginRequiredView.setVisibility(View.GONE);
        }else{
            loginRequiredView.setVisibility(View.VISIBLE);
            askQuestionView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i("tab3", "onResume: ------------->> Enter ");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Log.i("tab3", "onViewStateRestored: ------------->>> Enter ");
    }

    public class TagListTask extends AsyncTask<Void, Void, Boolean> {

        TagListTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Log.i("tab3", "TagListTask - Method : doInBackground: ----->> Enter ");

            TagList tags= UserUtils.getTags();

            if(tags != null && Utils.isListNotNull(tags.getTags())){

                for(Tag tag : tags.getTags()){
                    tagList.add(tag.getName());
                }

                return true;
            }

            Log.d("Tag lISt", tags.toString());
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            Log.i("tab3", "TagListTask - Method : onPostExecute: ----->> Enter ");
            if(success){

                Log.i("TAG", "onPostExecute: Tag list"+ tagList.toString());

                tagListAdapter=new ArrayAdapter<String>(getContext(), android.R.layout.simple_dropdown_item_1line,tagList);
                mQuestionTags.setAdapter(tagListAdapter);
                //showProgress(false);
            }

        }

        @Override
        protected void onCancelled() {

        }

    }


    public class PostQuestionTask extends AsyncTask<Void, Void, Boolean> {

        public PostQuestionTask() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            question = QuestionUtils.postQuestion(question);
            if(question != null){
                return true;
            }
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            Log.i("Question", "onPostExecute:");
            if(success){

                Intent intent = new Intent(getActivity().getApplicationContext(),ViewQuestionActivity.class);
                intent.putExtra(AppConstant.QUESTION_JSON,Utils.generateJSONFromObject(question));

                Toast.makeText(getActivity().getApplicationContext(), question.getId() + " is selected!", Toast.LENGTH_SHORT).show();

                startActivity(intent);

            }else {

                Toast.makeText(getActivity().getApplicationContext(), "Question not added", Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected void onCancelled() {

        }

    }

}
