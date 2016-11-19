package project.ks.com.posts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.ImageSpan;

/**
 * Created by Gopal on 29-Sep-16.
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private int[] imageResId = {
            R.mipmap.ic_home_white_24dp,
            R.mipmap.ic_notifications_white_24dp,
            R.mipmap.ic_mode_edit_white_24dp,
            R.mipmap.ic_account_circle_white_24dp


    };
    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
    Context mContext;

    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumbOfTabsumb, Context mContext) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
        this.mContext = mContext;

    }

    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            Tab1 tab1 = new Tab1();
            return tab1;
        }
        else if(position == 1)
        {
            Tab2 tab2 = new Tab2();
            return tab2;
        }
        else if(position == 2)
        {
            Tab3 tab3 = new Tab3();
            return tab3;
        }
        else
        {
            Tab4 tab4 = new Tab4();
            return tab4;
        }


    }

    @Override
    public CharSequence getPageTitle(int position) {

        Drawable image = mContext.getDrawable(imageResId[position]);
        image.setBounds(0, 0, image.getIntrinsicWidth(), image.getIntrinsicHeight());
        SpannableString sb = new SpannableString(" ");
        ImageSpan imageSpan = new ImageSpan(image, ImageSpan.ALIGN_BOTTOM);
        sb.setSpan(imageSpan, 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return sb;

    }

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
