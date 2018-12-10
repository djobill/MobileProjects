package com.perin.goods.data;


import android.media.Image;

import java.io.Serializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class AssetType implements Serializable {
    private static String locationTAG = "<Asset xml:location=";
    private static String nameTAG = "<xml:name=";
    private static String categTAG = "<xml:category=";
    private static String localisationTAG = "<xml:localisation=";
    private static String pidTAG = "<xml:pid=";
    private static String dateTAG = "<xml:purchasedate=";
    private static String priceTAG = "<xml:price=";
    private static String guarTAG = "<xml:guaranty=";
    private static String endTAG = "/>";

    private Location location = null;
    private String slocation = new String();
    private String assetName = new String();
    private String assetCategory = new String();
    private String assetLocalisation = new String();
    private String assetPID = new String();
    private Calendar assetPurchaseDate = Calendar.getInstance();
    private float assetPrice = 0;
    private int assetGuaranty = 5;
    private ArrayList<Image> assetPictures = new ArrayList<Image>();
    private ArrayList<URL> assetLinks = new ArrayList<URL>();

    public AssetType(String slocation,
                     String sName,
                     String sCategory,
                     String sPID,
                     String sPurchaseDate,
                     String sPrice,
                     String sGuaranty) {
        this.location = LocationContent.findLocation( slocation );
        this.assetName = sName;
        this.assetCategory = sCategory;
        this.assetPID = sPID;
        this.assetPurchaseDate = new GregorianCalendar();
        Integer year = Integer.getInteger( sPurchaseDate.substring( 0, 1 ) );
        Integer month = Integer.getInteger( sPurchaseDate.substring( 2, 3 ) );
        Integer day = Integer.getInteger( sPurchaseDate.substring( 4, 5 ) );
        assetPurchaseDate.set( Calendar.YEAR, year.intValue() );
        assetPurchaseDate.set( Calendar.MONTH, month.intValue() );
        assetPurchaseDate.set( Calendar.DAY_OF_MONTH, day.intValue() );
        Float fPrice = Float.valueOf( sPrice );
        this.assetPrice = fPrice.floatValue();
        this.assetGuaranty = Integer.getInteger( sGuaranty );
        this.assetPictures = new ArrayList<Image>();
        this.assetLinks = new ArrayList<URL>();
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getAssetName() {
        return assetName;
    }

    public void setAssetName(String assetName) {
        this.assetName = assetName;
    }

    public String getAssetCategory() {
        return assetCategory;
    }

    public void setAssetCategory(String assetCategory) {
        this.assetCategory = assetCategory;
    }

    public String getAssetLocalisation() {
        return assetLocalisation;
    }

    public void setAssetLocalisation(String assetLocalisation) {
        this.assetLocalisation = assetLocalisation;
    }

    public String getAssetPID() {
        return assetPID;
    }

    public void setAssetPID(String assetPID) {
        this.assetPID = assetPID;
    }

    public Calendar getAssetPurchaseDate() {
        return assetPurchaseDate;
    }

    public void setAssetPurchaseDate(Calendar assetPurchaseDate) {
        this.assetPurchaseDate = assetPurchaseDate;
    }

    public float getAssetPrice() {
        return assetPrice;
    }

    public void setAssetPrice(float assetPrice) {
        this.assetPrice = assetPrice;
    }

    public int getAssetGuaranty() {
        return assetGuaranty;
    }

    public void setAssetGuaranty(int assetGuaranty) {
        this.assetGuaranty = assetGuaranty;
    }

    public ArrayList<Image> getAssetPictures() {
        return assetPictures;
    }

    public void setAssetPictures(ArrayList<Image> assetPictures) {
        this.assetPictures = assetPictures;
    }

    public ArrayList<URL> getAssetLinks() {
        return assetLinks;
    }

    public void setAssetLinks(ArrayList<URL> assetLinks) {
        this.assetLinks = assetLinks;
    }

    public static ArrayList<String> parseAssetLine(String line) {
        String workLine = new StringBuilder( line ).toString();
        ArrayList<String> values = new ArrayList<String>();

        String sLocation = workLine.substring( workLine.indexOf( locationTAG ) + locationTAG.length(), workLine.indexOf( endTAG ) );
        values.add( sLocation );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );
//        workLine = workLine.substring( 0, workLine.indexOf( endTAG ) + endTAG.length() );

        String name = workLine.substring( workLine.indexOf( nameTAG ) + nameTAG.length(), workLine.indexOf( endTAG ) );
        values.add( name );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );
//        workLine = workLine.substring( 0, workLine.indexOf( endTAG ) + endTAG.length() );

        String category = workLine.substring( workLine.indexOf( categTAG ) + categTAG.length(), workLine.indexOf( endTAG ) );
        values.add( category );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );
//        workLine = workLine.substring( 0, workLine.indexOf( endTAG ) + endTAG.length() );

        String pid = workLine.substring( workLine.indexOf( pidTAG ) + pidTAG.length(), workLine.indexOf( endTAG ) );
        values.add( pid );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );

        String purchaseDate = workLine.substring( workLine.indexOf( dateTAG ) + dateTAG.length(), workLine.indexOf( endTAG ) );
        values.add( purchaseDate );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );

        String price = workLine.substring( workLine.indexOf( priceTAG ) + priceTAG.length(), workLine.indexOf( endTAG ) );
        values.add( price );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );

        String guaranty = workLine.substring( workLine.indexOf( guarTAG ) + guarTAG.length(), workLine.indexOf( endTAG ) );
        values.add( guaranty );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );

        return values;
    }

    public StringBuilder writeAssetToXML(AssetType assetType) {
        StringBuilder xmlAsset = new StringBuilder();
        xmlAsset.append( locationTAG );
        // add in this tag all the Location with a semi-colon as a field separator
        xmlAsset.append( location.getAddress() );
        xmlAsset.append( ";" );
        xmlAsset.append( location.getCity() );
        xmlAsset.append( ";" );
        xmlAsset.append( location.getPostalCode() );
        xmlAsset.append( ";" );
        xmlAsset.append( location.getResidenceType() );
        xmlAsset.append( ";" );
        xmlAsset.append( endTAG );

        xmlAsset.append( nameTAG );
        xmlAsset.append( getAssetName() );
        xmlAsset.append( endTAG );

        xmlAsset.append( categTAG );
        xmlAsset.append( getAssetCategory() );
        xmlAsset.append( endTAG );

        xmlAsset.append( localisationTAG );
        xmlAsset.append( getAssetLocalisation() );
        xmlAsset.append( endTAG );

        xmlAsset.append( pidTAG );
        xmlAsset.append( getAssetPID() );
        xmlAsset.append( endTAG );

        xmlAsset.append( dateTAG );
        xmlAsset.append( getAssetPurchaseDate() );
        xmlAsset.append( endTAG );

        xmlAsset.append( priceTAG );
        xmlAsset.append( getAssetPrice() );
        xmlAsset.append( endTAG );

        xmlAsset.append( guarTAG );
        xmlAsset.append( getAssetGuaranty() );
        xmlAsset.append( endTAG );
        return xmlAsset;
    }
}
