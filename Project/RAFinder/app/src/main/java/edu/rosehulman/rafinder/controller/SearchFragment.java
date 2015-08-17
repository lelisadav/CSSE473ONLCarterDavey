package edu.rosehulman.rafinder.controller;

import android.app.Activity;
import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import edu.rosehulman.rafinder.R;
import edu.rosehulman.rafinder.adapter.SearchResultArrayAdapter;
import edu.rosehulman.rafinder.model.person.Employee;

/**
 * The search box and result list.
 */
public class SearchFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    ListView listView;
    List<Employee> items=new ArrayList<>();
    ListAdapter mAdapter;

    /**
     * Use this factory method to create a new instance of this fragment using the provided parameters.
     *
     * @param param1
     *         Parameter 1.
     * @param param2
     *         Parameter 2.
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance(String param1, String param2) {
        SearchFragment fragment = new SearchFragment();

        return fragment;
    }

    public SearchFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdapter=new SearchResultArrayAdapter(getActivity(), R.layout.layout_search_item, items);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_search, container, false);
        ImageButton searchButton=(ImageButton) view.findViewById(R.id.searchButton);
       final EditText searchField=(EditText) view.findViewById(R.id.searchField);
        listView=(ListView) view.findViewById(R.id.searchResultsFragment);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               items= mListener.getEmployeesForName(searchField.getText().toString());
                ((SearchResultArrayAdapter)mAdapter).refresh(items);
                view.refreshDrawableState();

            }
        });
//        FrameLayout frameLayout= (FrameLayout) view.findViewById(R.id.searchresultsfragment);
//        getChildFragmentManager().beginTransaction()
//                .replace(R.id.searchResultsFragment, results)
//                .commit();
        return view;
    }



    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                                         + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this fragment to allow an interaction in this
     * fragment to be communicated to the activity and potentially other fragments contained in that activity.
     * <p/>
     * See the Android Training lesson <a href= "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
      public List<Employee> getEmployeesForName(String name);
    }

}
