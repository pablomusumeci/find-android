package io.github.pablomusumeci.find.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.model.ApplicationSettings;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;

public class MainFragment extends Fragment {
    private Unbinder unbinder;
    private MainActivityListener listener;

    @BindView(R.id.home_user_value)
    TextView user;

    @BindView(R.id.home_group_value)
    TextView group;

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

        user.setText(ApplicationSettings.getUsername());
        group.setText(ApplicationSettings.getGroupName());

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