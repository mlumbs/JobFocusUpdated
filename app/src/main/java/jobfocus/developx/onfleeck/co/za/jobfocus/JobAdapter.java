package jobfocus.developx.onfleeck.co.za.jobfocus;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import data.DatabaseCrud;
import data.JobContracts;


public class JobAdapter extends RecyclerView.Adapter<JobAdapter.cHolder> {
    @Override
    public int getItemCount()
    {
        if(null==mCurso)return 0;

        return mCurso.getCount();
    }

    private Cursor mCurso;
    private Context mContext;
    FragmentManager fragmentManager;
    CustomDialogFragment newFragment ;
    Boolean istab;
    public class cHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public final TextView company;
        public final TextView position ;
        public final TextView face;
        public final TextView face2;


        public cHolder(View view)
        {super(view);
            company=(TextView) view.findViewById(R.id.c);
            position=(TextView) view.findViewById(R.id.po);
            face=(TextView) view.findViewById(R.id.f);
            face2=(TextView) view.findViewById(R.id.f2);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapter_posotion = getAdapterPosition();
            mCurso.moveToPosition(adapter_posotion);
            int ID_CULUMN_INDEX = mCurso.getColumnIndex(JobContracts.JobEntry._ID);
            int p = mCurso.getInt(ID_CULUMN_INDEX);
            if(mContext.getResources().getBoolean(R.bool.is_tab_)){
                Bundle arguments = new Bundle();
                arguments.putParcelable(CustomDialogFragment.DETAIL_URI_TAB,JobContracts.JobEntry.BuildJobId(p+""));
                try {
                    newFragment.setArguments(arguments);
                    newFragment.show(fragmentManager, "dialog");
                }catch (Exception e){
                    Log.e("Error","The dialogue is already active");
                }

            }else {
                Intent intent = new Intent(mContext, JobDetails.class)
                        .setData(JobContracts.JobEntry.BuildJobId(p+""));
                mContext.startActivity(intent);
            }

        }


    }


    public JobAdapter(Context c) {
        mContext=c;
        FragmentActivity a;
        a=(FragmentActivity)mContext;
        fragmentManager = a.getSupportFragmentManager();
        newFragment = new CustomDialogFragment();
        istab=c.getResources().getBoolean(R.bool.is_tab_);
        a=a;
        //a=(Activity)mContext;
        // Roboto = Typeface.createFromAsset((a.getAssets()),"Roboto-Bold.ttf");
        //Roboto =Helper.mFont(2,c);

    }

    @Override
    public cHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v;

        int layoutId0 =R.layout.each_card;
        int layoutId1 =R.layout.each_card_tab_port;
        int layoutId2=R.layout.each_card_tab_land;


        if (istab) { //check if it a tab then ....if it a tab then check if it potrait or landscape


            if(!mContext.getResources().getBoolean(R.bool.is_tab_land_)){
                Log.v("tablet","Tablet Potrait");
                v = LayoutInflater.from(parent.getContext())
                        .inflate(layoutId1, parent, false);
                v.setFocusable(true);



            }
            else{
                Log.v("tablet","Tablet Landscape");
                v = LayoutInflater.from(parent.getContext())
                        .inflate(layoutId2, parent, false);
                v.setFocusable(true);

            }


        }
        else{
            Log.v("tablet","Phone it is");
             v = LayoutInflater.from(parent.getContext())
                    .inflate(layoutId0, parent, false);


        }
        return new cHolder(v);
    }

    @Override
    public void onBindViewHolder(cHolder holder, int position) {
        mCurso.moveToPosition(position);

        holder.company.setText(mCurso.getString(List_recycle.COL_C));
        holder.position.setText(Html.fromHtml(mCurso.getString(List_recycle.COL_F2)));
        holder.face.setText(mCurso.getString(List_recycle.COL_F));
        //holder.face2.setText(mCurso.getString(List_recycle.COL_F2));

    }

    public void swapCursor(Cursor newCursor){
        mCurso =newCursor;
        notifyDataSetChanged();
    }


    @Override
    public int getItemViewType(int position) {
       // return super.getItemViewType(position);
        //return (position == 0 ) ? 0 : 1;
        return position;
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


}
