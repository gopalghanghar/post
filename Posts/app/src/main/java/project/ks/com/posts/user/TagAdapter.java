package project.ks.com.posts.user;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ImageView;
import android.widget.TextView;

import project.ks.com.posts.R;
import project.ks.com.posts.bean.SelectedTag;
import project.ks.com.posts.bean.SelectedTag;

/**
 * Created by Gopal on 05-Nov-16.
 */

public class TagAdapter extends BaseAdapter {

    Context ctx;
    LayoutInflater lInflater;
    List<SelectedTag> objects;

    TagAdapter(Context context, List<SelectedTag> tags) {
        ctx = context;
        objects = tags;
        lInflater = (LayoutInflater) ctx
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = lInflater.inflate(R.layout.tag_item, parent, false);
        }

        SelectedTag tag = getTag(position);

        ((TextView) view.findViewById(R.id.tsTagName)).setText(tag.getName());
        ((TextView) view.findViewById(R.id.tsTagDetail)).setText(tag.getDescription());

        CheckBox cbBuy = (CheckBox) view.findViewById(R.id.tsChBox);
        cbBuy.setOnCheckedChangeListener(myCheckChangList);
        cbBuy.setTag(position);
        cbBuy.setChecked(tag.getIsChecked());
        return view;
    }

    SelectedTag getTag(int position) {
        return ((SelectedTag) getItem(position));
    }

    ArrayList<SelectedTag> getBox() {
        ArrayList<SelectedTag> box = new ArrayList<SelectedTag>();
        for (SelectedTag tag : objects) {
            if (tag.getIsChecked())
                box.add(tag);
        }
        return box;
    }

    OnCheckedChangeListener myCheckChangList = new OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            getTag((Integer) buttonView.getTag()).setIsChecked(isChecked);
        }
    };
}
