package com.oluwatayo.apps.medmanager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MedicationListViewHolder> {

    private Context context;
    public MedicationListAdapter(Context context){
       this.context = context;
    }

    @Override
    public MedicationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(MedicationListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public  class MedicationListViewHolder extends RecyclerView.ViewHolder{

        public MedicationListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
