package io.github.pablomusumeci.find.ui.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.events.LearningEvent;
import io.github.pablomusumeci.find.domain.model.scanning.LearningStrategy;
import io.github.pablomusumeci.find.domain.services.background.ScanningService;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class LearningFragment extends Fragment {

    @BindView(R.id.start_learning)
    Button button;

    @BindView(R.id.progress_bar)
    ProgressBar progressBar;

    @BindView(R.id.learning_location_value)
    EditText learningLocation;

    @BindView(R.id.learning_status_value)
    TextView learningStatus;

    private Unbinder unbinder;

    public LearningFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_learning, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.start_learning)
    public void onStartLearning() {
        if (learningLocation.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "You must enter a location", Toast.LENGTH_SHORT).show();
        }
        else {
            Intent mServiceIntent = new Intent(getActivity(), ScanningService.class);
            mServiceIntent.putExtra("strategy", new LearningStrategy());
            getActivity().startService(mServiceIntent);
            progressBar.setVisibility(View.VISIBLE);
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onLearningEvent(LearningEvent event) {
        learningStatus.setText(event.getMessage());
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }


}
