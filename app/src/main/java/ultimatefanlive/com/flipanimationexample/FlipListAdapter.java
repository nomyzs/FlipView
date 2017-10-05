package ultimatefanlive.com.flipanimationexample;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by szymon on 11/18/2015.
 */
public class FlipListAdapter extends BaseAdapter {
    private String[] items = new String[]{"Page 1", "Page 2", "Page 3"};

    @Override
    public int getCount() {
        return items.length;
    }

    @Override
    public Object getItem(int position) {
        return items[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_card_1, parent, false);

        textView.setText(items[position]);
        return textView;
    }
}
