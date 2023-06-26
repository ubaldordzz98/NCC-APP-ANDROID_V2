package com.janus.rodeo.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.janus.rodeo.R;
import com.janus.rodeo.Models.ShRelease;
import java.util.List;

public class ReleaseAdapter extends BaseAdapter {
    private Context context;
    private  int layout;
    private List<ShRelease>  releases_list;

    public ReleaseAdapter(Context context, int layout, List<ShRelease> releases_list) {
        this.context = context;
        this.layout = layout;
        this.releases_list = releases_list;
    }

    @Override
    public int getCount() {
        return  this.releases_list.size();
    }

    @Override
    public Object getItem(int position) {
        return this.releases_list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v= convertView;
        LayoutInflater layoutInflater= LayoutInflater.from(this.context);
        v=layoutInflater.inflate(R.layout.list_releases, null);
        ShRelease _currentRel=this.releases_list.get(position);
        TextView _releaseNum=(TextView) v.findViewById(R.id.rl_number_list);
        ImageView _imgTransport=(ImageView) v.findViewById(R.id.rl_img_transport);
        TextView _txt_numb_coils=(TextView) v.findViewById(R.id.rl_num_coils);
        TextView _txt_status_description =(TextView) v.findViewById(R.id.rl_st_description);
        _releaseNum.setText(_currentRel.getReleaseNumber());
        _txt_numb_coils.setText(_currentRel.getCoils());
        if(_currentRel.getTransportType()==1) {
            _imgTransport.setBackgroundResource(R.drawable.train);
        } else{
            _imgTransport.setBackgroundResource(R.drawable.truck);
        }

        _txt_status_description.setText(_currentRel.getStatusDescription());
        int colorBack= Color.parseColor(_currentRel.getBackgroundStatus());
        int colorText=Color.parseColor(_currentRel.getForegroundStatus());
        _txt_status_description.setTextColor(colorText);
        GradientDrawable shape = new GradientDrawable();
        shape.setCornerRadius(14);
        shape.setColor(colorBack);
        _txt_status_description.setBackground(shape);
        return  v;
    }

}