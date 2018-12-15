package com.sharonaapp.sharona.helper;

import java.util.ArrayList;
import java.util.Collections;

public class ConfigDataGen {


    private static ArrayList<String> getTypeArrayList()
    {
        ArrayList<String> typesArrayList = new ArrayList<>();
        typesArrayList.add("Shoes");
        typesArrayList.add("Watches");
        typesArrayList.add("Skirts");
        typesArrayList.add("Shorts");
        typesArrayList.add("T-shirts");
        typesArrayList.add("Polo shirts");
        typesArrayList.add("Scarfs");
        typesArrayList.add("Hats");
        typesArrayList.add("Gloves");
        typesArrayList.add("Sweater");
        typesArrayList.add("Hoody");
        typesArrayList.add("Blazer");
        typesArrayList.add("Dress");
        typesArrayList.add("Pants");
        typesArrayList.add("Jeans");
        typesArrayList.add("Swimwear");
        typesArrayList.add("Tights");
        typesArrayList.add("Shirts");
        typesArrayList.add("Towels");
        typesArrayList.add("Coat");
        typesArrayList.add("Jacket");
        typesArrayList.add("Tie");
        typesArrayList.add("Braces");
        typesArrayList.add("Belt");
        typesArrayList.add("Vest");

        Collections.sort(typesArrayList);

        return typesArrayList;
    }

    private static ArrayList<String> getSizeArrayList()
    {
        ArrayList<String> typesArrayList = new ArrayList<>();
        typesArrayList.add("XXS");
        typesArrayList.add("XS");
        typesArrayList.add("S");
        typesArrayList.add("M");
        typesArrayList.add("M");
        typesArrayList.add("L");
        typesArrayList.add("XL");
        typesArrayList.add("XXL");
        typesArrayList.add("3XL");
        typesArrayList.add("4XL");
        typesArrayList.add("5XL");
        typesArrayList.add("6XL");
        typesArrayList.add("7XL");

//        Collections.sort(typesArrayList);

        return typesArrayList;
    }

    private static ArrayList<String> getColorArrayList()
    {
        ArrayList<String> colorArrayList = new ArrayList<>();
        colorArrayList.add("Black");
        colorArrayList.add("Blue");
        colorArrayList.add("Brown");
        colorArrayList.add("Gray");
        colorArrayList.add("Green");
        colorArrayList.add("Lemon");
        colorArrayList.add("Maroon");
        colorArrayList.add("Olive");
        colorArrayList.add("Orange");
        colorArrayList.add("Pink");
        colorArrayList.add("Mixed");
        colorArrayList.add("Red");
        colorArrayList.add("Silver");
        colorArrayList.add("Teal");
        colorArrayList.add("Violet");
        colorArrayList.add("White");
        colorArrayList.add("Yellow");

        Collections.sort(colorArrayList);

        return colorArrayList;
    }

    private static ArrayList<String> getBrandArrayList()
    {
        ArrayList<String> brandArrayList = new ArrayList<>();
        brandArrayList.add("Asics");
        brandArrayList.add("Aldo");
        brandArrayList.add("Adidas");
        brandArrayList.add("Alfred Dunhill");
        brandArrayList.add("Antonio Banderas");
        brandArrayList.add("Alpina Watches");
        brandArrayList.add("Arnette");
        brandArrayList.add("Aab");
        brandArrayList.add("Boss");
        brandArrayList.add("Bluezoo");
        brandArrayList.add("Bourjois");
        brandArrayList.add("Bluekids");
        brandArrayList.add("BeYu");
        brandArrayList.add("Bata");
        brandArrayList.add("Bella Comoda");
        brandArrayList.add("Bvlgari");
        brandArrayList.add("Bentley");
        brandArrayList.add("Baldi");
        brandArrayList.add("Ben Sherman");
        brandArrayList.add("Brax");
        brandArrayList.add("Brosway");
        brandArrayList.add("Boss Orange");
        brandArrayList.add("Birkenstock");
        brandArrayList.add("Burberry");

        Collections.sort(brandArrayList);

        return brandArrayList;
    }



    public static void main(String[] args)
    {
        System.out.println(getTypeArrayList().toString());
        System.out.println(getSizeArrayList().toString());
        System.out.println(getColorArrayList().toString());
        System.out.println(getBrandArrayList().toString());

    }
}
