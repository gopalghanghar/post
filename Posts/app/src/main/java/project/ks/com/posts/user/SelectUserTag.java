package project.ks.com.posts.user;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.SelectedTag;
import project.ks.com.posts.bean.Tag;
import project.ks.com.posts.bean.TagList;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

public class SelectUserTag extends AppCompatActivity {

    User user;

    ListView tagListView ;
    Button finishButton ;
    View mProgressView;
    View mUserTagView;

    List<SelectedTag> tags = new ArrayList<>();
    TagAdapter tagAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_tag);

        mProgressView = findViewById(R.id.tagLoadProgress);
        mUserTagView = findViewById(R.id.userTagView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String userJson = getIntent().getStringExtra(AppConstant.USER_JSON);
        user = Utils.generateObjectFromJSON(userJson,User.class);

        showProgress(true);

        TagListTask tagListTask = new TagListTask();
        tagListTask.execute((Void)null);

        //fillData();
        Log.d("Tag lISt", tags.toString());
        tagAdapter = new TagAdapter(this,tags);

        tagListView = (ListView) findViewById(R.id.tsListView);
        tagListView.setAdapter(tagAdapter);

        finishButton = (Button) findViewById(R.id.regFinish);

        finishButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArrayList<SelectedTag> userTags = new ArrayList<SelectedTag>();

                for (SelectedTag tag : tagAdapter.getBox()) {
                    if (tag.getIsChecked()){
                        userTags.add(tag);
                    }
                }
                user.setTags(UserUtils.getTagFromSelectedTag(userTags));

                showProgress(true);
                RegistrationTask registrationTask = new RegistrationTask(Utils.generateJSONFromObject(user));
                registrationTask.execute((Void) null);
            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // back key return parent page

    }

    private void fillData() {

        for(int i=0;i<=20;i++){

            SelectedTag tag = new SelectedTag();
            tag.setId("tagId"+i);
            tag.setName("TagName"+i );
            tag.setDescription("tagDescription "+i);
            tag.setIsChecked(Boolean.FALSE);
            tags.add(tag);

        }
    }

    public class TagListTask extends AsyncTask<Void, Void, Boolean> {

        TagListTask() {

        }

        @Override
        protected Boolean doInBackground(Void... params) {

            TagList tagList= UserUtils.getTags();

            if(tagList != null){

                tags.addAll(UserUtils.getSelectedTagFromTag(tagList.getTags()));

            }
            
            Log.d("Tag lISt", tags.toString());
            return false;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            tagAdapter.notifyDataSetChanged();
            showProgress(false);
        }

        @Override
        protected void onCancelled() {

        }
    }

    public class RegistrationTask extends AsyncTask<Void, Void, Boolean> {

        String userJson ;

        RegistrationTask(String userJson) {

            this.userJson = userJson;
        }

        @Override
        protected Boolean doInBackground(Void... params) {

            Boolean isRegister = false;
            Log.i("json", "user Json "+userJson );
            if(!Utils.isEmpty(userJson)){

                isRegister = UserUtils.doRegistration(userJson);

            }

            return isRegister;

        }

        @Override
        protected void onPostExecute(final Boolean success) {

            Intent intent ;
            if(success){

                intent = new Intent(SelectUserTag.this,LoginActivity.class);

            }else{

                intent = new Intent(SelectUserTag.this,Registration.class);

            }
            showProgress(false);
            startActivity(intent);
            finish();
        }

        @Override
        protected void onCancelled() {

            showProgress(false);

        }
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mUserTagView.setVisibility(show ? View.GONE : View.VISIBLE);
            mUserTagView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mUserTagView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mUserTagView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}
