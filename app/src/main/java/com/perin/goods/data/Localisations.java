package com.perin.goods.data;

import java.util.ArrayList;

public class Localisations {

    public static String BED_ROOM = "Chambre";
    public static String DINING_ROOM = "Salle à manger";
    public static String KITCHEN = "Cuisine";
    public static String REST_ROOM = "WC";
    public static String BATH_ROOM = "Salle de Bain";
    public static String LIVING_ROOM = "Salon";
    public static String VERANDA = "Veranda";
    public static String ENTRANCE = "Entrée";
    public static String HALL = "Hall";
    public static String LANDING = "Palier";
    public static String BACKYARD = "Palier";
    public static String POOLROOM = "Local Technique Piscine";
    public static String GARAGE = "Garage";
    public static String ATELIER = "Atelier";
    public static String WINE_CELLAR = "Cave";

    private static String[] rooms = {
            BED_ROOM,
            DINING_ROOM,
            KITCHEN,
            REST_ROOM,
            BATH_ROOM,
            LIVING_ROOM,
            VERANDA,
            ENTRANCE,
            HALL,
            LANDING,
            BACKYARD,
            POOLROOM,
            GARAGE,
            ATELIER,
            WINE_CELLAR
    };
    public static ArrayList<String> LOCALISATIONS;

    public static ArrayList<String> getLocalisations() {
        if (rooms.length == 0) {
            for (int i = 0; i < rooms.length; i++) {
                LOCALISATIONS.add( rooms[i] );
            }
        }
        return LOCALISATIONS;
    }

}
