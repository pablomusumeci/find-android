package io.github.pablomusumeci.find.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.events.TrackingEvent;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TrackingFragment extends Fragment {


    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (getArguments() != null) {
        }
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
        return inflater.inflate(R.layout.fragment_tracking, container, false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTrackingEvent(TrackingEvent event) {
        TextView currentLocation = (TextView) getView().findViewById(R.id.value_location);
        currentLocation.setText(event.getLocation());
    }

}
