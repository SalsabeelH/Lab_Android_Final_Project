package birzeit.edu.labandroidfinalproject.ui.sorted;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import birzeit.edu.labandroidfinalproject.databinding.FragmentSortedBinding;


public class SortedFragment extends Fragment {
    private FragmentSortedBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SortedViewModel sortedViewModel = new ViewModelProvider(this).get(SortedViewModel.class);

        binding = FragmentSortedBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSorted;
        sortedViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
