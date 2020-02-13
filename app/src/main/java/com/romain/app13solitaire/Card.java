package com.romain.app13solitaire;

import android.graphics.Color;

import java.io.Serializable;

class Card implements Serializable {

    private CardType type;
    private int value;
    private boolean returned;

    public Card( CardType type, int value ) {
        this( type, value, false );
    }

    public Card( CardType type, int value, boolean returned ) {
        this.setType( type );
        this.setValue( value );
        this.setReturned( returned );
    }

    public CardType getType() {
        return type;
    }

    public void setType(CardType type) {
        this.type = type;
    }

    public int getColor() {
        switch( this.type ) {
            case COEUR:
            case CARREAU:
                return Color.RED;
            default:
                return Color.BLACK;
        }
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        if ( value < 1 || value > 13 ) throw new IllegalArgumentException( "Value not supported" );
        this.value = value;
    }

    public String getName() {
        switch( this.value ) {
            case 1: return "A";
            case 2: return "2";
            case 3: return "3";
            case 4: return "4";
            case 5: return "5";
            case 6: return "6";
            case 7: return "7";
            case 8: return "8";
            case 9: return "9";
            case 10: return "10";
            case 11: return "J";
            case 12: return "Q";
            case 13: return "K";
            default: throw new RuntimeException( "Bad card value" );
        }
    }

    public boolean isReturned() {
        return returned;
    }

    public void setReturned(boolean returned) {
        this.returned = returned;
    }



    @Override
    public String toString() {
        return type.toString() + " " + value;
    }

    public static enum CardType {
        COEUR, CARREAU, PIQUE, TREFLE;
    }
}