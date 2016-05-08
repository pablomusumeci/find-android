package io.github.pablomusumeci.find.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.events.ScanningCancelled;
import io.github.pablomusumeci.find.domain.events.TrackingEvent;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class TrackingFragment extends Fragment {
    @BindView(R.id.return_from_tracking) Button button;
    private Unbinder unbinder;

    private MainActivityListener listener;

    public TrackingFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_tracking, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        // stop scanning thread
        EventBus.getDefault().post(new ScanningCancelled());
    }

    @OnClick(R.id.return_from_tracking)
     public void returnFromTracking(){
        EventBus.getDefault().post(new ScanningCancelled());
        listener.goToMainFragment();
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onTrackingEvent(TrackingEvent event) {
        TextView currentLocation = (TextView) getView().findViewById(R.id.value_location);
        currentLocation.setText(event.getLocation());
    }

}
