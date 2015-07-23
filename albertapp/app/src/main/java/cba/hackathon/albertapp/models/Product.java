package cba.hackathon.albertapp.models;

import java.util.ArrayList;

public class Product {
    public String title;
    public int id;
    public String createdAt;
    public String updatedAt;
    public String type;
    public String permalink;
    public String sku;
    public Float price;
    public ArrayList<Image> images;
    public int stockQuantity;
    public boolean inStock;

    public String getImagePath(){
        return this.images.get(0).src;
    }

    private class Image {
        public int id;
        public String src;
    }
}
