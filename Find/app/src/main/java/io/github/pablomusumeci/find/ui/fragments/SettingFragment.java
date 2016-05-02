package io.github.pablomusumeci.find.ui.fragments;

import static io.github.pablomusumeci.find.utils.Constants.DEFAULT_SERVER_ADDRESS;
import static io.github.pablomusumeci.find.utils.Constants.GROUP_NAME;
import static io.github.pablomusumeci.find.utils.Constants.SERVER_ADDRESS;
import static io.github.pablomusumeci.find.utils.Constants.USERNAME;
import static io.github.pablomusumeci.find.utils.ViewUtils.isEmpty;

import com.pixplicity.easyprefs.library.Prefs;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import io.github.pablomusumeci.find.R;
import io.github.pablomusumeci.find.ui.activities.MainActivityListener;

public class SettingFragment extends Fragment {

    MainActivityListener listener;

    public SettingFragment() {
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
                    setPrefences(userName.getText().toString(),
                                 groupName.getText().toString(),
                                 serverAddress.getText().toString());
                }
            }
        });
        return view;
    }

    private void loadPreferences(View v) {
        EditText userName = (EditText) v.findViewById(R.id.username);
        userName.setText(Prefs.getString(USERNAME, ""));

        EditText groupName = (EditText) v.findViewById(R.id.group_name);
        groupName.setText(Prefs.getString(GROUP_NAME, ""));

        EditText serverAddress = (EditText) v.findViewById(R.id.server_address);
        serverAddress.setText(Prefs.getString(SERVER_ADDRESS, DEFAULT_SERVER_ADDRESS));

    }

    private void setPrefences(String userName, String groupName, String serverAddress){
        Prefs.putString(USERNAME, userName);
        Prefs.putString(GROUP_NAME, groupName);
        Prefs.putString(SERVER_ADDRESS, serverAddress);
    }

}
