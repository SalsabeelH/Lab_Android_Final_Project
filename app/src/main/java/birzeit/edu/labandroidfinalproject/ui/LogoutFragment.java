package birzeit.edu.labandroidfinalproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import birzeit.edu.labandroidfinalproject.LocalStorageManagers.SharedPrefManager;
import birzeit.edu.labandroidfinalproject.LogInActivity;
import birzeit.edu.labandroidfinalproject.NavigationDrawerActivity;
import birzeit.edu.labandroidfinalproject.databinding.FragmentLogoutBinding;

public class LogoutFragment extends Fragment {

    private FragmentLogoutBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentLogoutBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        SharedPrefManager sharedPrefManager = SharedPrefManager.getInstance((NavigationDrawerActivity) getActivity());
        sharedPrefManager.writeBoolean("loggedIn", false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Start the next activity
                Intent intent = new Intent((NavigationDrawerActivity) getActivity(), LogInActivity.class);
                startActivity(intent);
            }
        }, 2000);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
