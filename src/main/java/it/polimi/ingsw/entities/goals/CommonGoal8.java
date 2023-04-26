package it.polimi.ingsw.entities.goals;

import it.polimi.ingsw.entities.Bookshelf;
import it.polimi.ingsw.entities.Card;
import it.polimi.ingsw.exceptions.CellGetCardException;

/*
 * Four tiles of the same type in the four corners of the bookshelf.
 */

public class CommonGoal8 extends CommonGoal implements Goal {

    @Override
    public int checkGoal(Bookshelf bookshelf) {
        Card c1, c2, c3, c4;

        try {
            c1 = bookshelf.getCell(0,0).getCard();
            c2 = bookshelf.getCell(0,4).getCard();
            c3 = bookshelf.getCell(5,0).getCard();
            c4 = bookshelf.getCell(5,4).getCard();
        } catch (CellGetCardException e) {
            throw new RuntimeException(e);
        }

        if(c1.sameType(c2) && c3.sameType(c4) && c2.sameType(c4)) return getScore();
        else return 0;
    }
}