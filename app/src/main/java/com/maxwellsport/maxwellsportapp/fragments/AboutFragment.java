package com.maxwellsport.maxwellsportapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.services.ConnectionService;


public class AboutFragment extends Fragment {
    MainActivity mContext;
    private ConnectionService mConnectionService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(getResources().getString(R.string.toolbar_about_title));

        View v = inflater.inflate(R.layout.fragment_about, container, false);

        mConnectionService = new ConnectionService(getActivity());

        LinearLayout layout = (LinearLayout) v.findViewById(R.id.github_link);
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mConnectionService.isNetworkAvailable((ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE))) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse("https://github.com/peper11111/MaxwellSportApp"));
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, R.string.toast_msg_network_error, Toast.LENGTH_SHORT).show();
                }
            }
        });

        return v;
    }
}

