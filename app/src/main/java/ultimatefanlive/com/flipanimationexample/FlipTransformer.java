package ultimatefanlive.com.flipanimationexample;

import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * Created by szymon on 11/20/2015.
 */
public class FlipTransformer implements ViewPager.PageTransformer {

    protected void onTransform(View view, float position) {
        onPreTransform(view, position);
//        Log.i("FlipTransformer", "onTransfor pos: " + position + ", view text: " + ((TextView) view).getText());
        view.setPivotY(position > 0f ? view.getHeight() : 0f);
//        view.setPivotX(view.getWidth() * 0.5f);
        view.setRotationX(90f * position);
        view.setTranslationX(view.getWidth() * -position);
        view.setTranslationY(view.getHeight() * -position);
    }

    public boolean isPagingEnabled() {
        return true;
    }
    protected void onPreTransform(View page, float position) {
        final float height = page.getHeight();

        page.setRotationX(0);
        page.setRotationY(0);
        page.setRotation(0);
        page.setScaleX(1);
        page.setScaleY(1);
//        page.setPivotX(0);
        page.setPivotY(0);
        page.setTranslationY(isPagingEnabled() ? 0f : -height * position);
        page.setTranslationX(0);

            page.setEnabled(true);
            page.setAlpha(1f);
    }

    @Override
    public void transformPage(View page, float position) {
        onTransform(page, position);
    }
}
