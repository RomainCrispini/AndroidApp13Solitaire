package com.romain.app13solitaire;

import java.io.Serializable;
import java.util.Vector;

public class Game implements Serializable {

    public static class Stack extends java.util.Stack<Card>{}
    public static class Deck extends java.util.Stack<Card>{}

    public static final int STACK_COUNT = 4;
    public static final int DECK_COUNT = 7;

    public Stack [] stacks = new Stack[STACK_COUNT];
    public Deck [] decks = new Deck[DECK_COUNT];
    public Vector<Card> pioche = new Vector<>();
    public Vector<Card> returnedPioche = new Vector<>();


    // Constructeur qui permet d'initialiser l'état d'une nouvelle partie
    public Game() {

        // Step 1 - Toutes les cartes sont instanciées
        for( int i=1; i<=13; i++ ) {
            pioche.add( new Card( Card.CardType.CARREAU, i ) );
            pioche.add( new Card( Card.CardType.COEUR, i ) );
            pioche.add( new Card( Card.CardType.PIQUE, i ) );
            pioche.add( new Card( Card.CardType.TREFLE, i ) );
        }

        // Step 2 - On mélange les cartes
        for( int round=0; round<200; round++ ) {
            int position = (int) ( Math.random() * pioche.size() );
            Card removedCard = pioche.elementAt( position );
            pioche.removeElementAt( position );
            pioche.add( removedCard );
        }

        // Step 3 - On crée les sept decks avec des cartes tirées aléatoirement dans la pioche
        for( int deckIndex=0; deckIndex<DECK_COUNT; deckIndex++ ) {
            decks[deckIndex] = new Deck();
            for( int cardIndex = 0; cardIndex < deckIndex+1; cardIndex++ ) {
                int position = (int) ( Math.random() * pioche.size() );
                Card removedCard = pioche.elementAt( position );
                pioche.removeElementAt( position );
                decks[deckIndex].push( removedCard );
                if ( cardIndex == deckIndex ) removedCard.setReturned( true );
            }
        }

        // Step 4 - On initialise les quatre stacks.
        for( int stackIndex=0; stackIndex<STACK_COUNT; stackIndex++ ) {
            stacks[stackIndex] = new Stack();
        }

    }

}
