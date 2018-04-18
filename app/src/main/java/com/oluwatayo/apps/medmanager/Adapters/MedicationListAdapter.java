package com.oluwatayo.apps.medmanager.Adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.oluwatayo.apps.medmanager.Data.Medication;
import com.oluwatayo.apps.medmanager.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class MedicationListAdapter extends RecyclerView.Adapter<MedicationListAdapter.MedicationListViewHolder> {

    private ArrayList<Medication> medicationArrayList;
    private Context context;
    public MedicationListAdapter(Context context, ArrayList<Medication> medications){
       this.context = context;
       this.medicationArrayList = medications;
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
        holder.medType.setImageDrawable(getMedDrawable(med.getMedType()));
        holder.nextUsage.setText("11:00am");
    }

    @Override
    public int getItemCount() {
        return medicationArrayList.size();
    }

    public void swapData(ArrayList<Medication> medications){
        this.medicationArrayList = medications;
        notifyDataSetChanged();
    }

    public  class MedicationListViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.med_name)
        TextView medName;
        @BindView(R.id.med_type_imageView)
        ImageView medType;
        @BindView(R.id.med_next_usage)
        TextView nextUsage;
        public MedicationListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    private Drawable getMedDrawable(String medType){
        int i;
        switch (medType){
            case "Tablet":
                i = R.drawable.ic_pill;
                break;
            case "Capsule":
                i = R.drawable.ic_pill_capsule;
                break;
            case "Syrup":
                i = R.drawable.ic_syrup;
                break;
            case "Injection":
                i = R.drawable.ic_anesthesia;
                break;
            default:
                i = R.drawable.ic_pill;

        }
        return context.getResources().getDrawable(i);
    }
}
