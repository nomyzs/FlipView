package ultimatefanlive.com.flipanimationexample;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ultimatefanlive.com.flipview.FlipView;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private ViewPager pager;

    private FlipView flipView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (savedInstanceState == null) {
//            getFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.flip_view_container, new CardFrontFragment())
//                    .commit();
//        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.fragment_fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flipViews();
            }
        });
        flipView = (FlipView) view.findViewById(R.id.flip_view);

        flipView.setUpWithViews(getFlipadbleViews());
        ListAdapter listAdapter = new FlipListAdapter();
//        pager = (VerticalViewPager) view.findViewById(R.id.flip_view_pager);
//        pager.setAdapter(new PagerTestAdapter());
//        pager.setPageTransformer(true, new FlipTransformer());
    }

    private List<View> getFlipadbleViews() {
        Random generator = new Random();
        List<View> list = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            TextView tv = new TextView(getContext());
            tv.setBackgroundColor(Color.rgb(generator.nextInt(255), generator.nextInt(255), generator.nextInt(255)));
            tv.setText("Page nr " + i);
            list.add(tv);
        }
        return list;
    }

    private void flipViews() {
        pager.setCurrentItem(pager.getCurrentItem() + 1, true);
        flipView.flipNext();
    }

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

}
