package ultimatefanlive.com.flipview;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by szymon on 11/18/2015.
 */
public class FlipView extends FrameLayout {
    private final float NUM_OF_VIEWS = 4;
    private final float rotationAngle;
    private int currentViewIndex = -1;
    private List<View> inflatedViews = new LinkedList<>();
    private long flipDuration = 500;
    private boolean loopViewFlipping = true;
    private boolean animationRunning;
    private int height;
    private int width;

    public FlipView(Context context, AttributeSet attrs) {
        super(context, attrs);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                    FlipView.this.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                } else {
                    FlipView.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                }
                height = FlipView.this.getHeight();
                width = FlipView.this.getWidth();
            }
        });
        rotationAngle = 360f / NUM_OF_VIEWS;
    }

    public void setUpWithViews(@NonNull List<View> views) {
        inflatedViews = views;
        for (int i = 0; i < views.size(); i++) {
            this.addView(views.get(i));
            if (i > 0) views.get(i).setVisibility(GONE);
        }
        currentViewIndex = 0;
    }

    public void flipNext() {
        if (inflatedViews.size() <= 1) return;
        int nextIndex = -1;
        if (currentViewIndex + 1 < inflatedViews.size()) {
            nextIndex = currentViewIndex + 1;
        } else if (loopViewFlipping) {
            nextIndex = 0;
        }
        if (nextIndex == -1) return;
        flipView(currentViewIndex, nextIndex);
    }

    private void flipView(int currentViewIndex, int nextViewIndex) {
        if (!animationRunning) {
            prepareViewsToFlip(getInflatedView(currentViewIndex), getInflatedView(nextViewIndex));
            AnimatorSet animatorSet = prepareAnimatorSet(getInflatedView(currentViewIndex), getInflatedView(nextViewIndex));
            animatorSet.addListener(createAnimatorListener(getInflatedView(currentViewIndex), nextViewIndex));
            animatorSet.start();
        }
    }

    private View getInflatedView(int index) {
        return inflatedViews.get(index);
    }

    private void prepareViewsToFlip(View currentView, View nextView) {
        currentView.setPivotY(0);
        currentView.setPivotX(width * 0.5f);
        nextView.setVisibility(VISIBLE);
        nextView.setPivotX(width * 0.5f);
        nextView.setPivotY(height);
        nextView.setTranslationY(-height);
        nextView.setRotationX(rotationAngle);
    }

    @NonNull
    private AnimatorSet prepareAnimatorSet(View currentView, View nextView) {
        ObjectAnimator objectAnimator11 = ObjectAnimator.ofFloat(currentView, "rotationX", 0f, -rotationAngle);
        ObjectAnimator objectAnimator12 = ObjectAnimator.ofFloat(currentView, "translationY", 0f, currentView.getHeight());

        ObjectAnimator objectAnimator21 = ObjectAnimator.ofFloat(nextView, "rotationX", nextView.getRotationX(), 0);
        ObjectAnimator objectAnimator22 = ObjectAnimator.ofFloat(nextView, "translationY", nextView.getTranslationY(), 0f);
        AnimatorSet animatorSet = new AnimatorSet();
        List<Animator> animatorList = new ArrayList<>();
        animatorList.add(objectAnimator11);
        animatorList.add(objectAnimator12);
        animatorList.add(objectAnimator21);
        animatorList.add(objectAnimator22);
        animatorSet.setDuration(flipDuration).setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.playTogether(animatorList);
        return animatorSet;
    }

    private Animator.AnimatorListener createAnimatorListener(final View oldView, final int newViewIndex) {
        return new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                animationRunning = true;
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                oldView.setVisibility(GONE);
                animationRunning = false;
                currentViewIndex = newViewIndex;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                oldView.setVisibility(GONE);
                animationRunning = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                animationRunning = true;

            }
        };
    }


    public void addCard(View view) {
        if (inflatedViews.size() == 0) currentViewIndex = 0;
        if (inflatedViews.size() > 0) view.setVisibility(GONE);
        inflatedViews.add(view);
        this.addView(view);
    }

    public void removeCard(View view) {
        inflatedViews.remove(view);
    }

    public boolean contains(View view) {
        return inflatedViews.contains(view);
    }

    public void flipToView(View view) {
        if (!inflatedViews.contains(view)) return;
        if (inflatedViews.size() <= 1) return;
        int nextIndex = inflatedViews.indexOf(view);
        if (nextIndex == -1 || nextIndex == currentViewIndex) return;
        flipView(currentViewIndex, nextIndex);
    }
}
