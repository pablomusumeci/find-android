package io.github.pablomusumeci.find.ui.fragments;

import static io.github.pablomusumeci.find.utils.ViewUtils.isEmpty;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.domain.model.ApplicationSettings;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;

public class SettingsFragment extends Fragment {

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
        loadPreferences(view);
        Button button = (Button) view.findViewById(R.id.save_settings);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText userName = (EditText) view.findViewById(R.id.username);
                EditText groupName = (EditText) view.findViewById(R.id.group_name);
                EditText serverAddress = (EditText) view.findViewById(R.id.server_address);
                if (isEmpty(userName) || isEmpty(groupName) || isEmpty(serverAddress)) {
                    listener.showAlertDialog("Incomplete form", "All the fields are required");
                }
                else {
                    ApplicationSettings.setUsername(userName.getText().toString());
                    ApplicationSettings.setGroupName(groupName.getText().toString());
                    ApplicationSettings.setServerAddress(serverAddress.getText().toString());
                }
            }
        });
        return view;
    }

    private void loadPreferences(View v) {
        EditText userName = (EditText) v.findViewById(R.id.username);
        userName.setText(ApplicationSettings.getUsername());

        EditText groupName = (EditText) v.findViewById(R.id.group_name);
        groupName.setText(ApplicationSettings.getGroupName());

        EditText serverAddress = (EditText) v.findViewById(R.id.server_address);
        serverAddress.setText(ApplicationSettings.getServerAddress());

    }
}
