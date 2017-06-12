package application;
	


import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventDispatcher;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;


public class Main extends Application {
	
	private final TabPane tabPane2 = new TabPane();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("GM Swiss Knife");
			
			final ObservableList<String> listItems = FXCollections.observableArrayList("Campaign 1", "Campaign 2", "Campaign 3");

			
			TreeItem<String> dummyRoot1 = new TreeItem<>();
			TreeItem<String> dummyRoot2 = new TreeItem<>();
			
	        for (int i = 0; i < listItems.size(); i++) {
	        	
	        	TreeItem<String> rootItem = new TreeItem<String> (listItems.get(i));
	        	rootItem.setExpanded(false);
	        	
	            TreeItem<String> pcs = new TreeItem<String> ("PCs");
	            pcs.setExpanded(false);
	            TreeItem<String> pc1 = new TreeItem<String> ("PC1");
	            TreeItem<String> pc2 = new TreeItem<String> ("PC2");
	            
	            pcs.getChildren().addAll(pc1, pc2);
	            
	            TreeItem<String> npcs = new TreeItem<String> ("NPCs");
	            TreeItem<String> npc1 = new TreeItem<String> ("NPC1");
	            TreeItem<String> npc2 = new TreeItem<String> ("NPC2");
	            
	            npcs.getChildren().addAll(npc1, npc2);
	       
	            TreeItem<String> locations = new TreeItem<String> ("Locations");
	            rootItem.getChildren().addAll(pcs, npcs, locations);
	            dummyRoot1.getChildren().add(rootItem);
	            
	            if (dummyRoot2.getChildren().isEmpty()) {
	            	dummyRoot2.getChildren().addAll(pcs, npcs, locations);
	            }
	        }        
	        TreeView<String> tree1 = new TreeView<String>(dummyRoot1);
	        //tree1.setEditable(true);
	        tree1.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	            	
	            	TextFieldTreeCellImpl cell = new TextFieldTreeCellImpl((p.getChildrenUnmodifiable().isEmpty()) ? true : false);
	                
	                return cell;
	            }
	        });
	        tree1.setShowRoot(false);
			
			TreeView<String> tree2 = new TreeView<String>(dummyRoot2);
			tree2.setEditable(true);
	        tree2.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	                return new TextFieldTreeCellImpl(true);
	            }
	        });
	        tree2.setShowRoot(false);
			
			TabPane tabPane1 = new TabPane();
			Tab campaigns = new Tab("Campaigns");
			Tab resources = new Tab("Resources");
			campaigns.setContent(tree1);
			resources.setContent(tree2);
			tabPane1.getTabs().addAll(campaigns, resources);
			tabPane1.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			
			final TextArea textArea = new TextArea("");
			textArea.setWrapText(true);
			
			Tab tab = new Tab();
			tab.setText("new tab");
			tab.setContent(textArea);
			tabPane2.getTabs().add(tab);
			
			SplitPane root = new SplitPane();
			root.getItems().addAll(tabPane1, tabPane2);
			root.setDividerPosition(0, 0.2f);
			//BorderPane.setAlignment(tree, Pos.TOP_LEFT);
			//BorderPane.setAlignment(tabPane, Pos.CENTER);
			
			//BorderPane root = new BorderPane(tabPane, null, null, null, tree);
			
			Scene scene = new Scene(root, 1024, 768);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class TextFieldTreeCellImpl extends TreeCell<String> {
		 
        private TextField textField;
        private boolean isFolder;
 
        public TextFieldTreeCellImpl(boolean isFolder) {
        	this.isFolder = isFolder;
        	addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
                @Override public void handle(MouseEvent t) {
                    if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2) {
                    	if(treeItemProperty().getValue().getChildren().isEmpty()) {
                    		final TextArea textArea = new TextArea("");
                    		textArea.setWrapText(true);
                    		textArea.setText("This is a new file for "+getItem().toString());
                    		Tab newTab = new Tab(getItem().toString());
                    		newTab.setContent(textArea);
                    		tabPane2.getTabs().add(newTab);
        				}
                    	System.out.println("double click");
                    	t.consume();
                    }
                }
            });
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
            System.out.println(getTreeItem());
            
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
        
        public boolean getIsFolder(){
        	return this.isFolder;
        }
 
        private void createTextField() {
            textField = new TextField(getString());
            textField.setOnKeyPressed(new EventHandler<KeyEvent>() {
 
                @Override
                public void handle(KeyEvent t) {
                    if (t.getCode() == KeyCode.ENTER) {
                        commitEdit(textField.getText());
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

class TreeMouseEventDispatcher implements EventDispatcher {
    private final EventDispatcher originalDispatcher;

    public TreeMouseEventDispatcher(EventDispatcher originalDispatcher) {
      this.originalDispatcher = originalDispatcher;
    }

    public Event dispatchEvent(Event event, EventDispatchChain tail) {
        if (event instanceof MouseEvent) {
           if (((MouseEvent) event).getButton() == MouseButton.PRIMARY
               && ((MouseEvent) event).getClickCount() >= 2) {

             if (!event.isConsumed()) {
               // Implement your double-click behavior here, even your
               // MouseEvent handlers will be ignored, i.e., the event consumed!
             }

             event.consume();
           }
        }
        return originalDispatcher.dispatchEvent(event, tail);
    }
}