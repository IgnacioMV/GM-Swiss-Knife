package application;
	
import java.awt.event.MouseEvent;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("GM Swiss Knife");
			
			
			final ObservableList<String> listItems = FXCollections.observableArrayList("Campaign 1", "Campaign 2", "Campaign 3");

			
			TreeItem<String> dummyRoot = new TreeItem<>();
			
	        for (int i = 0; i < listItems.size(); i++) {
	        	
	        	TreeItem<String> rootItem = new TreeItem<String> (listItems.get(i));
	        	rootItem.setExpanded(false);
	        	
	            TreeItem<String> pcs = new TreeItem<String> ("PCs");
	            TreeItem<String> pc1 = new TreeItem<String> ("PC1");
	            TreeItem<String> pc2 = new TreeItem<String> ("PC2");
	            
	            pcs.getChildren().addAll(pc1, pc2);
	            
	            TreeItem<String> npcs = new TreeItem<String> ("NPCs");
	            
	            
	            TreeItem<String> locations = new TreeItem<String> ("Locations");
	            rootItem.getChildren().add(pcs);
	            rootItem.getChildren().add(npcs);
	            rootItem.getChildren().add(locations);
	            dummyRoot.getChildren().add(rootItem);
	        }        
	        TreeView<String> tree = new TreeView<String>(dummyRoot);
	        tree.setEditable(true);
	        tree.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	                return new TextFieldTreeCellImpl();
	            }
	        });
	        tree.setShowRoot(false);
			tree.setPrefWidth(150);
			
			final TextArea textArea = new TextArea("");
			textArea.setWrapText(true);
			
			TabPane tabPane = new TabPane();
			Tab tab = new Tab();
			tab.setText("new tab");
			tab.setContent(textArea);
			tabPane.getTabs().add(tab);
			
			BorderPane.setAlignment(tree, Pos.TOP_LEFT);
			BorderPane.setAlignment(tabPane, Pos.CENTER);
			
			BorderPane root = new BorderPane(tabPane, null, null, null, tree);
			
			Scene scene = new Scene(root, 640, 480);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class TextFieldTreeCellImpl extends TreeCell<String> {
		 
        private TextField textField;
 
        public TextFieldTreeCellImpl() {
        }
        
        public void onMouseClicked(MouseEvent click) {
        	
        }
 
        @Override
        public void startEdit() {
            super.startEdit();
 
            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }
 
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText((String) getItem());
            setGraphic(getTreeItem().getGraphic());
        }
 
        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);
 
            if (empty) {
                setText(null);
                setGraphic(null);
            } else {
                if (isEditing()) {
                    if (textField != null) {
                        textField.setText(getString());
                    }
                    setText(null);
                    setGraphic(textField);
                } else {
                    setText(getString());
                    setGraphic(getTreeItem().getGraphic());
                }
            }
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.F2) {
                        commitEdit(textField.getText());
                    } else if (t.getCode() == KeyCode.F2) {
                        cancelEdit();
                    }
                }
            });
            
           
        }
 
        private String getString() {
            return getItem() == null ? "" : getItem().toString();
        }
    }
	
	public static void main(String[] args) {
		Application.launch(args);
	}
}
