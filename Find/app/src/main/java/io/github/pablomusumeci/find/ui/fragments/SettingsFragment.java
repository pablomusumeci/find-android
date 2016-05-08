package io.github.pablomusumeci.find.ui.fragments;

import static io.github.pablomusumeci.find.utils.ViewUtils.isEmpty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.github.pablomusumeci.find.FindApplication;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.model.ApplicationSettings;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;

public class SettingsFragment extends Fragment {

    @BindView(R.id.username) EditText userName;
    @BindView(R.id.group_name) EditText groupName;
    @BindView(R.id.server_address) EditText serverAddress;
    private Unbinder unbinder;

    MainActivityListener listener;

    public SettingsFragment() {
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
        final View view = inflater.inflate(R.layout.fragment_setting, container, false);
        unbinder = ButterKnife.bind(this, view);
        loadPreferences();
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.save_settings)
    public void saveSettings() {
        if (isEmpty(userName) || isEmpty(groupName) || isEmpty(serverAddress)) {
            listener.showAlertDialog("Incomplete form", "All the fields are required");
        }
        else {
            ApplicationSettings.setUsername(userName.getText().toString());
            ApplicationSettings.setGroupName(groupName.getText().toString());
            ApplicationSettings.setServerAddress(serverAddress.getText().toString());

            // Use new server address
            FindApplication application = (FindApplication) getActivity().getApplication();
            application.setHttpService();

            listener.goToMainFragment();
        }
    }

    private void loadPreferences() {
        userName.setText(ApplicationSettings.getUsername());
        groupName.setText(ApplicationSettings.getGroupName());
        serverAddress.setText(ApplicationSettings.getServerAddress());

    }
}
