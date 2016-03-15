package com.tobin.fotofetcher.RecyclerViewStuff;

public class DataObject {
    private String imageName;
    private String tags;
    private String url;
    private String uniqueId;

    public DataObject (String imageName, String tags, String url,String uniqueId){
        this.imageName = imageName;
        this.tags = tags;
        this.url = url;
        this.uniqueId=uniqueId;
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
    public void setUniqueId(String uniqueId){this.uniqueId=uniqueId;}
    public String getUniqueId(){return uniqueId;}

}