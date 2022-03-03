package com.bwt.mypawnshop.ui.dashboard;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import com.bwt.mypawnshop.databinding.FragmentDashboardBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.io.IOException;
import com.bwt.mypawnshop.R;

public class DashboardFragment extends Fragment {
    private static final String TAG ="MyPawnShop";
    private DashboardViewModel dashboardViewModel;
    private FragmentDashboardBinding binding;
    TextView textView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        textView = binding.textDashboard;
        final Button goldPriceBtn = binding.getgold;
        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        goldPriceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(getActivity(),"Get Price Button clicked...",Toast.LENGTH_LONG).show();
                new doIT().execute();
            }
        });
        return root;
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public class doIT extends AsyncTask<Void,Void,Void> {
        String words;
        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document document = Jsoup.connect("https://www.myloancare.in/gold-rate-india/chennai").get();
                words = document.select(".col-12 .p-0 p").first().text();

            } catch (IOException e) {
                e.printStackTrace();
            } return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(words);
            Log.i(TAG, words);
        }
    }
}