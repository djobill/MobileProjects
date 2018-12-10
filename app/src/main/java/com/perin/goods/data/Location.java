package com.perin.goods.data;

import android.graphics.Bitmap;

import java.io.Serializable;
import java.util.ArrayList;

public class Location extends Object implements Serializable, Cloneable {

    public String postalCode = new String();
    public String address = new String();
    public String city = new String();
    public String residenceType = new String();
    public boolean isChecked = false;
    public Bitmap imageLocation = null;
    private static String adrTAG = "<Location xml:address=";
    private static String cityTAG = "<xml:city=";
    private static String cpTAG = "<xml:postalcode=";
    private static String resTAG = "<xml:residencetype=";
    private static String imlTAG = "<xml:imagelocation=";

    private static String endTAG = "/>";

    public Location() {
        new Location( new String( "-" ), new String( "-" ), new String( "-" ) );
    }

    public Location(String address, String city, String postalCode) {
        new Location( address, city, postalCode, "P", false );
    }

    public Location(String address, String city, String postalCode, boolean isChecked) {
        new Location( address, city, postalCode, "P", isChecked );
    }

    public Location(String address, String city, String postalCode, String residenceType, boolean isChecked) {
        super();
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.residenceType = residenceType;
        this.isChecked = isChecked;
        this.imageLocation = null;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getResidenceType() {
        return residenceType;
    }

    public void setResidenceType(String residenceType) {
        this.residenceType = residenceType;
    }

    public boolean isChecked() {
        return isChecked;
    }


    public Bitmap getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(Bitmap imageLocation) {
        this.imageLocation = imageLocation;
    }

    public StringBuilder writeLocationToXML(Location location) {
        StringBuilder xmlLocation = new StringBuilder();
        xmlLocation.append( adrTAG );
        xmlLocation.append( location.getAddress() );
        xmlLocation.append( endTAG );
        xmlLocation.append( cityTAG );
        xmlLocation.append( location.getCity() );
        xmlLocation.append( endTAG );
        xmlLocation.append( cpTAG );
        xmlLocation.append( location.getPostalCode() );
        xmlLocation.append( endTAG );
        xmlLocation.append( resTAG );
        xmlLocation.append( location.getResidenceType() );
        xmlLocation.append( endTAG );
        xmlLocation.append( endTAG );

        return xmlLocation;
    }

    public static ArrayList<String> parseLocationLine(String line) {
        String workLine = new StringBuilder( line ).toString();
        ArrayList<String> values = new ArrayList<String>();
        String address = workLine.substring( workLine.indexOf( adrTAG ) + adrTAG.length(), workLine.indexOf( endTAG ) );
        values.add( address );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );
//        workLine = workLine.substring( 0, workLine.indexOf( endTAG ) + endTAG.length() );
        String city = workLine.substring( workLine.indexOf( cityTAG ) + cityTAG.length(), workLine.indexOf( endTAG ) );
        values.add( city );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );
//        workLine = workLine.substring( 0, workLine.indexOf( endTAG ) + endTAG.length() );
        String postalCode = workLine.substring( workLine.indexOf( cpTAG ) + cpTAG.length(), workLine.indexOf( endTAG ) );
        values.add( postalCode );
        workLine = workLine.substring( workLine.indexOf( endTAG ) + endTAG.length() );
//        workLine = workLine.substring( 0, workLine.indexOf( endTAG ) + endTAG.length() );
        String resType = workLine.substring( workLine.indexOf( resTAG ) + resTAG.length(), workLine.indexOf( endTAG ) );
        values.add( resType );
        return values;
    }

    public Object clone() {
        Location location = null;
        try {
            // On récupère l'instance à renvoyer par l'appel de la
            // méthode super.clone()
            location = (Location) super.clone();
        } catch (CloneNotSupportedException cnse) {
            // Ne devrait jamais arriver car nous implémentons
            // l'interface Cloneable
            cnse.printStackTrace( System.err );
        }

        // on renvoie le clone
        return location;
    }
}
