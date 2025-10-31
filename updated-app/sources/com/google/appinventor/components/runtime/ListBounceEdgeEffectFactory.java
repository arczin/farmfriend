package com.google.appinventor.components.runtime;

import android.graphics.Canvas;
import android.widget.EdgeEffect;
import androidx.dynamicanimation.animation.SpringAnimation;
import androidx.dynamicanimation.animation.SpringForce;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ListBounceEdgeEffectFactory extends RecyclerView.EdgeEffectFactory {
    private static final float FLING_TRANSLATION_MAGNITUDE = 0.5f;
    private static final float OVERSCROLL_TRANSLATION_MAGNITUDE = 0.2f;

    public EdgeEffect createEdgeEffect(RecyclerView recyclerView, int direction) {
        LinearLayoutManager layoutManager = recyclerView.getLayoutManager();
        return new BounceEdgeEffect(recyclerView, direction, layoutManager != null && layoutManager.getOrientation() == 0);
    }

    private static class BounceEdgeEffect extends EdgeEffect {
        private final int direction;
        private final boolean isHorizontal;
        private final RecyclerView recyclerView;
        private SpringAnimation translationAnim;

        public BounceEdgeEffect(RecyclerView recyclerView2, int direction2, boolean isHorizontal2) {
            super(recyclerView2.getContext());
            this.recyclerView = recyclerView2;
            this.direction = direction2;
            this.isHorizontal = isHorizontal2;
        }

        public void onPull(float deltaDistance) {
            super.onPull(deltaDistance);
            handlePull(deltaDistance);
        }

        public void onPull(float deltaDistance, float displacement) {
            super.onPull(deltaDistance, displacement);
            handlePull(deltaDistance);
        }

        private void handlePull(float deltaDistance) {
            translateRecyclerView(((float) (this.recyclerView.getWidth() * ((this.direction == 3 || (this.isHorizontal && this.direction == 2)) ? -1 : 1))) * deltaDistance * ListBounceEdgeEffectFactory.OVERSCROLL_TRANSLATION_MAGNITUDE);
            if (this.translationAnim != null) {
                this.translationAnim.cancel();
            }
        }

        public void onRelease() {
            super.onRelease();
            if (getTranslation() != 0.0f) {
                this.translationAnim = createAnim();
                if (this.translationAnim != null) {
                    this.translationAnim.start();
                }
            }
        }

        public void onAbsorb(int velocity) {
            super.onAbsorb(velocity);
            float translationVelocity = ((float) (((this.direction == 3 || (this.isHorizontal && this.direction == 2)) ? -1 : 1) * velocity)) * ListBounceEdgeEffectFactory.FLING_TRANSLATION_MAGNITUDE;
            if (this.translationAnim != null) {
                this.translationAnim.cancel();
            }
            this.translationAnim = createAnim();
            if (this.translationAnim != null) {
                this.translationAnim.setStartVelocity(translationVelocity);
                this.translationAnim.start();
            }
        }

        public boolean draw(Canvas canvas) {
            return false;
        }

        public boolean isFinished() {
            return this.translationAnim == null || !this.translationAnim.isRunning();
        }

        private SpringAnimation createAnim() {
            return new SpringAnimation(this.recyclerView, this.isHorizontal ? SpringAnimation.TRANSLATION_X : SpringAnimation.TRANSLATION_Y).setSpring(new SpringForce().setFinalPosition(0.0f).setDampingRatio(ListBounceEdgeEffectFactory.FLING_TRANSLATION_MAGNITUDE).setStiffness(200.0f));
        }

        private float getTranslation() {
            return this.isHorizontal ? this.recyclerView.getTranslationX() : this.recyclerView.getTranslationY();
        }

        private void translateRecyclerView(float translationDelta) {
            if (this.isHorizontal) {
                this.recyclerView.setTranslationX(getTranslation() + translationDelta);
            } else {
                this.recyclerView.setTranslationY(getTranslation() + translationDelta);
            }
        }
    }
}
