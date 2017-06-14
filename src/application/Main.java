package application;
	


import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import dao.CampaignDAOImpl;
import dao.EMFService;
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
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.Campaign;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ContextMenuBuilder;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.MenuItemBuilder;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
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
		
		/**Campaign campaign = new Campaign();
		campaign.setName("First campaign");
		
		JDBCCampaignDAO jbdcCampaignDAO = new JDBCCampaignDAO();
		jbdcCampaignDAO.getConnection();
		jbdcCampaignDAO.insert(campaign);
		
		List<Campaign> camps = jbdcCampaignDAO.selectAll();
		for (Campaign camp : camps) {
			System.out.println(camp.getName());
		}**/
		
		/**
		EntityManager em = EMFService.get().createEntityManager();
		CampaignDAOImpl campaigndao = CampaignDAOImpl.getInstance();
		Campaign camp = new Campaign("First campaign");
		campaigndao.createCampaign(em, camp);
		em.close();
	    **/
	     		
		try {
			primaryStage.setTitle("GM Swiss Knife");
			
			final ObservableList<String> listItems = FXCollections.observableArrayList("Campaign 1", "Campaign 2", "Campaign 3");
			
			TreeItem<String> dummyRoot1 = new TreeItem<>();
			TreeItem<String> dummyRoot2 = new TreeItem<>();
						
	        for (int i = 0; i < listItems.size(); i++) {
	        	
	        	TreeItem<String> rootItem1 = new TreeItem<String> (listItems.get(i));
	        	rootItem1.setExpanded(false);
	        	
	        	String pc1name = "PC"+String.valueOf(i)+"1";
	        	String pc2name = "PC"+String.valueOf(i)+"2";
	        	String npc1name = "NPC"+String.valueOf(i)+"1";
	        	String npc2name = "NPC"+String.valueOf(i)+"2";
	        	
	            TreeItem<String> pcs = new TreeItem<String> ("PCs");
	            pcs.setExpanded(false);
	            TreeItem<String> pc1 = new TreeItem<String> (pc1name);
	            TreeItem<String> pc2 = new TreeItem<String> (pc2name);
	            
	            TreeItem<String> tiname1 = new TreeItem<String> ("player1");
	            
	            TreeItem<String> pcs2 = new TreeItem<String> ("PCs");
	            pcs.setExpanded(false);
	            TreeItem<String> pc12 = new TreeItem<String> (pc1name);
	            TreeItem<String> pc22 = new TreeItem<String> (pc2name);
	            
	            pcs.getChildren().addAll(pc1, pc2, tiname1);
	            pcs2.getChildren().addAll(pc12, pc22, tiname1);
	            
	            TreeItem<String> npcs = new TreeItem<String> ("NPCs");
	            npcs.setExpanded(false);
	            TreeItem<String> npc1 = new TreeItem<String> (npc1name);
	            TreeItem<String> npc2 = new TreeItem<String> (npc2name);
	            
	            npcs.getChildren().addAll(npc1, npc2);
	       
	            TreeItem<String> locations = new TreeItem<String> ("Locations");
	            locations.setExpanded(false);
	            dummyRoot2.getChildren().addAll(pcs2);
	            rootItem1.getChildren().addAll(pcs, npcs, locations);
	            dummyRoot1.getChildren().add(rootItem1);
	            
	            
	        }   
	        TreeView<String> tree1 = new TreeView<String>();
	        tree1.setRoot(dummyRoot1);
	        
	        //tree1.setEditable(true);
	        tree1.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	            	
	            	TextFieldTreeCellImpl cell = new TextFieldTreeCellImpl();
	                
	                return cell;
	            }
	        });
	        tree1.setShowRoot(false);
	        
	        final ContextMenu tree1ContextMenu = new ContextMenu();
	        tree1ContextMenu.setOnShowing(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent e) {
	                System.out.println("showing");
	            }
	        });
	        tree1ContextMenu.setOnShown(new EventHandler<WindowEvent>() {
	            public void handle(WindowEvent e) {
	                System.out.println("shown");
	            }
	        });

	        MenuItem item1 = new MenuItem("New...");
	        item1.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent e) {
	                System.out.println("New...");
	                TextInputDialog dialog = new TextInputDialog("New file");
	                dialog.setTitle("Create new file");
	                dialog.setHeaderText(null);
	                dialog.setContentText("New filename:");
	                dialog.setGraphic(null);
	                Optional<String> result = dialog.showAndWait();
	                result.ifPresent(filename -> {
	                	System.out.println("New file: " + filename); 
	                	TreeItem<String> selectedItem = tree1.getSelectionModel().getSelectedItem();
	                	TreeItem<String> newItem = new TreeItem<String>(filename);
	                	if (selectedItem == null) {
	                		TreeItem<String> newPCs = new TreeItem<String>("PCs");
	                		TreeItem<String> newNPCs = new TreeItem<String>("NPCs");
	                		TreeItem<String> newLocations = new TreeItem<String>("Locations");
	                		newItem.getChildren().addAll(newPCs, newNPCs, newLocations);
	                		dummyRoot1.getChildren().add(newItem);
	                	}
	                	else {
	                		selectedItem.getChildren().add(newItem);
	                	}
	                });
	            }
	        });
	        MenuItem item2 = new MenuItem("Rename");
	        item2.setOnAction(new EventHandler<ActionEvent>() {
	            public void handle(ActionEvent e) {
	                System.out.println("Rename");
	                
	            }
	        });
	        
	        tree1ContextMenu.getItems().addAll(item1, item2);

	        tree1.setContextMenu(tree1ContextMenu);
			
			TreeView<String> tree2 = new TreeView<String>(dummyRoot2);
			//tree2.setEditable(true);
	        tree2.setCellFactory(new Callback<TreeView<String>,TreeCell<String>>(){
	            @Override
	            public TreeCell<String> call(TreeView<String> p) {
	                return new TextFieldTreeCellImpl();
	            }
	        });
	        tree2.setShowRoot(false);
			
			TabPane tabPane1 = new TabPane();
			Tab campaignsTab = new Tab("Campaigns");
			Tab resourcesTab = new Tab("Resources");
			campaignsTab.setContent(tree1);
			resourcesTab.setContent(tree2);
			tabPane1.getTabs().addAll(campaignsTab, resourcesTab);
			tabPane1.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			campaignsTab.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
	            public void handle(Event t) {
	                if (campaignsTab.isSelected()) {
	                    System.out.println("campaignsTab selected");
	                }
	            }
			});
			
			final TextArea textArea = new TextArea("");
			textArea.setWrapText(true);
			
			Tab tab = new Tab();
			tab.setText("new tab");
			tab.setContent(textArea);
			tabPane2.getTabs().add(tab);
			
			SplitPane root = new SplitPane();
			root.getItems().addAll(tabPane1, tabPane2);
			root.setDividerPosition(0, 0.2f);
			
			Scene scene = new Scene(root, 1024, 768);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private final class TextFieldTreeCellImpl extends TreeCell<String> {
		 
        private TextField textField;
        
        public TextFieldTreeCellImpl() {
        	
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

