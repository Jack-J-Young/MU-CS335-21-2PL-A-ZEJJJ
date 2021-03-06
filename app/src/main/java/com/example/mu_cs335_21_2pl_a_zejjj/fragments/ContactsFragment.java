package com.example.mu_cs335_21_2pl_a_zejjj.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mu_cs335_21_2pl_a_zejjj.NumberAdapter;
import com.example.mu_cs335_21_2pl_a_zejjj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ContactsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactsFragment extends Fragment {

    RecyclerView rv;
    RecyclerView.LayoutManager lm;
    NumberAdapter na;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ContactsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ContactsFragment newInstance(String param1, String param2) {
        ContactsFragment fragment = new ContactsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_contacts, container, false);
        // Inflate the layout for this fragment

        // setup objects for recycle view for the contacts
        rv = v.findViewById(R.id.rv_1);
        rv.setHasFixedSize(true);

        lm = new LinearLayoutManager(getContext());

        rv.setLayoutManager(lm);

        // get uid
        String uid = "";
        try {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
            uid = "aBcDeFgH1234";
        }
        DocumentReference document = FirebaseFirestore.getInstance().collection("users").document(uid);

        //query db for logged in users contacts
        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                String uid = "";
                try {
                    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                } catch (Exception e) {
                    uid = "aBcDeFgH1234";
                }
                if (task.isSuccessful()) {
                    DocumentSnapshot snap_document = task.getResult();
                    if (snap_document.exists()) {
                        Map<String, Object> data = snap_document.getData();
                        if (data.containsKey("contacts")) {
                            List<String> contacts = (List<String>) data.get("contacts");
                            // add the contacts to the recycle view
                            na = new NumberAdapter(contacts);
                            rv.setAdapter(na);

                        }
                    }
                }
            }
        });

        return v;
    }

    /* updateList: update the recycle view with new user data */
    public void updateList(View v) {
        // get uid
        String uid = "";
        try {
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        } catch (Exception e) {
            uid = "aBcDeFgH1234";
        }
        DocumentReference document = FirebaseFirestore.getInstance().collection("users").document(uid);

        document.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                // get uid
                String uid = "";
                try {
                    uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
                } catch (Exception e) {
                    uid = "aBcDeFgH1234";
                }
                if (task.isSuccessful()) {
                    DocumentSnapshot snap_document = task.getResult();
                    if (snap_document.exists()) {
                        Map<String, Object> data = snap_document.getData();
                        if (data.containsKey("contacts")) {
                            List<String> contacts = (List<String>) data.get("contacts");
                            // set recycle view to the contacts
                            na = new NumberAdapter(contacts);
                            rv.setAdapter(na);

                        }
                    }
                }
            }
        });
    }
}