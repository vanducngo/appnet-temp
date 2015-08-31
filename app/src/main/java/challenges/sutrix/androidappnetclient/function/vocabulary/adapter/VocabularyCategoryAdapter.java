package challenges.sutrix.androidappnetclient.function.vocabulary.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyCategoryModel;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by root on 31/08/2015.
 */
public class VocabularyCategoryAdapter extends BaseAdapter {
    private Activity mContext;
    private ArrayList<VocabularyCategoryModel> mVocabularyCategoryList;

    public VocabularyCategoryAdapter(Activity sContext, ArrayList<VocabularyCategoryModel> sVocabularyCategoryList) {
        this.mContext = sContext;
        this.mVocabularyCategoryList = sVocabularyCategoryList;
    }

    @Override
    public int getCount() {
        return mVocabularyCategoryList.size();
    }

    @Override
    public Object getItem(int i) {
        return mVocabularyCategoryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View sView, ViewGroup sParent) {
        final ViewHolder tViewHolder;

        if (sView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            sView = inflater.inflate(R.layout.vocabulary_category_item_view,
                    sParent, false);
            tViewHolder = new ViewHolder();
            tViewHolder.mTvName = (TextView) sView
                    .findViewById(R.id.tv_vocabulary_category_name_item);
            tViewHolder.mTvMeaning = (TextView) sView
                    .findViewById(R.id.tv_vocabulary_category_meaning_item);
            tViewHolder.mIvImage = (CircleImageView) sView
                    .findViewById(R.id.iv_vocabulary_category_image_item);

            sView.setTag(tViewHolder);
        } else {
            tViewHolder = (ViewHolder) sView.getTag();
        }

        tViewHolder.mTvMeaning.setText(mVocabularyCategoryList.get(i).getName());
//        tViewHolder.mIvImage.setImageResource(R.drawable.contract);
        Picasso.with(mContext).load(R.drawable.contract).into(tViewHolder.mIvImage);

        return sView;
    }

    static class ViewHolder {
        TextView mTvName;
        TextView mTvMeaning;
        CircleImageView mIvImage;
    }
}
