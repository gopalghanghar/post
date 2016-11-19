package project.ks.com.posts.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.SelectedTag;
import project.ks.com.posts.bean.User;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

public class SelectUserProfile extends AppCompatActivity {

    User user ;
    String userJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        userJson = getIntent().getStringExtra(AppConstant.USER_JSON);
        user = Utils.generateObjectFromJSON(userJson,User.class);

        GridView gridview = (GridView) findViewById(R.id.regUserProfile);

        gridview.setAdapter(new ImageAdapter(this));

        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {


                Intent tagSelection=new Intent(SelectUserProfile.this, SelectUserTag.class);
                user.setProfile(AppConstant.mThumbName[position]);

                tagSelection.putExtra(AppConstant.USER_JSON, Utils.generateJSONFromObject(user));
                startActivity(tagSelection);
                finish();
                Toast.makeText(SelectUserProfile.this,"Selected  position : "+position+"json"+Utils.generateJSONFromObject(user),Toast.LENGTH_SHORT).show();
            }
        });
    }

}
