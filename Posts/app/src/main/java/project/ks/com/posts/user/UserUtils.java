package project.ks.com.posts.user;

import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.util.EntityUtils;
import project.ks.com.posts.bean.SelectedTag;
import project.ks.com.posts.bean.Tag;
import project.ks.com.posts.bean.TagList;
import project.ks.com.posts.bean.UserList;
import project.ks.com.posts.util.AppConstant;
import project.ks.com.posts.util.Utils;

/**
 * Created by Gopal on 25-Oct-16.
 */
public class UserUtils {

    public static UserList doLogin (String username){

        Log.i("UserList"," Method : doLogin---> Enter");

        HttpResponse httpResponse = null;
        String responseBody = "";
        UserList userList = null;

        try {

            httpResponse = Utils.postRequest(AppConstant.LOGIN_MODULE + "/" + username + "/user",null);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            if(!Utils.isEmpty(responseBody)) {
                userList = Utils.generateObjectFromJSON(responseBody,UserList.class);
            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("UserList"," Method : doLogin---> Exit");
        return userList;
    }

    public static Boolean doRegistration (String userJson){

        Log.i("UserList"," Method : doRegistration---> Enter");

        HttpResponse httpResponse = null;
        Boolean isRegister = false;

        try {

            httpResponse = Utils.postRequest(AppConstant.USER_REGISTRATION, userJson);

            if(httpResponse.getStatusLine().getStatusCode() == 200){

                isRegister = Boolean.TRUE;

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("UserList"," Method : doRegistration---> Exit");
        return isRegister;
    }

    public static TagList getTags (){

        Log.i("UserList"," Method : getTags---> Enter");

        HttpResponse httpResponse = null;
        String responseBody = "";
        TagList tagList = null;

        try {

            httpResponse = Utils.postRequest(AppConstant.TAG_LIST,null);

            responseBody = EntityUtils.toString(httpResponse.getEntity());

            if(!Utils.isEmpty(responseBody)) {
                tagList = Utils.generateObjectFromJSON(responseBody,TagList.class);

            }

        } catch (IOException e) {

            e.printStackTrace();
        }

        Log.i("UserList"," Method : getTags---> Exit");
        return tagList;
    }

    public static List<Tag> getTagFromSelectedTag(List<SelectedTag> selectedTags){

        List<Tag> tags = new ArrayList<>();

        if(!selectedTags.isEmpty()){
            for (SelectedTag selectedTag : selectedTags ) {

                Tag tag = new Tag(selectedTag);
                tags.add(tag);
            }
        }
        return tags;
    }

    public static List<SelectedTag> getSelectedTagFromTag(List<Tag> tags){

        List<SelectedTag> selectedTags = new ArrayList<>();

        if(!tags.isEmpty()){
            for (Tag tag : tags ) {

                SelectedTag selectedTag = new SelectedTag(tag);
                selectedTags.add(selectedTag);
            }
        }
        return selectedTags;
    }
}
