package com.richdroid.recyclerviewexample.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.richdroid.recyclerviewexample.R;

import java.util.List;

import com.richdroid.recyclerviewexample.model.ContactDetail;

/**
 * Created by richa.khanna on 3/18/16.
 */
public class ContactDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static Context mContext;
    private static List<ContactDetail> mDatasetList;
    private static final int PERSON_CONTACT_DETAIL = 0;
    private static final int ANIMAL_CONTACT_DETAIL = 1;
    // Allows to remember the last item shown on screen
    private int lastAnimatedItemPosition = -1;


    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactDetailAdapter(Context context, List<ContactDetail> datasetList) {
        mContext = context;
        mDatasetList = datasetList;
    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ContactDetailViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {
        private TextView mTVName;
        private TextView mTVEmail;
        private TextView mTVLocation;
        private TextView mTVType;
        private LinearLayout mContainer;


        public ContactDetailViewHolder(View view) {
            super(view);
            this.mTVName = (TextView) view.findViewById(R.id.tv_name);
            this.mTVEmail = (TextView) view.findViewById(R.id.tv_email);
            this.mTVLocation = (TextView) view.findViewById(R.id.tv_location);
            this.mTVType = (TextView) view.findViewById(R.id.tv_type);
            this.mContainer = (LinearLayout) view.findViewById(R.id.contact_layout_container);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int itemPosition = getAdapterPosition();
            Toast.makeText(mContext, "You clicked at position " + itemPosition +
                    " on contact details of : " +
                    mDatasetList.get(itemPosition).getName(), Toast.LENGTH_SHORT).show();
        }
    }


    // Create new views (invoked by the layout manager)
    @Override
    public ContactDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_detail_view, parent, false);

        switch (viewType) {
            case PERSON_CONTACT_DETAIL:
            case ANIMAL_CONTACT_DETAIL:
                return new ContactDetailViewHolder(view);
        }
        return null;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ContactDetailViewHolder cusHolder = (ContactDetailViewHolder) holder;
        cusHolder.mTVName.setText(mDatasetList.get(position).getName());
        cusHolder.mTVEmail.setText(mDatasetList.get(position).getEmail());
        cusHolder.mTVLocation.setText(mDatasetList.get(position).getLocation());

        switch (getItemViewType(position)) {
            case PERSON_CONTACT_DETAIL:
                cusHolder.mTVType.setText("Person");
                break;
            case ANIMAL_CONTACT_DETAIL:
                cusHolder.mTVType.setText("Animal");
                break;
        }

        setEnterAnimation(cusHolder.mContainer, position);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDatasetList.size();
    }

    @Override
    public int getItemViewType(int position) {
        ContactDetail contactDetail = mDatasetList.get(position);
        String contactType = contactDetail.getType();
        if (contactType.equalsIgnoreCase("Animal")) {
            return ANIMAL_CONTACT_DETAIL;
        }
        return PERSON_CONTACT_DETAIL;
    }

    private void setEnterAnimation(View viewToAnimate, int position) {
        // If the bound view wasn't previously displayed on screen, it will be animated
        if (position > lastAnimatedItemPosition) {
            Animation animation = AnimationUtils.loadAnimation(mContext, R.anim.translate_left);
            viewToAnimate.startAnimation(animation);
            lastAnimatedItemPosition = position;
        }
    }

    /**
     * The view could be reused while the animation is been happening.
     * In order to avoid that is recommendable to clear the animation when is detached.
     */
    @Override
    public void onViewDetachedFromWindow(final RecyclerView.ViewHolder holder) {
        ((ContactDetailViewHolder) holder).mContainer.clearAnimation();
    }
}
