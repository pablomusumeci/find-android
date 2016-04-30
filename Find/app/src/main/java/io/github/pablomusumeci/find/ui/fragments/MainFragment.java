package io.github.pablomusumeci.find.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;

public class MainFragment extends Fragment {

    private MainActivityListener listener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (MainActivityListener) context;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(context.toString() + " must implement MainActivityListener interface");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        view.findViewById(R.id.learn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startLearning();
            }
        });


        view.findViewById(R.id.track).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.startTracking();
            }
        });

        return view;
    }
}