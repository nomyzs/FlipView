package ultimatefanlive.com.flipanimationexample;

import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by szymon on 11/19/2015.
 */
public class PagerTestAdapter extends PagerAdapter {
    String[] pages = new String[]{"page1", "page2", "page3", "page4", "page5"};

    @Override
    public int getCount() {
        return pages.length;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        TextView view = (TextView) LayoutInflater.from(container.getContext()).inflate(R.layout.example_page_1, container, false);
        view.setText(pages[position]);
        Random random = new Random();

        view.setBackgroundColor(Color.rgb(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
        container.addView(view);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((View) object);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }
}
