package project.ks.com.posts;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import project.ks.com.posts.bean.User;
import project.ks.com.posts.user.LoginActivity;
import project.ks.com.posts.user.Registration;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

/**
 * Created by Gopal on 30-Sep-16.
 */
public class Tab4 extends Fragment {

    SharedPreferences settings ;
    User user;

    TextView username, email, questions, answers;
    ImageView userProfile;
    Button registrationButton,loginButton;

    View v,userProfileLayout,loginRequiredLayout;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        Log.i("Tab4", "onCreateView: --> Enter");

        v =inflater.inflate(R.layout.tab_4,container,false);

        userProfile = (ImageView) v.findViewById(R.id.userprofilePic);
        username = (TextView) v.findViewById(R.id.userprofileUsername);
        email = (TextView) v.findViewById(R.id.userprofileEmail);
        questions = (TextView) v.findViewById(R.id.userprofileQuestions);
        answers = (TextView) v.findViewById(R.id.userprofileAnswers);

        loginRequiredLayout = v.findViewById(R.id.login_required_content);
        userProfileLayout = v.findViewById(R.id.user_profile_content_layout);

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

        settings = this.getActivity().getSharedPreferences(AppConstant.LOGIN_PREFERENCES,this.getActivity().MODE_PRIVATE);

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.i("Tab4", "onCreate: --> Enter");

    }

    @Override
    public void onResume() {
        super.onResume();

        Log.i("Tab4", "onResume: --> Enter");

        settings = this.getActivity().getSharedPreferences(AppConstant.LOGIN_PREFERENCES, getContext().MODE_PRIVATE);

        if(settings.getBoolean(AppConstant.IS_LOGIN,false)){

            user = Utils.getUserData(settings.getString(AppConstant.USER_JSON,null));

            if(user != null){

                username.setText(user.getFirstName() + " " + user.getLastName() );
                email.setText(user.getEmail());
                questions.setText("20");
                answers.setText("90");

                int imageId = getResources().getIdentifier(user.getProfile(), "mipmap", AppConstant.PACKAGE_NAME);

                if(imageId > 0){
                    userProfile.setImageResource(imageId);
                }

            }

            userProfileLayout.setVisibility(View.VISIBLE);
            loginRequiredLayout.setVisibility(View.GONE);

        } else {

            userProfileLayout.setVisibility(View.GONE);
            loginRequiredLayout.setVisibility(View.VISIBLE);

        }
    }
}
