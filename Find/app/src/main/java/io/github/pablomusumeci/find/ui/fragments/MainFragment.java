package io.github.pablomusumeci.find.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;

public class MainFragment extends Fragment {
    private Unbinder unbinder;
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
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.learn)
    public void learn(){
        listener.startLearning();
    }

    @OnClick(R.id.track)
    public void track(){
        listener.startTracking();
    }
}