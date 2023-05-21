package it.polimi.ingsw.view.gui.scenes;

import it.polimi.ingsw.entities.goals.Goal;
import it.polimi.ingsw.entities.goals.PrivateGoal;
import it.polimi.ingsw.entities.util.BoardTile;
import it.polimi.ingsw.entities.util.Tile;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/**
 * Controller of the actual game scene where the player can interact with the game entities
 */
public class GameScene extends GenericScene{

    //region FXML
    @FXML private GridPane board;
    @FXML private GridPane bookshelf;

    @FXML private ImageView cg1;
    @FXML private ImageView cg1Score;
    @FXML private ImageView cg2;
    @FXML private ImageView cg2Score;
    @FXML private ImageView token;
    @FXML private ImageView pg;

    @FXML private Button confirm;
    @FXML private Button col0;
    @FXML private Button col1;
    @FXML private Button col2;
    @FXML private Button col3;
    @FXML private Button col4;
    //endregion
    
    private ArrayList<int[]> currentSelection;

    /**
     * Routines of game scene initialization such as binding even handlers to buttons and making image views not visible
     */
    public void initGame(){
        currentSelection = new ArrayList<>();

        for(Node node : board.getChildren()) {
            //logic
            node.setOnMouseClicked(onBoardCardClick);

            //graphic
            node.setVisible(false);
        }
        for(Node node : bookshelf.getChildren()) node.setVisible(false);

        col0.setOnAction(onInsertColumnClick);
        col1.setOnAction(onInsertColumnClick);
        col2.setOnAction(onInsertColumnClick);
        col3.setOnAction(onInsertColumnClick);
        col4.setOnAction(onInsertColumnClick);

    }

    //region CLICKS
    /**
     * Adds the coordinates of the card placed in the clicked board tile in the selection arraylist and changes its aesthetic
     */
    EventHandler<MouseEvent> onBoardCardClick = event -> {
        Node clikedNode = (Node) event.getSource();
        ImageView selectedCard = (ImageView) clikedNode;

        //Adding the clicked card to the selection
        int[] selection = new int[2];
        selection[0] = GridPane.getRowIndex(clikedNode);
        selection[1] = GridPane.getColumnIndex(clikedNode);
        currentSelection.add(selection);

        //Changing the graphic properties of the card
        selectedCard.setOpacity(0.5);

    };

    /**
     * Creates an array of coordinates out of the selection arraylist and sends it in a selection request to the server
     */
    @FXML public void onConfirmClick(){

        //Creating the array of coordinates out of the selection arraylist
        int[][] sentSelection = new int[currentSelection.size()][2];
        for(int i = 0; i < currentSelection.size(); i++){
            sentSelection[i][0] = currentSelection.get(i)[0];
            sentSelection[i][1] = currentSelection.get(i)[1];
        }

        //Sending the selected coordinates to the server
        controller.sendSelection(sentSelection);

        //End task routines: clearing the selection arraylist and setting the opacity back to normal
        currentSelection.clear();
        for(Node node : board.getChildren()) node.setOpacity(1);
    }

    /**
     * Extracts the number of the clicked column and sends it in an insertion request to the server
     */
    EventHandler<ActionEvent> onInsertColumnClick = event -> {
        Button column = (Button) event.getSource();
        int sentColumn = 5;
        
        //Extracting the column number from its relative button
        switch (column.getId()){
            case "col0" -> sentColumn = 0;
            case "col1" -> sentColumn = 1;
            case "col2" -> sentColumn = 2;
            case "col3" -> sentColumn = 3;
            case "col4" -> sentColumn = 4;
        }
        
        //Sending the insertion request to the server
        controller.sendInsertion(sentColumn);
        
    };
    //endregion

    //Methods called by GUIManager to update the graphical entities of the game scene during the game
    //region DISPLAYS
    /**
     * Updating and displaying the refilled board
     * @param updatedBoard refilled board
     */
    public void displayBoard(BoardTile[][] updatedBoard){
        int numRows = board.getRowCount();
        int numCols = board.getColumnCount();

        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numCols; col++){

                //Checking if a new card was placed in the tile
                if(!updatedBoard[row][col].isTileActive() || updatedBoard[row][col].isTileEmpty()) continue;

                //Updating and displaying the newly placed card
                Node node = getNodeByRowColumnIndex(row, col, board);
                if (node instanceof ImageView) {
                    ImageView card = (ImageView) node;
                    //String imgPath = Main.class.getResource("") + updatedBoard[row][col].getCard().getImgPath();
                    String imgPath = "file:/C:/Users/green/Documents/GitHub/proj-ingsw-rj45/target/classes/assets/Cards/games2.png";
                    card.setImage(new Image(imgPath)); //TODO: Aggiungere base path bellino
                    card.setVisible(true);
                }

            }
        }
    }

    /**
     * Updating and displaying the player's bookshelf with the newly added cards
     * @param updatedBookshelf updated bookshelf
     */
    public void displayBookshelf(Tile[][] updatedBookshelf){
        int numRows = bookshelf.getRowCount();
        int numCols = bookshelf.getColumnCount();

        for(int row = 0; row < numRows; row++){
            for(int col = 0; col < numCols; col++){

                //Checking if a new card was added in the bookshelf tile
                if(updatedBookshelf[row][col].isTileEmpty()) continue;

                //Updating and displaying the newly added card
                Node node = getNodeByRowColumnIndex(row, col, bookshelf);
                if (node instanceof ImageView) {
                    ImageView card = (ImageView) node;
                    //String imgPath = Main.class.getResource("") + updatedBookshelf[row][col].getCard().getImgPath();
                    String imgPath = "file:/C:/Users/green/Documents/GitHub/proj-ingsw-rj45/target/classes/assets/Cards/games2.png";
                    card.setImage(new Image(imgPath)); //TODO: Aggiungere base path bellino
                    card.setVisible(true);
                }

            }
        }
    }

    /**
     * Displaying the randomly chosen common goal cards and updating the score whenever a player achieves one
     * @param commonGoals common goals of the current game
     */
    public void displayCommonGoals(Goal[] commonGoals){

        //TODO: Così a fine turno viene riaggiornata inultimente anche l'immagine del commongoal, inutile ma irrilevante
        //TODO: Risolvere questione image path
        /*
        cg1.setImage(new Image());
        cg1Score.setImage(new Image());
        cg2.setImage(new Image());
        cg2Score.setImage(new Image());

         */
    }

    /**
     * Displays the player-specific private goal card randomly chosen for each player at the start of the game
     * @param privateGoal player-specific private goal
     */
    public void displayPrivateGoal(PrivateGoal privateGoal){
        //pg.setImage(new Image()); //TODO: Implementare metodo in PrivateGoal che restituisce image path
    }

    public void removeCards(int[][] coordinates){
        for (int[] coordinate : coordinates) {
            Node node = getNodeByRowColumnIndex(coordinate[0], coordinate[1], board);
            ImageView card = (ImageView) node;
            assert card != null;
            card.setVisible(false);
        }

    }
    //endregion

    //region UTIL
    /**
     * Returns the instance of the GridPane child ImageView given its coordinates
     * @param row GridPane row coordinate fo the ImageView
     * @param col GridPane column coordinate fo the ImageView
     * @param gridPane GridPane parent of the ImageView
     * @return the instance of the ImageView
     */
    private Node getNodeByRowColumnIndex(final int row, final int col, GridPane gridPane) {
        for (Node node : gridPane.getChildren()) {
            Integer rowIndex = GridPane.getRowIndex(node);
            Integer colIndex = GridPane.getColumnIndex(node);

            if (rowIndex != null && colIndex != null && rowIndex == row && colIndex == col) {
                return node;
            }
        }
        return null;
    }
    //endregion

}
