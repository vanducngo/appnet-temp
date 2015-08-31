package challenges.sutrix.androidappnetclient.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.sutrix.androidappnetclient.model.PostModel;
import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.utils.ImageRoundedTransformation;

/**
 * Created by root on 19/05/2015.
 */
public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {
    private ArrayList<PostModel> mDataSet;
    private Context mContext;

    //Constructor
    public PostAdapter(Context context,ArrayList<PostModel> myDataSet) {
        mDataSet = myDataSet;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // create a new view
        View v = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.recent_post_adapter, viewGroup, false);
        ViewHolder rViewHolder = new ViewHolder(v);
        return rViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        PostModel tPostModel = mDataSet.get(i);

        Log.i("Avatar ", tPostModel.getUser().getAvatar_image().getUrl());

        //Load avatar with picasso lib
        Picasso.with(mContext).load(tPostModel.getUser().getAvatar_image().getUrl()).transform(new ImageRoundedTransformation(20, 0)).into(viewHolder.mIvAvatar);

        viewHolder.mTvUserName.setText(tPostModel.getUser().getUsername());
        viewHolder.mTvText.setText(Html.fromHtml(tPostModel.getHtml()));
    }

    @Override
    public int getItemCount() {
        return mDataSet.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mTvText;
        public ImageView mIvAvatar;
        public TextView mTvUserName;

        public ViewHolder(View view) {
            super(view);
            this.mTvText = (TextView)view.findViewById(R.id.adapter_post_text);
            this.mTvUserName = (TextView)view.findViewById(R.id.adapter_user_name);
            this.mIvAvatar = (ImageView)view.findViewById(R.id.adapter_avatar);
        }
    }


}
