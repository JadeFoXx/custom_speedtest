package ludwigsamuel.custom_speedtest.util;

import android.app.ActionBar;
import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;

/**
 * Created by secur on 7/18/2017.
 */

public class Animator {

    public static void fadeOut(Activity activity, final View view, int duration) {
        Animation fadeOut = new AlphaAnimation(1, 0);
        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(duration);
        fadeOut.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(fadeOut);
    }

    public static void fadeIn(Activity activity, final View view, int duration) {
        Animation fadeIn = new AlphaAnimation(0, 1);
        fadeIn.setInterpolator(new AccelerateInterpolator());
        fadeIn.setDuration(duration);
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(fadeIn);
    }

    public static void expand(final View view, int durationMultiplier) {
        if (view.getVisibility() == View.GONE) {

            view.measure(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            final int targetHeight = view.getMeasuredHeight();
            view.getLayoutParams().height = 1;
            view.setVisibility(View.VISIBLE);
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    view.getLayoutParams().height = (int) (targetHeight * interpolatedTime);
                    view.requestLayout();
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            a.setDuration(((int) (targetHeight / view.getContext().getResources().getDisplayMetrics().density)) * durationMultiplier);
            view.startAnimation(a);
        }
    }

    public static void collapse(final View view, int durationMultiplier) {
        if (view.getVisibility() == View.VISIBLE) {


            final int initialHeight = view.getMeasuredHeight();
            Animation a = new Animation() {

                @Override
                protected void applyTransformation(float interpolatedTime, Transformation t) {
                    if (interpolatedTime == 1) {
                        view.setVisibility(View.GONE);
                    } else {
                        view.getLayoutParams().height = initialHeight - (int) (initialHeight * interpolatedTime);
                        view.requestLayout();
                    }
                }

                @Override
                public boolean willChangeBounds() {
                    return true;
                }
            };

            a.setDuration(((int) (initialHeight / view.getContext().getResources().getDisplayMetrics().density)) * durationMultiplier);
            view.startAnimation(a);
        }
    }
}
