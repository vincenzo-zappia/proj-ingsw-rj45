/* Author/s: Tirabassi M., Vianello G., Zappia V.
 * Date: 26/03/2023
 * IDE: IntelliJ IDEA
 * Version 0.2
 * Comments: none
 */

package it.polimi.ingsw.server.model.entities;

import it.polimi.ingsw.exceptions.CellGetCardException;

public class Cell {

    //REGION ATTRIBUTES
    private Card card;
    private boolean empty;
    //END REGION

    //REGION CONSTRUCT
    public Cell(){
        empty=true;
    }
    //END REGION

    //REGION METHODS
    public Card getCard() throws CellGetCardException {
        if(!isCellEmpty()) return card;
        else return null;
    }
    public void setCard(Card card) {
        this.card = card;
        empty = false;
    }
    public void setCellEmpty(){
        empty=true;
    }
    public boolean isCellEmpty(){
        return empty;
    }
    //END REGION

}
