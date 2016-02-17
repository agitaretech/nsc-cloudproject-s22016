package com.tobin.fotofetcher;

import android.provider.ContactsContract;

import com.tobin.fotofetcher.RecyclerViewStuff.DataObject;

import java.util.ArrayList;

/**
 * Created by Tobin on 2/16/16.
 */
public class AsyncObjectList {

    private ArrayList<DataObject> dummyData;
    private static AsyncObjectList instance = null;

    private AsyncObjectList (){
        dummyData = fillDummyData();
    }

    public static AsyncObjectList getInstance(){
        if (instance == null)
            return new AsyncObjectList();
        return instance;
    }

    public ArrayList<DataObject> getList(){
        return dummyData;
    }

    public DataObject getDataObject(int position){
        return dummyData.get(position);
    }

    public void updateObject(int position, DataObject object){
        dummyData.remove(position);
        dummyData.add(position, object);
    }

    public void updateList(ArrayList<DataObject> list){
        dummyData = list;
    }

    private ArrayList<DataObject> fillDummyData() {
        ArrayList<DataObject> imageDataList = new ArrayList<>();
        DataObject obj1 = new DataObject(
                "name1",
                "1tag1, 1tag2, 1tag3, 1tag4, 1tag5, 1tag6, 1tag7, 1tag8, 1tag9, 1tag10, 1tag11, 1tag12, 1tag13",
                "http://images7.memedroid.com/avatars/AVATAR65/55768b80b15aa.jpeg");
        DataObject obj2 = new DataObject(
                "name2",
                "2tag1, 2tag2, 2tag3, 2tag4, 2tag5, 2tag6, 2tag7, 2tag8, 2tag9, 2tag10, 2tag11, 2tag12, 2tag13",
                "http://img.ifcdn.com/images/b991ea55f357101d895250308987b00356df44affb126810bc9e9099f5612747_1.jpg");
        DataObject obj3 = new DataObject(
                "name3",
                "3tag1, 3tag2, 3tag3, 3tag4, 3tag5, 3tag6, 3tag7, 3tag8, 3tag9, 3tag10, 3tag11, 3tag12, 3tag13",
                "http://api.ning.com/files/kV4MbYiv7oRidf3AaaNXC1F7274JDTgNC7q4lO6oYUO7abgYq0aCWql2h7nSYfvxm4itcQrYRGJ8mqZ2GEvPND4z847eV1aX/1082034191.jpeg");
        DataObject obj4 = new DataObject(
                "name4",
                "4tag1, 4tag2, 4tag3, 4tag4, 4tag5, 4tag6, 4tag7, 4tag8, 4tag9, 4tag10, 4tag11, 4tag12, 4tag13",
                "http://api.ning.com/files/kV4MbYiv7oRLu*fzoHWERwweMGG3KsFu0Huyl8DEXs8NYcjPhS1o-9FRWPsa9xovOxDgB9k0-dS59hv*6hx-fB86JdUkP1-Y/1082034107.jpeg");
        DataObject obj5 = new DataObject(
                "name5",
                "5tag1, 5tag2, 5tag3, 5tag4, 5tag5, 5tag6, 5tag7, 5tag8, 5tag9, 5tag10, 5tag11, 5tag12, 5tag13",
                "http://media.creativebloq.futurecdn.net/sites/creativebloq.com/files/images/2015/07/bart10.jpg");
        DataObject obj6 = new DataObject("Hyperion",
                "hype1, hype2, hype3",
                "https://upload.wikimedia.org/wikipedia/commons/thumb/e/ef/Hyperion_false_color.jpg/777px-Hyperion_false_color.jpg");
        DataObject obj7 = new DataObject("Enceladys",
                "en1, en2, en3",
                "http://ichef-1.bbci.co.uk/news/660/cpsprodpb/CE15/production/_85575725_enc.jpg");
        DataObject obj8 = new DataObject("Mimas",
                "mimas1, mimas2 mimas3",
                "http://vignette4.wikia.nocookie.net/terraforming/images/3/3e/Mimas.jpg/revision/latest?cb=20120515031016");
        DataObject obj9 = new DataObject("Hexagonal Storm",
                "hex1, hex2, hex3",
                "http://i.dailymail.co.uk/i/pix/2014/04/08/article-0-1CF20C2800000578-359_634x629.jpg");
        DataObject obj10 = new DataObject("Saturn",
                "saturn1, saturn2, saturn3",
                "http://www.unmuseum.org/7wonders/saturn_planet.jpg");
        imageDataList.add(0, obj1);
        imageDataList.add(1, obj2);
        imageDataList.add(2, obj3);
        imageDataList.add(3, obj4);
        imageDataList.add(4, obj5);
        imageDataList.add(5, obj6);
        imageDataList.add(6, obj7);
        imageDataList.add(7, obj8);
        imageDataList.add(8, obj9);
        imageDataList.add(9, obj10);
        return imageDataList;
    }

}
