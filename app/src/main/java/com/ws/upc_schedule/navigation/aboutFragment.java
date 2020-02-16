package com.ws.upc_schedule.navigation;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ws.upc_schedule.BuildConfig;
import com.ws.upc_schedule.R;

public class aboutFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_about, container, false);
        final TextView version = root.findViewById(R.id.version);
        version.setText("版本:" + BuildConfig.VERSION_NAME);
        return root;
    }
}