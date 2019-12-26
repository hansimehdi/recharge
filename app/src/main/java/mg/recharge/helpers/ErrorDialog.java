package mg.recharge.helpers;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import mg.recharge.R;


public class ErrorDialog{
    TextView message;
    Button dimissButton;
    Context context;
    Dialog dialog;

    public ErrorDialog(Context context) {
        this.context = context;
        this.dialog= new Dialog(context);
        this.dialog.setContentView(R.layout.error_dialog);
    }

    public void setMessage(int error){
        this.message= this.dialog.findViewById(R.id.errorText);
        this.message.setText(error);
    }

    public void show(){
        this.dimissButton = this.dialog.findViewById(R.id.ErrorButton);
        this.dimissButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        this.dialog.show();
    }

}
