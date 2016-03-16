package com.tobin.fotofetcher.Fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tobin.fotofetcher.AsyncAndDB.AsyncObjectList;
import com.tobin.fotofetcher.Interface;
import com.tobin.fotofetcher.R;
import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;

public class FullSizeImageFragment extends Fragment {
    Button btn,addTagButton;

    public LinearLayout ll;
    public ScrollView sv;

    public static PopupWindow popupWindow;
    String tags, name, url;
    String[] tagArray;
    DataObject object;
    AsyncObjectList list;
    ProgressBar fullPhotoProgressBar;
    int position = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_full_size_photo, container, false);
        fullPhotoProgressBar = (ProgressBar) view.findViewById(R.id.full_photo_progressBar);
        sv = (ScrollView) view.findViewById(R.id.scrollView);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d("name", "here fullsize");

        if (savedInstanceState != null) {

            this.position = savedInstanceState.getInt("position");
        }
        Log.d("onViewCreated", "1111111111");
        Log.d("onViewCreated", position + "");

        list = AsyncObjectList.getInstance();
        setObject(position);
    }

    public void setObject(int position) {

        if(list.getList().size() == 0) {
            object = new DataObject("No pics to view", null, null);
            name = object.getImageName();
            this.position = position;
            setName(name);
        } else {
            object = list.getDataObject(position);
            url = object.getUrl();
            name = object.getImageName();
            tags = object.getTags();
            this.position = position;
            tagArray = tags.split(",");
            displayTags();
            setName(name);
            setURL(url);
        }


    }

    public void setName(String name) {
        TextView nameTextView = (TextView) getActivity().findViewById(R.id.image_name);
        nameTextView.setText(name);
    }

    public void setURL(final String url) {
        final ImageView imageView = (ImageView) getActivity().findViewById(R.id.fullSizePhotoImageView);
        fullPhotoProgressBar.setVisibility(View.VISIBLE);
        imageView.setVisibility(View.GONE);
        Picasso.with(getActivity()).load(url).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                fullPhotoProgressBar.setVisibility(View.GONE);
                imageView.setVisibility(View.VISIBLE);
            }

            @Override
            public void onError() {

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

    }

    public void displayTags() {

        LinearLayout allTagsContainer = (LinearLayout) getActivity().findViewById(R.id.all_tags_container);
//        allTagsContainer.removeAllViews();
        ll = (LinearLayout) getActivity().findViewById(R.id.tagContainer);
        LinearLayout addTagLayout = (LinearLayout) getActivity().findViewById(R.id.add_tag_container);
        addTagLayout.removeAllViews();
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
// if(addTagButton==null) {
            addTagButton = new Button(getActivity());
            addTagButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            addTagButton.setText("Add Text");
            addTagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    displayAlertDialogToAddTag();
                }
            });
//        ll.addView(btn);

            addTagLayout.addView(addTagButton);
//        }
    }

    public void displayAlertDialogForEditTag(int number, View v) {
        Context context = getActivity();
        btn = (Button) v;
        String title = "Would you like to edit this tag ";
        String submit = "Submit";
        String cancel = "Cancel";
        String delete = "Delete";

        AlertDialog.Builder ad = new AlertDialog.Builder(context);
        final EditText input = new EditText(getActivity());
        input.setHint("Edit this tag");
        final int tagIndex = number;
        ad.setTitle(title + (tagIndex + 1) + "?");
        ad.setView(input);

        ad.setNegativeButton(
                delete,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                      String result="";
                       for(int i=0;i<tagArray.length;i++){
                           if(i!=tagIndex) {
                               if(result==""){
                                   result=tagArray[i];
                               }else {
                                   result += ", "+tagArray[i];
                               }
                           }
                       }

                            object.setTags(result);
                            ll.removeViewAt(tagIndex);

                            Interface anInterface = (Interface) getActivity();
                            anInterface.updateTag(position);


                    }
                }
        );

        ad.setNeutralButton(
                cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // Do nothing for cancel.                    }
                    }
                }
        );

        ad.setPositiveButton(
                submit,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        tagArray[tagIndex] = input.getText().toString();
                        btn.setText(tagArray[tagIndex]);
                        String result = "";
                        for (String tag : tagArray) {
                            if (result.equals("")) {
                                result = tag;
                            } else {
                                result += "," + tag;
                            }
                        }
                        object.setTags(result);
                        Interface anInterface = (Interface) getActivity();
                        anInterface.updateTag(position);
                    }
                }
        );
        ad.show();
        list.updateObject(position, object);
    }

    public void displayAlertDialogToAddTag() {
        String title = "Would you like to add a tag?";
        String addTag = "Add tag";
        String cancelAddTag = "Cancel";
        AlertDialog.Builder ad = new AlertDialog.Builder(getActivity());
        final EditText input = new EditText(getActivity());
        btn = new Button(getActivity());
        input.setHint("Add a tag");
        ad.setTitle(title);
        ad.setView(input);

        ad.setNegativeButton(
                cancelAddTag,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {
                        // Do nothing for cancel.
                    }
                }
        );

        ad.setPositiveButton(
                addTag,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int arg1) {

                        String output = input.getText().toString();

                        if(!output.isEmpty()) {
                            btn.setText(output);
                            object.setTags(tags + ", " + output);
                            ll.addView(btn);

                            Interface anInterface = (Interface) getActivity();
                            anInterface.updateTag(position);
                        } else {
                            Toast.makeText(getActivity(), "Tag must contain valid characters", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
        );
        ad.show();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }
}