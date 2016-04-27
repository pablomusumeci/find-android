package io.github.pablomusumeci.find.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.ui.events.LearningEvent;
import io.github.pablomusumeci.find.ui.events.TrackingEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LearningFragment extends Fragment {


    public LearningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }


    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_learning, container, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLearningEvent(LearningEvent event) {
        TextView learningStatus = (TextView) getView().findViewById(R.id.learning_status_value);
        learningStatus.setText(event.getMessage());

    }
}
