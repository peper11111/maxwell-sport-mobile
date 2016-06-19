package com.maxwellsport.maxwellsportapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.maxwellsport.maxwellsportapp.activities.MainActivity;
import com.maxwellsport.maxwellsportapp.R;
import com.maxwellsport.maxwellsportapp.helpers.SharedPreferencesHelper;

import java.util.Locale;

public class SettingsServerFragment extends Fragment {
    private MainActivity mContext;
    private View mView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        mContext = (MainActivity) getActivity();
        mContext.setTitle(R.string.settings_server_label);
        mView = inflater.inflate(R.layout.fragment_settings_server, container, false);

        Button save_button = (Button) mView.findViewById(R.id.settings_server_save_button);
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean error = false;

                String ipString1 = ((EditText) mView.findViewById(R.id.settings_server_ip_1)).getText().toString();
                String ipString2 = ((EditText) mView.findViewById(R.id.settings_server_ip_2)).getText().toString();
                String ipString3 = ((EditText) mView.findViewById(R.id.settings_server_ip_3)).getText().toString();
                String ipString4 = ((EditText) mView.findViewById(R.id.settings_server_ip_4)).getText().toString();
                String portString = ((EditText) mView.findViewById(R.id.settings_server_port)).getText().toString();
                int ip1 = 0;
                int ip2 = 0;
                int ip3 = 0;
                int ip4 = 0;
                int port = 0;

                try {
                    ip1 = Integer.parseInt(ipString1);
                    if (ip1 > 255)
                        error = true;

                    ip2 = Integer.parseInt(ipString2);
                    if (ip2 > 255)
                        error = true;

                    ip3 = Integer.parseInt(ipString3);
                    if (ip3 > 255)
                        error = true;

                    ip4 = Integer.parseInt(ipString4);
                    if (ip4 > 255)
                        error = true;

                    port = Integer.parseInt(portString);
                    if (port > 65535)
                        error = true;
                } catch (NumberFormatException e) {
                    error = true;
                }

                if (error)
                    Toast.makeText(mContext, R.string.toast_msg_server_address_error, Toast.LENGTH_SHORT).show();
                else {
                    String address = String.format(Locale.getDefault(), "%d.%d.%d.%d", ip1, ip2, ip3, ip4);
                    SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.settings_server_address_key, address);
                    SharedPreferencesHelper.putValue(mContext, SharedPreferencesHelper.settings_server_port_key, port);
                    Toast.makeText(mContext, R.string.toast_msg_server_address_changed, Toast.LENGTH_SHORT).show();
                }
            }
        });

        Button restore_button = (Button) mView.findViewById(R.id.settings_server_restore_button);
        restore_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.settings_server_address_key);
                SharedPreferencesHelper.remove(mContext, SharedPreferencesHelper.settings_server_port_key);
                Toast.makeText(mContext, R.string.toast_msg_server_address_restored, Toast.LENGTH_SHORT).show();
            }
        });

        return mView;
    }

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        if (enter) {
            return AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.abc_grow_fade_in_from_bottom);
        } else {
            return AnimationUtils.loadAnimation(mContext.getApplicationContext(), R.anim.abc_fade_out);
        }
    }
}
