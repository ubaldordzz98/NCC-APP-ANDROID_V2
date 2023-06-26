package com.janus.rodeo.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.janus.rodeo.R;
//import com.janus.rodeo.Activities.ZebraEMDK_Activity;

public class MessageDialog {
    private boolean response;
    private Listener listener;
    public Listener getListener() {
        return listener;
    }
    public void setListener(Listener listener) {
        this.listener = listener;
    }

    public void showMessageDialog(Activity activity, String msg, String type, boolean releaseView){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_message_dialog);

        ImageView image = (ImageView) dialog.findViewById(R.id.ivAlertIcon);
        TextView text = (TextView) dialog.findViewById(R.id.tvDialog);
        switch (type){
            case "Error":
                image.setImageResource(R.drawable.baseline_error_24);
                break;
            case "Warning":
                image.setImageResource(R.drawable.baseline_warning_24);
                break;
            case "Ok":
                image.setImageResource(R.drawable.baseline_check_circle_outline_green_500_24dp);
                break;
            default:
                image.setImageResource(R.drawable.nucor_tp_logo_2);
                break;
        }

        text.setText(msg);

        Button dialogButton = (Button) dialog.findViewById(R.id.btnDialog);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturnValue("Ok");
                if(releaseView == false){
                    //ZebraEMDK_Activity.isInReleaseScreen=false;
                }
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showAskDialog(Activity activity, String msg,String type){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_ask_user_dialog);

        ImageView image = (ImageView) dialog.findViewById(R.id.ivAlertAskIcon);
        TextView text = (TextView) dialog.findViewById(R.id.tvAskDialog);
        switch (type){
            case "Error":
                image.setImageResource(R.drawable.baseline_error_24);
                break;
            case "Warning":
                image.setImageResource(R.drawable.baseline_warning_24);
                break;
            case "Ok":
                image.setImageResource(R.drawable.baseline_done_24);
                break;
            default:
                image.setImageResource(R.drawable.nucor_tp_logo_2);
                break;
        }
        text.setText(msg);

        Button okButton = (Button) dialog.findViewById(R.id.btnOkDialog);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnCancelDialog);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturnValue("Ok");
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturnValue("CANCEL");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void showAskDialogInfo(Activity activity, String msg,String type){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_user_dialog);

        ImageView image = (ImageView) dialog.findViewById(R.id.ivAlertAskIcon);
        TextView text = (TextView) dialog.findViewById(R.id.tvDialogModal);
        switch (type){
            case "Error":
                image.setImageResource(R.drawable.baseline_error_24);
                break;
            case "Warning":
                image.setImageResource(R.drawable.baseline_warning_24);
                break;
            case "Ok":
                image.setImageResource(R.drawable.baseline_done_24);
                break;
            default:
                image.setImageResource(R.drawable.nucor_tp_logo_2);
                break;
        }
        text.setText(msg);

        Button okButton = (Button) dialog.findViewById(R.id.btnOkDialogModal);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturnValue("Ok");
                dialog.dismiss();

            }
        });
        dialog.show();
    }

    public void showInventorySendDialog(Activity activity, String msg,String type){
        final Dialog dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.layout_inventory_send_dialog);

        ImageView image = (ImageView) dialog.findViewById(R.id.ivAlertSendIcon);
        TextView text = (TextView) dialog.findViewById(R.id.tvSendDialog);
        EditText editText = (EditText) dialog.findViewById(R.id.etWriteSendDialog);
        Button okButton = (Button) dialog.findViewById(R.id.btnSendOkDialog);
        Button cancelButton = (Button) dialog.findViewById(R.id.btnSendCancelDialog);

        switch (type){
            case "Error":
                image.setImageResource(R.drawable.baseline_error_24);
                break;
            case "Warning":
                image.setImageResource(R.drawable.baseline_warning_24);
                break;
            case "Ok":
                image.setImageResource(R.drawable.baseline_done_24);
                break;
            default:
                image.setImageResource(R.drawable.nucor_tp_logo_2);
                break;
        }
        text.setText(msg);

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturnValue(editText.getText().toString());
                dialog.dismiss();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onReturnValue("CANCEL");
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public  void CloseDialog(Activity activity){
        final Dialog dialog = new Dialog(activity);
        dialog.dismiss();
    }

    public void FullScreenMethod(Activity activity){
        if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            MessageDialog m= new MessageDialog();
            View v2 = activity.getWindow().getDecorView();
            v2.setSystemUiVisibility(View.GONE);
        } else if(Build.VERSION.SDK_INT >= 19) {
            Window g =  activity.getWindow();
            View decorView = g.getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decorView.setSystemUiVisibility(uiOptions);
        }
    }

}