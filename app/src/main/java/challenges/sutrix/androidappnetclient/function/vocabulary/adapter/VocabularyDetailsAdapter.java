package challenges.sutrix.androidappnetclient.function.vocabulary.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.function.vocabulary.listener.RememberedCheckChangeListener;
import challenges.sutrix.androidappnetclient.function.vocabulary.model.VocabularyModel;
import challenges.sutrix.androidappnetclient.utils.GeneralUtils;
import challenges.sutrix.androidappnetclient.utils.SecurityUtils;

/**
 * Created by root on 31/08/2015.
 */
public class VocabularyDetailsAdapter extends BaseAdapter {
    private final String TAG = "VocabularyDetailsAdapter";
    private Activity mContext;
    private List<VocabularyModel> mVocabularyList;
    private RememberedCheckChangeListener mListener;

    public VocabularyDetailsAdapter(Activity sContext, List<VocabularyModel> sVocabularyCategoryList, RememberedCheckChangeListener sListener) {
        this.mContext = sContext;
        this.mVocabularyList = sVocabularyCategoryList;
        this.mListener = sListener;
    }

    @Override
    public int getCount() {
        return mVocabularyList.size();
    }

    @Override
    public Object getItem(int i) {
        return mVocabularyList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View sView, ViewGroup sParent) {
        final ViewHolder tViewHolder;

        if (sView == null) {
            LayoutInflater inflater = mContext.getLayoutInflater();
            sView = inflater.inflate(R.layout.vocabulary_details_item_view,
                    sParent, false);
            tViewHolder = new ViewHolder();
            tViewHolder.mTvName = (TextView) sView
                    .findViewById(R.id.tv_vocabulary_details_name_item);
            tViewHolder.mTvMeaning = (TextView) sView
                    .findViewById(R.id.tv_vocabulary_details_meaning_item);
            tViewHolder.mIvImage = (ImageView) sView
                    .findViewById(R.id.iv_vocabulary_details_image_item);

            tViewHolder.mCbRemember = (CheckBox) sView
                    .findViewById(R.id.cb_vocabulary_details_remembered_item_item);

            sView.setTag(tViewHolder);
        } else {
            tViewHolder = (ViewHolder) sView.getTag();
        }

        tViewHolder.mTvName.setText(SecurityUtils.decodeString(mVocabularyList.get(position).getWord()));
        tViewHolder.mTvMeaning.setText(SecurityUtils.decodeString(mVocabularyList.get(position).getShortMeanVietnamese()));
        Picasso.with(mContext).load(R.drawable.contract).into(tViewHolder.mIvImage);


        tViewHolder.mCbRemember.setOnCheckedChangeListener(null);

        if (mVocabularyList.get(position).isRemember()) {
            tViewHolder.mCbRemember.setChecked(true);
            GeneralUtils.showLog(TAG, "Is remember true");
        } else {
            tViewHolder.mCbRemember.setChecked(false);
            GeneralUtils.showLog(TAG, "Is remember false");
        }

        tViewHolder.mCbRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                mListener.onRememberedCheckChangeListener(isChecked, position);
            }
        });

        return sView;
    }

    static class ViewHolder {
        TextView mTvName;
        TextView mTvMeaning;
        ImageView mIvImage;
        CheckBox mCbRemember;
    }
}
