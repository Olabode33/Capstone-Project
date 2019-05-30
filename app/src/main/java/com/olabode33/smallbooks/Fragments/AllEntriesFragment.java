package com.olabode33.smallbooks.Fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.olabode33.smallbooks.Adapters.TransactionEntryViewHolder;
import com.olabode33.smallbooks.Interfaces.OnFragmentInteractionListener;
import com.olabode33.smallbooks.Model.Transaction;
import com.olabode33.smallbooks.R;
import com.olabode33.smallbooks.ui.SignInActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllEntriesFragment extends Fragment {
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";

//    private String mParam1;
//    private String mParam2;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mUserTransactionsDatabaseRef;
    private FirebaseUser mFirebaseUser;
    private FirebaseRecyclerAdapter<Transaction, TransactionEntryViewHolder> mFirebaseRecyclerAdapter;

    private OnFragmentInteractionListener mListener;

    View mView;
    @BindView(R.id.rv_transactions_list) RecyclerView mTransactionsRecyclerView;
    @BindView(R.id.pb_loading_transactions) ProgressBar mLoadingProgressBar;

    public AllEntriesFragment() {
        // Required empty public constructor
    }

    public static AllEntriesFragment newInstance() {
        AllEntriesFragment fragment = new AllEntriesFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if(mFirebaseUser == null) {
            Intent intent = new Intent(getContext(), SignInActivity.class);
            startActivity(intent);
        }

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mUserTransactionsDatabaseRef = mFirebaseDatabase.getReference().child(getString(R.string.firebase_database_reference)).child(mFirebaseUser.getUid());
        mUserTransactionsDatabaseRef.keepSynced(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_all_entries, container, false);
        ButterKnife.bind(this, mView);
        retrieveTransactions();

        return mView;
    }

    private void retrieveTransactions(){
        Log.i("Testing", "Retrieving...");
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mView.getContext());
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        mTransactionsRecyclerView.setLayoutManager(linearLayoutManager);

        Query query = mFirebaseDatabase.getReference()
                .child(getString(R.string.firebase_database_reference))
                .child(mFirebaseUser.getUid())
                .limitToLast(50);

        FirebaseRecyclerOptions<Transaction> options = new FirebaseRecyclerOptions.Builder<Transaction>()
                .setQuery(mUserTransactionsDatabaseRef, Transaction.class)
                .build();

        mFirebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Transaction, TransactionEntryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull TransactionEntryViewHolder holder, int position, @NonNull Transaction model) {
                Log.i("Testing", "onBindViewHolder...");
                String modelKey = getRef(position).getKey();
                populateUI(holder, model, modelKey);
            }

            @NonNull
            @Override
            public TransactionEntryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                Log.i("Testing", "onCreateViewHolder...");
                LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
                View view = inflater.inflate(R.layout.transaction_list_item, viewGroup, false);
                return new TransactionEntryViewHolder(view);
            }

            @Override
            public void onDataChanged() {
                Log.i("Testing", "onDataChanged...");
                super.onDataChanged();
                if(mLoadingProgressBar != null && mLoadingProgressBar.isShown()){
                    mLoadingProgressBar.setVisibility(View.INVISIBLE);
                }
            }
        };

        mTransactionsRecyclerView.setAdapter(mFirebaseRecyclerAdapter);
        DividerItemDecoration decoration = new DividerItemDecoration(mView.getContext(), DividerItemDecoration.VERTICAL);
        mTransactionsRecyclerView.addItemDecoration(decoration);
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

    private void populateUI(TransactionEntryViewHolder holder, Transaction model, String modelKey){
        Log.i("Testing", "populateUI...");
        final String key = modelKey;
        model.setKey(modelKey);

        holder.setAmountTextView(model.getAmount());
        holder.setDateTextView(model.getDate());
        holder.setCategoryTextView(model.getCategory());
        holder.setMemoTextView(model.getMemo());
    }

}
