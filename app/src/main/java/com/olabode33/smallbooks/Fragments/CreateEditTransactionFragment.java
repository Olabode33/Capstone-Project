package com.olabode33.smallbooks.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.olabode33.smallbooks.Model.Transaction;
import com.olabode33.smallbooks.R;
import com.olabode33.smallbooks.ui.SignInActivity;

import butterknife.BindString;
import butterknife.BindView;
import butterknife.ButterKnife;


public class CreateEditTransactionFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.type_editText) EditText mTypeET;
    @BindView(R.id.category_editText) EditText mCategoryET;
    @BindView(R.id.date_editText) EditText mDateET;
    @BindView(R.id.amount_editText) EditText mAmountET;
    @BindView(R.id.memo_editText) EditText mMemoET;
    @BindView(R.id.save_trans_button) Button mSaveButton;

    @BindString(R.string.firebase_database_reference) String mFirebaseDBRefString;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserTransactionsDatabaseRef;
    private FirebaseUser mFirebaseUser;


    public CreateEditTransactionFragment() {
        // Required empty public constructor
    }

    public static CreateEditTransactionFragment newInstance(String param1, String param2) {
        CreateEditTransactionFragment fragment = new CreateEditTransactionFragment();
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


        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mFirebaseUser == null) {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserTransactionsDatabaseRef = mFirebaseDatabase.getReference().child(getString(R.string.firebase_database_reference)).child(mFirebaseUser.getUid());


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_edit_transaction, container, false);
        ButterKnife.bind(this, view);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTransaction();
            }
        });

        return view;
    }

    private void saveTransaction() {
        Transaction userTrans = new Transaction(mTypeET.getText().toString(),
                                                mDateET.getText().toString(),
                                                Double.parseDouble(mAmountET.getText().toString()),
                                                mMemoET.getText().toString(),
                                                mCategoryET.getText().toString());
        mUserTransactionsDatabaseRef.push().setValue(userTrans)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(getContext(), "Posted successfully!", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), "Error posting transaction: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
