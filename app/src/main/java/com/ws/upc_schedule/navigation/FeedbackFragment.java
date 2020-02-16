package com.ws.upc_schedule.navigation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.ws.upc_schedule.R;

public class FeedbackFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_feedback, container, false);
//        final Button launchQQButton = root.findViewById(R.id.launchQQ);
        final Button launchGitHub = root.findViewById(R.id.launchGithub);
        final Button launchCoolApk = root.findViewById(R.id.launchCoolApk);
//        launchQQButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String url = "mqqwpa://im/chat?chat_type=wpa&uin=79223948";
//                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
//            }
//        });

        launchGitHub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://github.com/Wangs121/UPC_Schedule";
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
            }
        });

        launchCoolApk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://coolapk.com/apk/com.ws.upc_schedule";
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse(url)));
            }
        });
        return root;
    }
}