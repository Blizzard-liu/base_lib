package com.winsky.winto.base.dialog.ad;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;
import com.winsky.winto.base.util.AppUtils;
import com.winsky.winto.base.util.Utils;


/**
 * Created by aaron on 16/8/3.
 * 弹性动画操作类
 */
public class AnimSpring {

    public static AnimSpring animSpring = null;
    public static SpringSystem springSystem = null;
    public SpringConfig springConfig = SpringConfig.fromBouncinessAndSpeed(8, 2);

    public static AnimSpring getInstance() {
        if (springSystem == null) {
            springSystem = SpringSystem.create();
        }
        if (animSpring == null) {
            animSpring = new AnimSpring();
        }

        return animSpring;
    }


    // #################### 弹窗打开时的动画效果 #############################
    /**
     * 弹窗打开时的动画效果
     * @param animContainer
     */
    public void startAnim( final RelativeLayout animContainer) {
        springConfig = SpringConfig.fromBouncinessAndSpeed(8, 2);
        startConstantAnim(animContainer);
    }

    /**
     * 开始动画-定义动画开始角度
     * @param animType
     * @param animContainer
     */
    public void startCircleAnim(final int animType, final RelativeLayout animContainer) {
        Context context = Utils.getApp().getApplicationContext();

        double radius = Math.sqrt(AppUtils.getScreenHeight(context) * AppUtils.getScreenHeight(context) + AppUtils.getScreenWidth(context) * AppUtils.getScreenWidth(context));
        double heightY = - Math.sin(Math.toRadians(animType)) * radius;
        double widthX = Math.cos(Math.toRadians(animType)) * radius;

        Spring tranSpring = springSystem.createSpring();
        Spring tranSpring1 = springSystem.createSpring();
        tranSpring.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringActivate(Spring spring) {
                animContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSpringUpdate(Spring spring) {
                animContainer.setTranslationX((float) spring.getCurrentValue());
            }
        });

        tranSpring1.addListener(new SimpleSpringListener() {
            @Override
            public void onSpringActivate(Spring spring) {
                animContainer.setVisibility(View.VISIBLE);
            }

            @Override
            public void onSpringUpdate(Spring spring) {
                animContainer.setTranslationY((float) spring.getCurrentValue());
            }
        });

        tranSpring.setSpringConfig(springConfig);
        tranSpring1.setSpringConfig(springConfig);
        tranSpring.setCurrentValue(widthX);
        tranSpring.setEndValue(0);
        tranSpring1.setCurrentValue(heightY);
        tranSpring1.setEndValue(0);
    }


    /**
     * 开始动画-固定类型动画
     */
    public void startConstantAnim( final RelativeLayout animContainer) {
      startCircleAnim(270, animContainer);

    }

    // ############################## 弹窗关闭时的动画效果 ##################################

    /**
     * 弹窗退出时的动画
     * @param animDialogUtils
     */
    public void stopAnim( final AnimDialogUtils animDialogUtils) {
        if (animDialogUtils == null) {
            return;
        }
        animDialogUtils.getAndroidContentView().removeView(animDialogUtils.getRootView());
        animDialogUtils.setShowing(false);
//            animDialogUtils.getAnimContainer().animate().alpha(0).setListener(new AnimatorListenerAdapter() {
//                @Override
//                public void onAnimationEnd(Animator animation) {
//                    animDialogUtils.getAndroidContentView().removeView(animDialogUtils.getRootView());
//                    animDialogUtils.setShowing(false);
//                }
//            }).setDuration(500).setInterpolator(new AccelerateInterpolator()).start();
        }

}
