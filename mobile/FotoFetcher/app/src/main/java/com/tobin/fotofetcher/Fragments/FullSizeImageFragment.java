package com.tobin.fotofetcher.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tobin.fotofetcher.Activities.HomeActivity;
import com.tobin.fotofetcher.Interface;
import com.tobin.fotofetcher.R;
import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;

public class FullSizeImageFragment extends Fragment {
    Button btn;
    LinearLayout ll;
    PopupWindow popupWindow;
    int positionInRecyclerView;
    String tags,name,url;
    String[] tagArray;
    Interface anInterface;
    DataObject object;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_full_size_photo, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Interface) {
            anInterface = (Interface) context;
        } else {
            throw new ClassCastException(context.toString()
                    + " must implement MyListFragment.OnItemSelectedListener");
        }
    }

    public void setObject(DataObject object, int position){
        this.object = object;


        this.url = object.getUrl();
        this.name = object.getImageName();
        this.tags = object.getTags();
        tagArray = tags.split(",");
        this.positionInRecyclerView = position;

        setName(name);
        displayTags(tagArray);
        setURL(url);
    }

//    public void setImageAttributes(String name, String tags, String url, int position) {
//        tagArray = tags.split(",");
//        setName(name);
//        displayTags(tagArray);
//        setURL(url);
//        this.url=url;
//        this.name=name;
//        this.positionInRecyclerView =position;
//        this.tags=tags;
//    }

    public void setName(String name) {
        TextView nameTextView = (TextView) getActivity().findViewById(R.id.image_name);
        nameTextView.setText(name);
    }

    public void setURL(final String url) {
        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.fullSizePhotoImageView);
        Picasso.with(getActivity()).load(url).into(imageView);
//        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //imagePopUp(url);
                    LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
                    View popupView = layoutInflater.inflate(R.layout.image_popup, null);
                    ImageView im = (ImageView) popupView.findViewById(R.id.popup_image);
                    Picasso.with(getActivity()).load(url).into(im);
                    popupWindow = new PopupWindow(
                            popupView,
                            ViewGroup.LayoutParams.MATCH_PARENT,
                            ViewGroup.LayoutParams.MATCH_PARENT);
                    im.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            popupWindow.dismiss();
                        }
                    });
                    popupWindow.showAsDropDown(im, 0, 0);

                }
            });
//        }
    }

    public void displayTags(String[] tags) {

        ll = (LinearLayout) getActivity().findViewById(R.id.tagContainer);
        ll.removeAllViews();
        int counter = 0;

// clear priviouse tags
        for (int i = 0; i < tagArray.length; i++) {
            btn = new Button(getActivity());
            btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            btn.setId(i);
            btn.setTag(i);
            btn.setText(tagArray[counter]);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayAlertDialogForEditTag(v.getId(), v);
                }
            });
            ll.addView(btn);
            counter++;
        }
        btn = new Button(getActivity());
        btn.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        btn.setText("Add Text");
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayAlertDialogToAddTag();
            }
        });
        ll.addView(btn);
    }

    public void displayAlertDialogForEditTag(int number, View v) {
        Context context = getActivity();
        btn = (Button) v;
        String title = "Would you like to edit this tag ";
        String button1String = "Submit";
        String button2String = "Cancel";

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        final EditText input = new EditText(getActivity());
        input.setHint("Edit this tag");
        final int tagIndex = number;
        ad.setTitle(title + ( tagIndex + 1) + "?");
        ad.setView(input);

        ad.setNegativeButton(
                button1String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        tagArray[tagIndex]= input.getText().toString();
                        Log.d("fullfrag", "tag = " + tagArray[tagIndex]);
                        btn.setText(tagArray[tagIndex]);
                        String result="";
                        for(String tag: tagArray){
                            if(result == "") {
                                result = tag;
                            } else {
                                result += "," + tag;
                            }
                        }
                        object.setTags(result);
                        anInterface.updateTag(object, positionInRecyclerView);

//                        anInterface.itemClicked(positionInRecyclerView);


                    }
                }
        );

        ad.setPositiveButton(
                button2String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // Do nothing for cancel.                    }
                    }
                }
        );
        ad.show();
    }

    public void displayAlertDialogToAddTag() {
        Context context = getActivity();
        String title = "Would you like to add a tag?";
        String button1String = "Add tag";
        String button2String = "Cancel";
        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        final EditText input = new EditText(getActivity());
        btn = new Button(getActivity());
        input.setHint("Add a tag");
        ad.setTitle(title);
        ad.setView(input);
        ad.setNegativeButton(
                button1String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        String output = input.getText().toString();
                        btn.setText(output);
                        ll.addView(btn);

//                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("added_buttons", Context.MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("added_button_text", output);
//                        editor.commit();
                    }
                }
        );

        ad.setPositiveButton(
                button2String,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // Do nothing for cancel.
                    }
                }
        );
        ad.show();
    }
}