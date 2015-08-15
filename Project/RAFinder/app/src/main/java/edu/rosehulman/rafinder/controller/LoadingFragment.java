package edu.rosehulman.rafinder.controller;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.rosehulman.rafinder.R;

public class LoadingFragment extends Fragment {
    private View mProgressView;
    private ProgressDialog mProgressDialog;
    private Context context;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @return A new instance of fragment HomeFragment.
     */
    public static LoadingFragment newInstance(/*String param1, String param2*/) {
        LoadingFragment fragment = new LoadingFragment();
        return fragment;
    }

    public LoadingFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_loading, container, false);
        context = v.getContext();
        mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setTitle("Loading");
        mProgressDialog.setMessage("Loading Employee data...");
        mProgressDialog.setCancelable(false);

        mProgressView = v.findViewById(R.id.login_progress);

        int shortAnimTime = getResources().getInteger(
                android.R.integer.config_shortAnimTime);

        mProgressView.setVisibility(View.VISIBLE);
        mProgressView.animate().setDuration(shortAnimTime)
                .alpha(1)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        mProgressView.setVisibility(View.VISIBLE);
                    }
                });

        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
