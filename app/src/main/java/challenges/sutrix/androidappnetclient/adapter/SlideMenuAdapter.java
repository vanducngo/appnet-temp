package challenges.sutrix.androidappnetclient.adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import challenges.sutrix.androidappnetclient.R;
import challenges.sutrix.androidappnetclient.utils.PreferenceUtils;
import challenges.sutrix.androidappnetclient.fragment.CountryMusicFragment;
import challenges.sutrix.androidappnetclient.fragment.HomeFragment;
import challenges.sutrix.androidappnetclient.fragment.OtherFragment;
import challenges.sutrix.androidappnetclient.fragment.PopFragment;
import challenges.sutrix.androidappnetclient.fragment.RockAndRollFragment;

public class SlideMenuAdapter
        extends RecyclerView.Adapter<SlideMenuAdapter.ViewHolder> {

    private Context mContext;

    private static final int TYPE_HEADER = 0;  // Declaring Variable to Understand which View is being worked on
    // IF the view under inflation and population is header or Item
    private static final int TYPE_ITEM = 1;

    private String mNavTitles[]; // String Array to store the passed titles Value from MainActivity.java
    private int mIcons[];       // Int Array to store the passed icons resource value from MainActivity.java


    private int selected;

    public void setSelected(int selected) {
        this.selected = selected;
        notifyDataSetChanged();

    }

    public void setSelectedOnBackPress(Fragment tFragment) {
        int tCurrentFragmentPosition = 0;
        if(tFragment instanceof HomeFragment){
            tCurrentFragmentPosition = 1;
        }else if(tFragment instanceof PopFragment){
            tCurrentFragmentPosition = 2;
        }else if(tFragment instanceof RockAndRollFragment){
            tCurrentFragmentPosition = 3;
        }else if(tFragment instanceof CountryMusicFragment){
            tCurrentFragmentPosition = 4;
        }else if(tFragment instanceof OtherFragment){
            tCurrentFragmentPosition = 5;
        }

        setSelected(tCurrentFragmentPosition);
    }


    public void refreshSlideMenu() {
        notifyDataSetChanged();
    }

    // Creating a ViewHolder which extends the RecyclerView View Holder
    // ViewHolder are used to to store the inflated views in order to recycle them

    public class ViewHolder extends RecyclerView.ViewHolder {
        int HolderId;

        TextView textView;
        ImageView imageView;
        ImageView profile;
        TextView Name;
        TextView email;


        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        public ViewHolder(View itemView, int ViewType) {                 // Creating ViewHolder Constructor with View and viewType As a parameter
            super(itemView);


            // Here we set the appropriate view in accordance with the the view type as passed when the holder object is created

            if (ViewType == TYPE_ITEM) {
                textView = (TextView) itemView.findViewById(R.id.rowText); // Creating TextView object with the id of textView from item_row.xml
                imageView = (ImageView) itemView.findViewById(R.id.rowIcon);// Creating ImageView object with the id of ImageView from item_row.xml
                HolderId = 1;                                               // setting holder id as 1 as the object being populated are of type item row
            } else {
                Name = (TextView) itemView.findViewById(R.id.name);         // Creating Text View object from header.xml for name
                email = (TextView) itemView.findViewById(R.id.email);       // Creating Text View object from header.xml for email
                profile = (ImageView) itemView.findViewById(R.id.circleView);// Creating Image view object from header.xml for profile pic
                HolderId = 0;                                                // Setting holder id = 0 as the object being populated are of type header view
            }
        }
    }


    public SlideMenuAdapter(Context context, String Titles[], int Icons[]) { // MyAdapter Constructor with titles and icons parameter
        // titles, icons, name, email, profile pic are passed from the main activity as we
        mNavTitles = Titles;                //have seen earlier
        mIcons = Icons;
        //in adapter
        this.mContext = context;


    }


    //Below first we ovverride the method onCreateViewHolder which is called when the ViewHolder is
    //Created, In this method we inflate the item_row.xml layout if the viewType is Type_ITEM or else we inflate header.xml
    // if the viewType is TYPE_HEADER
    // and pass it to the view holder

    @Override
    public SlideMenuAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.navigation_item, parent, false); //Inflating the layout

            return  new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view

            //inflate your layout and pass it to view holder

        } else if (viewType == TYPE_HEADER) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header, parent, false); //Inflating the layout

            return  new ViewHolder(v, viewType); //Creating ViewHolder and passing the object of type view
        }
        return null;

    }


    //Next we override a method which is called when the item in a row is needed to be displayed, here the int position
    // Tells us item at which position is being constructed to be displayed and the holder id of the holder object tell us
    // which view type is being created 1 for item row
    @Override
    public void onBindViewHolder(SlideMenuAdapter.ViewHolder holder, int position) {
        if (holder.HolderId == 1) {                              // as the list view is going to be called after the header view so we decrement the
            // position by 1 and pass it to the holder while setting the text and image
            holder.textView.setText(mNavTitles[position - 1]); // Setting the Text with the array of our Titles
            holder.imageView.setImageResource(mIcons[position - 1]);// Setting the image with array of our icons
            if (selected == position) {
                holder.itemView.setActivated(true);
            }else{
                holder.itemView.setActivated(false);
            }
        } else {

            String tFbName = PreferenceUtils.getString(mContext, PreferenceUtils.PREF_LOGIN_NAME, mContext.getString(R.string.anonymous));
            String tFbEmail = PreferenceUtils.getString(mContext, PreferenceUtils.PREF_LOGIN_EMAIL, mContext.getString(R.string.email_anonymous));
            String tFbImageUrl = PreferenceUtils.getString(mContext, PreferenceUtils.PREF_USER_IMAGE, "");
            if(tFbImageUrl!="") {
                Picasso.with(mContext).load(tFbImageUrl).into(holder.profile);
            }else{
                holder.profile.setImageResource(R.drawable.ic_anonymous);
            }
            // Similarly we set the resources for header view
            holder.Name.setText(tFbName);
            holder.email.setText(tFbEmail);
        }
    }

    // This method returns the number of items present in the list
    @Override
    public int getItemCount() {
        return mNavTitles.length + 1; // the number of items in the list will be +1 the titles including the header view.
    }


    // Witht the following method we check what type of view is being passed
    @Override
    public int getItemViewType(int position) {
        if (isPositionHeader(position))
            return TYPE_HEADER;

        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }
}
