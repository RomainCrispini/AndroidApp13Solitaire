package com.romain.app13solitaire;

import java.io.Serializable;
import java.util.Vector;

public class Game implements Serializable {

    // Définition de deux classes dérivent de collections (qui sont des classes génériques)
    // Définition des types Stack et Deck
    public static class Stack extends java.util.Stack<Card>{}
    public static class Deck extends java.util.Stack<Card>{}

    public static final int STACK_COUNT = 4;
    public static final int DECK_COUNT = 7;

    // Définition des attributs du jeu
    public Stack [] stacks = new Stack[STACK_COUNT];
    public Deck [] decks = new Deck[DECK_COUNT];
    public Vector<Card> pioche = new Vector<>();
    public Vector<Card> returnedPioche = new Vector<>();


    // Constructeur qui permet d'initialiser l'état d'une nouvelle partie
    // Création de l'état initial du jeu
    public Game() {

        // Step 1 - Toutes les cartes sont instanciées
        for( int i = 1; i <= 13; i++ ) {
            pioche.add( new Card( Card.CardType.CARREAU, i ) );
            pioche.add( new Card( Card.CardType.COEUR, i ) );
            pioche.add( new Card( Card.CardType.PIQUE, i ) );
            pioche.add( new Card( Card.CardType.TREFLE, i ) );
        }

        // Step 2 - On mélange les cartes
        for( int round = 0; round < 200; round++ ) {
            // A chaque tour on récupère une position aléatoire au niveau de la pioche
            int position = (int) ( Math.random() * pioche.size() );
            // On supprime la carte de la pioche
            Card removedCard = pioche.elementAt( position );
            pioche.removeElementAt( position );
            // Et on réinsère la carte au dessus du paquet
            pioche.add( removedCard );
        }

        // Step 3 - On crée les sept decks avec des cartes tirées aléatoirement dans la pioche
        // Les 7 decks ont des tailles différentes
        for( int deckIndex = 0; deckIndex < DECK_COUNT; deckIndex++ ) {
            // On instancie les 7 decks
            decks[deckIndex] = new Deck();
            // Boucle qui permet de faire colonne1 : 1 carte, colonne2 : 2 cartes...
            for( int cardIndex = 0; cardIndex < deckIndex + 1; cardIndex++ ) {
                int position = (int) ( Math.random() * pioche.size() );
                Card removedCard = pioche.elementAt( position );
                pioche.removeElementAt( position );
                decks[deckIndex].push( removedCard );
                // La dernière carte est visible
                if ( cardIndex == deckIndex ) removedCard.setReturned( true );
            }
        }

        // Step 4 - On initialise les quatre stacks.
        // On instancie les 4 stacks
        for( int stackIndex = 0; stackIndex < STACK_COUNT; stackIndex++ ) {
            stacks[stackIndex] = new Stack();
        }

    }

    /**
     * Vérifie que la carte puisse être déposée sur l'un des quatre stacks.
     * @param card La carte à déposer.
     * @return L'indice du stack sur lequelle la carte peut être déposée,
     *         -1 si ce n'est pas possible.
     */
    public int canMoveCardToStack( Card card ) {
        // Si un stack est vide et que la carte est un as
        if ( card.getValue() == 1 ) {
            int stackIndex = 0;
            while( ! this.stacks[stackIndex].isEmpty() ) {
                stackIndex++;
            }
            return stackIndex;
        }

        // Si ce n'est pas un as, peut-on empiler la carte sur une carte de
        // valeur inférieure dans l'une des piles.
        for( int stackIndex = 0; stackIndex < STACK_COUNT; stackIndex++ ) {
            Stack stack = this.stacks[stackIndex];
            if ( ! stack.isEmpty() ) {
                if ( stack.lastElement().getType() != card.getType() ) continue;
                if ( stack.lastElement().getValue() == card.getValue() - 1 ) return stackIndex;
            }
        }

        return -1;
    }

    /**
     * Vérifie si une carte peut être déposée sur un des sept decks.
     * @param card La carte à déposer.
     * @return L'indice du deck sur lequel la carte peut être déposée,
     *         -1 si ce n'est pas possible.
     */
    public int canMoveCardToDeck( Card card ) {
        // Si la carte est un roi (13) et qu'un deck est vide, alors OK
        if ( card.getValue() == 13 ) {
            for( int deckIndex = 0; deckIndex < DECK_COUNT; deckIndex++ ) {
                if ( this.decks[deckIndex].isEmpty() ) return deckIndex;
            }
        }

        // Est-ce que la carte peut être placée sur un deck ?
        for( int deckIndex = 0; deckIndex < DECK_COUNT; deckIndex++ ) {
            Deck deck = this.decks[deckIndex];
            if ( deck.size() > 0 ) {
                if ( deck.lastElement().getColor() == card.getColor() ) continue;
                if ( deck.lastElement().getValue() == card.getValue()+1 ) return deckIndex;
            }
        }

        // Au final, si on ne peut déposer la carte sur l'un des decks
        return -1;
    }

    /**
     * Détermine si le jeu est terminé, c'est à dire que chaque stack possède bien 13 cartes.
     * @return true si le jeu est terminé, false dans le cas contraire.
     */
    public boolean isFinish() {
        return stacks[0].isEmpty() == false && stacks[0].lastElement().getValue() == 13 &&
                stacks[1].isEmpty() == false && stacks[1].lastElement().getValue() == 13 &&
                stacks[2].isEmpty() == false && stacks[2].lastElement().getValue() == 13 &&
                stacks[3].isEmpty() == false && stacks[3].lastElement().getValue() == 13;
    }

    /**
     * Pour savoir si toutes les cartes du jeu sont retournées et
     * qu'on peut lancer une terminaison automatique du jeu.
     * @return true si toutes les cartes sont retournées, false dans le cas contraire.
     */
    public boolean allIsReturned() {
        for( int i = 0; i < DECK_COUNT; i++ ) {
            Deck deck = decks[i];
            if ( deck.size() > 0 && deck.firstElement().isReturned() == false ) return false;
        }
        return true;
    }


}
