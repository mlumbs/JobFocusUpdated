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
    public final static String EXTRA_MESSAGE = "jobfocus.developx.onfleeck.co.za.jobfocus.EXTRA";
    public final static String EXTRA_MESSAGE_TITLE = "jobfocus.developx.onfleeck.co.za.jobfocus.EXTRA__TITLE";

    private boolean mfront;
    public class cHolder extends RecyclerView.ViewHolder implements View.OnClickListener{


        public final TextView company;
        public final TextView position ;
        public final TextView face;
        public final TextView face2;
        public final TextView Title ;
        public final TextView Body ;


        public cHolder(View view)
        {
            super(view);
            company=(TextView) view.findViewById(R.id.c);
            position=(TextView) view.findViewById(R.id.po);
            face=(TextView) view.findViewById(R.id.f);
            face2=(TextView) view.findViewById(R.id.f2);
            Title= (TextView) view.findViewById(R.id.Title_Notification);
            Body= (TextView) view.findViewById(R.id.Body_Notification);
            view.setOnClickListener(this);
        }


        @Override
        public void onClick(View v) {
            int adapter_posotion = getAdapterPosition();
            mCurso.moveToPosition(adapter_posotion);
            int ID_CULUMN_INDEX = mCurso.getColumnIndex(JobContracts.JobEntry._ID);
            int p = mCurso.getInt(ID_CULUMN_INDEX);
            String link= mCurso.getString(JobDetailFragment.COL_entryid);
            String title= mCurso.getString(List_recycle.COL_C);

            if(mContext.getResources().getBoolean(R.bool.is_tab_)){
                Bundle arguments = new Bundle();
                arguments.putParcelable(CustomDialogFragment.DETAIL_URI_TAB,JobContracts.JobEntry.BuildJobId(p+""));
                arguments.putString(EXTRA_MESSAGE,link);
                arguments.putString(EXTRA_MESSAGE_TITLE,title);
                try {
                    newFragment.setArguments(arguments);
                    newFragment.show(fragmentManager, "dialog");
                }catch (Exception e){
                    Log.e("Error","The dialogue is already active");
                }

            }else {
                Intent intent = new Intent(mContext, JobDetails.class)
                        .setData(JobContracts.JobEntry.BuildJobId(p+""))
                         .putExtra(EXTRA_MESSAGE,link)
                         .putExtra(EXTRA_MESSAGE_TITLE,title);

                mContext.startActivity(intent);
            }

        }


    }


    public JobAdapter(Context c, boolean front) {
        mContext=c;
        FragmentActivity a;
        a=(FragmentActivity)mContext;
        fragmentManager = a.getSupportFragmentManager();
        newFragment = new CustomDialogFragment();
        istab=c.getResources().getBoolean(R.bool.is_tab_);
        mfront=front;
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
        /*
         This injectected notification area which is always greater than all values or ids in a given list or Context
        There is one notification to be loaded and one notificaiton that exist in the server at a specific time\
        This is automitically deleted as a List notification(the notification within the list)
        data.DatabaseCrud.class will implement the method thats ads the notification in the database and also implement the
        method that delete the database notification location.
        AddNotification and RemoveNotification
        How does removeNotification function identify the Notificattion to be removed int the List ? well let the Notification id be 100 more thatn the the last entry as always and have extar attribute as always\
        The extars attribute will be the type or measures the priority i.e priority of ranking [decaprated]
         * If the priority is
         *   1 This is the Command to be issued
         *   2 This is the general list to be added or deleted
         *   3 This is the ranking attribute
         *
         *   Therefore a modified version does not need an extar attributes
         *   but it only recognises the first number according to the above attribute of ab entyID from the server
         */

        if(Integer.parseInt(mCurso.getString(List_recycle.COL_EXTRAS))==3&&mfront){
            holder.Title.setVisibility(View.VISIBLE);
            holder.Title.setText(mCurso.getString(List_recycle.COL_C));
            holder.Body.setVisibility(View.VISIBLE);
            holder.Body.setText(mCurso.getString(List_recycle.COL_PO));
            holder.company.setVisibility(View.GONE);
            holder.position.setVisibility(View.GONE);
            holder.face.setVisibility(View.GONE);
            holder.face2.setVisibility(View.GONE);
        }

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
