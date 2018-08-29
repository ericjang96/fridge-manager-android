package com.example.kevin.fridgemanager.Managers;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.util.AttributeSet;

/**
 * Created by kevin on Aug 27, 2018
 **/
// Npa stands for No Predictive Animations
// Fixes an issue with random crashing that came from recycler view trying to pull from a common pool
// before clean up was done, causing an index out of bounds issue.
// https://stackoverflow.com/questions/30220771/recyclerview-inconsistency-detected-invalid-item-position
public class NpaLinearLayoutManager extends LinearLayoutManager{
    @Override
    public boolean supportsPredictiveItemAnimations() {
        return false;
    }

    public NpaLinearLayoutManager(Context context) {
        super(context);
    }

    public NpaLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public NpaLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }
}
