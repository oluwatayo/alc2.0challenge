package com.oluwatayo.apps.medmanager.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.Interfaces.ClickListeners;
import com.oluwatayo.apps.medmanager.Interfaces.ClickListeners.MedItemClickListener;
import com.oluwatayo.apps.medmanager.R;
import com.oluwatayo.apps.medmanager.utils.DateUtils;
import com.oluwatayo.apps.medmanager.utils.ResourceUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MedicationListViewHolder> {

    private MedItemClickListener medItemClickListener;
    private ArrayList<Medication> medicationArrayList;
    private Context context;
    public MedicationListAdapter(Context context, ArrayList<Medication> medications, MedItemClickListener clickListener){
       this.context = context;
       this.medicationArrayList = medications;
       this.medItemClickListener = clickListener;
    }

    @Override
    public MedicationListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.medication_list, parent, false);
        return new MedicationListViewHolder(view) ;
    }

    @Override
    public void onBindViewHolder(MedicationListViewHolder holder, int position) {
        Medication med = medicationArrayList.get(position);
        holder.medName.setText(med.getName());
        holder.medType.setImageDrawable(ResourceUtils.getMedDrawable(context, med.getMedType()));
        String endDate = System.currentTimeMillis()>=DateUtils.getDateInMilliseconds(med.getEndDate())?"Completed":med.getEndDate();
        holder.endDate.setText(endDate);
    }

    @Override
    public int getItemCount() {
        return medicationArrayList.size();
    }

    public void swapData(ArrayList<Medication> medications){
        this.medicationArrayList = medications;
        notifyDataSetChanged();
    }

    public  class MedicationListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        @BindView(R.id.med_name)
        TextView medName;
        @BindView(R.id.med_type_imageView)
        ImageView medType;
        @BindView(R.id.med_end_date)
        TextView endDate;
        public MedicationListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            medItemClickListener.onMedItemClicked(medicationArrayList.get(getAdapterPosition()));
        }
    }
}
