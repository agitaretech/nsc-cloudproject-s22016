package com.tobin.fotofetcher.RecyclerViewStuff;

public class DataObject {
    private String imageName;
    private String tags;
    private String url;

    public DataObject (String text1, String text2, String text3){
        imageName = text1;
        tags = text2;
        url = text3;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getUrl(){
        return url;
    }

    public void setUrl(String url){
        this.url = url;
    }

}