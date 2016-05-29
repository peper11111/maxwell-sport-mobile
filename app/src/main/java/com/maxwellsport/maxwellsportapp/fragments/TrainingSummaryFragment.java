package com.maxwellsport.maxwellsportapp.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.maxwellsport.maxwellsportapp.R;

public class TrainingSummaryFragment extends Fragment {
    private View mView;
    private int[] mStars = {R.id.training_summary_star_1, R.id.training_summary_star_2, R.id.training_summary_star_3, R.id.training_summary_star_4, R.id.training_summary_star_5};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_training_summary, container, false);

        for (int i = 0; i < 5; i++) {
            ImageView star = (ImageView) mView.findViewById(mStars[i]);
            star.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinearLayout parent = (LinearLayout) v.getParent();
                    int n = parent.indexOfChild(v);
                    colorStars(n + 1);
                }
            });
        }
        return mView;
    }

    public void colorStars(int n) {
        LinearLayout stars = (LinearLayout) mView.findViewById(R.id.training_summary_rate);
        int i;
        /* Pokolorowanie gwiazdek na color accent */
        for (i = 0; i < n; i++) {
            ImageView star = (ImageView) stars.getChildAt(i);
            star.setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_ATOP);
        }

        /* Pokolorowoanie pozostalych gwiazdek na biało */
        for (; i < 5; i++) {
            ImageView star = (ImageView) stars.getChildAt(i);
            star.setColorFilter(getResources().getColor(R.color.material_white), PorterDuff.Mode.SRC_ATOP);
        }
    }
}
