package mg.recharge.adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import mg.recharge.R;
import mg.recharge.entities.CreditCode;

public class RechargeHistoryAdapter extends RecyclerView.Adapter<RechargeHistoryAdapter.ViewHolder> {
    private List<CreditCode> codeList;
    private Context context;

    public RechargeHistoryAdapter(List<CreditCode> codeList, Context context) {
        this.codeList = codeList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_template,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.creditCode.setText(this.codeList.get(position).getCode());
        holder.operator.setText(this.codeList.get(position).getOperator());
        holder.date.setText(this.codeList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return this.codeList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView creditCode,operator,date;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.creditCode=itemView.findViewById(R.id.historyCreditCode);
            this.operator=itemView.findViewById(R.id.historyOperator);
            this.date=itemView.findViewById(R.id.historyDate);
        }
    }
}
