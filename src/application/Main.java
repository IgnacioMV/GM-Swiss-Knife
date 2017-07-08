package application;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;

import dao.CampaignDAOImpl;
import dao.CampaignItemDAOImpl;
import dao.CategoryDAOImpl;
import dao.EMFService;
import dao.TemplateItemDAOImpl;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SingleSelectionModel;
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
import javafx.scene.input.InputEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.TextFlow;
import javafx.scene.web.HTMLEditor;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;
import model.Campaign;
import model.CampaignItem;
import model.CampaignCategory;
import model.DummyItem;
import model.GenericItem;
import model.ItemType;
import model.TemplateItem;

public class Main extends Application {

	private Timer autosaveTimer = new Timer();
	private TabPane tabPane1 = new TabPane();
	private TabPane tabPane2 = new TabPane();
	private Tab campaignsTab = new Tab("Campaigns");
	private Tab templatesTab = new Tab("Templates");
	private TreeView<GenericItem> campaignsTree = new TreeView<GenericItem>();
	private TreeView<GenericItem> templatesTree = new TreeView<GenericItem>();
	private EntityManager em = EMFService.get().createEntityManager();

	private CampaignDAOImpl campaigndao = CampaignDAOImpl.getInstance();
	private CategoryDAOImpl categorydao = CategoryDAOImpl.getInstance();
	private CampaignItemDAOImpl citemdao = CampaignItemDAOImpl.getInstance();
	private TemplateItemDAOImpl titemdao = TemplateItemDAOImpl.getInstance();

	@Override
	public void start(Stage primaryStage) {
		getMetaModels();

		try {
			primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent t) {
	                Platform.exit();
	                System.exit(0);
	            }
	        });
			
			primaryStage.setTitle("GM Swiss Knife");

			campaignsTab.setContent(campaignsTree);
			templatesTab.setContent(templatesTree);
			tabPane1.getTabs().addAll(campaignsTab, templatesTab);
			tabPane1.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
			
	        tabPane1.setContextMenu(getCampaignTabPaneContextMenu());
	        
			populateCampaigns();
			populateTemplates();

			campaignsTab.setOnSelectionChanged(new EventHandler<Event>() {
				@Override
				public void handle(Event t) {
					if (campaignsTab.isSelected()) {
						System.out.println("campaignsTab selected");
					}
				}
			});

			SplitPane root = new SplitPane();
			root.getItems().addAll(tabPane1, tabPane2);
			root.setDividerPosition(0, 0.2f);

			Scene scene = new Scene(root, 1024, 768);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void getMetaModels() {
		em.getMetamodel().entity(Campaign.class);
		em.getMetamodel().entity(CampaignItem.class);
		em.getMetamodel().entity(TemplateItem.class);
		em.getMetamodel().entity(CampaignCategory.class);
	}

	private void populateCampaigns() {

		System.out.println("populating campaigns");

		List<Campaign> campaigns = campaigndao.readAll(em);

		if (campaigns.isEmpty()) {
			System.out.println("creating 3 sample campaigns");
			campaigndao.createCampaign(em, new Campaign("First Campaign"));
			campaigndao.createCampaign(em, new Campaign("Second Campaign"));
			campaigndao.createCampaign(em, new Campaign("Third Campaign"));
			campaigns = campaigndao.readAll(em);
		}

		TreeItem<GenericItem> dummyRoot = new TreeItem<GenericItem>();

		for (Campaign campaign : campaigns) {
			TreeItem<GenericItem> campaignNode = new TreeItem<GenericItem>(campaign);
			if (campaignNode.valueProperty().get() instanceof Campaign) {
				System.out.println("is campaign and Nacho YOU FUCKING ROCK");
			}
			
			List<CampaignCategory> categories = categorydao.readByCampaign(em, campaign.getId());

			System.out.println(categories);
			
			if (categories != null) {
			for (CampaignCategory category : categories) {
				System.out.println(category.getText());
				
				TreeItem<GenericItem> categoryNode = new TreeItem<GenericItem>(category);
				
				List<CampaignItem> categoryItems = citemdao.readAllByCategory(em, category);
				
				for (CampaignItem categoryItem : categoryItems) {
					categoryNode.getChildren().add(new TreeItem<GenericItem>(categoryItem));
				}
				
				campaignNode.getChildren().add(categoryNode);
			}
			}

			dummyRoot.getChildren().add(campaignNode);
		}

		campaignsTree.setShowRoot(false);

		campaignsTree.setCellFactory(new Callback<TreeView<GenericItem>, TreeCell<GenericItem>>() {
			@Override
			public TreeCell<GenericItem> call(TreeView<GenericItem> p) {

				TextFieldTreeCellImpl cell = new TextFieldTreeCellImpl();

				return cell;
			}
		});

		campaignsTree.setRoot(dummyRoot);
	}

	private void populateTemplates() {

		System.out.println("populating templates");

		TreeItem<GenericItem> dummyRoot = new TreeItem<GenericItem>();

		TreeItem<GenericItem> pcsNode = new TreeItem<>(new DummyItem("PCs", ItemType.PC));
		TreeItem<GenericItem> npcsNode = new TreeItem<>(new DummyItem("NPCs", ItemType.NPC));
		TreeItem<GenericItem> locationsNode = new TreeItem<>(new DummyItem("Locations", ItemType.LOCATION));
		TreeItem<GenericItem> equipmentsNode = new TreeItem<>(new DummyItem("Equipment", ItemType.EQUIPMENT));

		List<TemplateItem> pcs = titemdao.readByItemType(em, ItemType.PC);
		List<TemplateItem> npcs = titemdao.readByItemType(em, ItemType.NPC);
		List<TemplateItem> locations = titemdao.readByItemType(em, ItemType.LOCATION);
		List<TemplateItem> equipments = titemdao.readByItemType(em, ItemType.EQUIPMENT);

		for (TemplateItem tpc : pcs) {
			pcsNode.getChildren().add(new TreeItem<GenericItem>(tpc));
		}
		for (TemplateItem tnpc : npcs) {
			npcsNode.getChildren().add(new TreeItem<GenericItem>(tnpc));
		}
		for (TemplateItem tloc : locations) {
			locationsNode.getChildren().add(new TreeItem<GenericItem>(tloc));
		}
		for (TemplateItem teq : equipments) {
			equipmentsNode.getChildren().add(new TreeItem<GenericItem>(teq));
		}

		dummyRoot.getChildren().addAll(pcsNode, npcsNode, locationsNode, equipmentsNode);

		templatesTree.setShowRoot(false);

		templatesTree.setCellFactory(new Callback<TreeView<GenericItem>, TreeCell<GenericItem>>() {
			@Override
			public TreeCell<GenericItem> call(TreeView<GenericItem> p) {

				TextFieldTreeCellImpl cell = new TextFieldTreeCellImpl();

				return cell;
			}
		});

		templatesTree.setRoot(dummyRoot);
	}

	private ContextMenu getAppropriateContextMenu(TreeItem<GenericItem> treeItem) {

		if (treeItem.getValue() instanceof Campaign) {
			return getCampaignContextMenu();
		} else if (treeItem.getValue() instanceof CampaignCategory) {
			return getCategoryContextMenu(treeItem.getValue().getText());
		} else if (treeItem.getValue() instanceof DummyItem) {
			boolean isCampaignsTree = (treeItem.getParent().getValue() instanceof Campaign) ? true : false;
			return getDummyItemContextMenu(isCampaignsTree);
		} else if (treeItem.getValue() instanceof CampaignItem) {
			return getCampaignItemContextMenu();
		} else if (treeItem.getValue() instanceof TemplateItem) {
			return getTemplateItemContextMenu();
		} else {
			return null;
		}
	}
	
	private ContextMenu getCampaignTabPaneContextMenu() {
		final ContextMenu contextMenu = new ContextMenu();
		
		MenuItem item1 = new MenuItem("New Campaign");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("New Campaign");

				TextInputDialog dialog = new TextInputDialog("New Campaign");
				dialog.setTitle("Create new Campaign");
				dialog.setHeaderText(null);
				dialog.setContentText("New Campaign name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					System.out.println("New Campaign: " + filename);
					TreeItem<GenericItem> newNode = null;
					System.out.println("campaign");

					Campaign newItem = new Campaign(filename);
					campaigndao.createCampaign(em, newItem);
					newNode = new TreeItem<GenericItem>(newItem);
					
					CampaignCategory newPCs = new CampaignCategory();
					CampaignCategory newNPCs = new CampaignCategory();
					CampaignCategory newLocations = new CampaignCategory();
					CampaignCategory newEquipments = new CampaignCategory();
					
					newPCs.setName("PC");
					newPCs.setCampaignId(newItem.getId());
					newNPCs.setName("NPC");
					newNPCs.setCampaignId(newItem.getId());
					newLocations.setName("Location");
					newLocations.setCampaignId(newItem.getId());
					newEquipments.setName("Equipment");
					newEquipments.setCampaignId(newItem.getId());
					categorydao.createCategory(em, newPCs);
					categorydao.createCategory(em, newNPCs);
					categorydao.createCategory(em, newLocations);
					categorydao.createCategory(em, newEquipments);
					
					TreeItem<GenericItem> newPCsNode = new TreeItem<GenericItem>(newPCs);
					TreeItem<GenericItem> newNPCsNode = new TreeItem<GenericItem>(newNPCs);
					TreeItem<GenericItem> newLocationsNode = new TreeItem<GenericItem>(newLocations);
					TreeItem<GenericItem> newEquipmentsNode = new TreeItem<GenericItem>(newEquipments);
					newNode.getChildren().addAll(newPCsNode, newNPCsNode, newLocationsNode, newEquipmentsNode);
					campaignsTree.getRoot().getChildren().add(newNode);
				});
			}
		});
		
		contextMenu.getItems().add(item1);
		return contextMenu;
	}

	private ContextMenu getCampaignContextMenu() {
		final ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("New category");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("New category");

				TextInputDialog dialog = new TextInputDialog("New Category");
				dialog.setTitle("Create new Category");
				dialog.setHeaderText(null);
				dialog.setContentText("New Category name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					System.out.println("New Category: " + filename);
					TreeItem<GenericItem> newNode = null;
					System.out.println("category");

					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					CampaignCategory newCategory = new CampaignCategory();
					newCategory.setName(filename);
					newCategory.setCampaignId(selectedNode.getValue().getId());
					categorydao.createCategory(em, newCategory);
					
					newNode = new TreeItem<GenericItem>(newCategory);
					selectedNode.getChildren().add(newNode);
				});
			}
		});
		MenuItem item2 = new MenuItem("Rename");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Rename");
				TextInputDialog dialog = new TextInputDialog("New name");
				dialog.setTitle("Rename file");
				dialog.setHeaderText(null);
				dialog.setContentText("New name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					System.out.println(selectedNode.getValue().toString());
					Campaign campaign = (Campaign) selectedNode.getValue();
					campaign.setName(filename);
					campaigndao.updateCampaign(em, campaign);
					selectedNode.setValue(null);
					selectedNode.setValue(campaign);
				});
			}
		});

		contextMenu.getItems().addAll(item1, item2);
		return contextMenu;
	}
	
	private ContextMenu getCategoryContextMenu(String categoryName) {
		final ContextMenu contextMenu = new ContextMenu();
		
		MenuItem item1 = new MenuItem("New "+categoryName);
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				//String categoryName = campaignsTree.getSelectionModel().getSelectedItem().getValue().getText();
				System.out.println("New "+categoryName);

				TextInputDialog dialog = new TextInputDialog("New "+categoryName);
				dialog.setTitle("Create new "+categoryName);
				dialog.setHeaderText(null);
				dialog.setContentText("New "+categoryName+" name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					System.out.println("New "+categoryName+" name: " + filename);
					TreeItem<GenericItem> newNode = null;
					System.out.println(categoryName);

					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					CampaignItem newCampaignItem = new CampaignItem();
					newCampaignItem.setName(filename);
					newCampaignItem.setCategoryId(selectedNode.getValue().getId());
					citemdao.createCampaignItem(em, newCampaignItem);
					
					newNode = new TreeItem<GenericItem>(newCampaignItem);
					selectedNode.getChildren().add(newNode);
				});
			}
		});
		MenuItem item2 = new MenuItem("Rename");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Rename");
				TextInputDialog dialog = new TextInputDialog("New name");
				dialog.setTitle("Rename category");
				dialog.setHeaderText(null);
				dialog.setContentText("New name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					System.out.println(selectedNode.getValue().toString());
					CampaignCategory category= (CampaignCategory) selectedNode.getValue();
					category.setName(filename);
					categorydao.updateCategory(em, category);
					selectedNode.setValue(null);
					selectedNode.setValue(category);
				});
			}
		});

		contextMenu.getItems().addAll(item1, item2);
		return contextMenu;
	}

	private ContextMenu getDummyItemContextMenu(boolean isCampaignsTree) {
		final ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("New...");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("New...");

				TextInputDialog dialog = new TextInputDialog("New Campaign");
				dialog.setTitle("Create new Campaign");
				dialog.setHeaderText(null);
				dialog.setContentText("New Campaign name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					System.out.println("New Campaign: " + filename);
					TreeItem<GenericItem> newNode = null;
					TreeItem<GenericItem> selectedNode = (isCampaignsTree)
							? campaignsTree.getSelectionModel().getSelectedItem()
							: templatesTree.getSelectionModel().getSelectedItem();

					System.out.println("dummy item");
					DummyItem dm = (DummyItem) selectedNode.getValue();

					if (isCampaignsTree) {
						Campaign parent = (Campaign) selectedNode.getParent().getValue();
						CampaignItem newItem = new CampaignItem();
						newItem.setName(filename);
						newItem.setCategoryId(parent.getId());
						citemdao.createCampaignItem(em, newItem);
						newNode = new TreeItem<GenericItem>(newItem);
					} else {
						TemplateItem newItem = new TemplateItem();
						newItem.setName(filename);
						titemdao.createTemplateItem(em, newItem);
						newNode = new TreeItem<GenericItem>(newItem);
					}

					selectedNode.getChildren().add(newNode);
				});
			}
		});

		contextMenu.getItems().addAll(item1);

		return contextMenu;
	}

	private ContextMenu getCampaignItemContextMenu() {
		final ContextMenu contextMenu = new ContextMenu();
/**
		MenuItem item1 = new MenuItem("New...");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("New...");

				TextInputDialog dialog = new TextInputDialog("New Campaign");
				dialog.setTitle("Create new item");
				dialog.setHeaderText(null);
				dialog.setContentText("New item name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					System.out.println("New item: " + filename);
					TreeItem<GenericItem> newNode = null;
					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					CampaignItem selectedItem = (CampaignItem) selectedNode.getValue();
					Campaign parentCampaign = (Campaign) selectedNode.getParent().getParent().getValue();
					CampaignItem newItem = new CampaignItem();
					newItem.setName(filename);
					newItem.setCategoryId(parentCampaign.getId());
					citemdao.createCampaignItem(em, newItem);
					newNode = new TreeItem<GenericItem>(newItem);
					selectedNode.getParent().getChildren().add(newNode);
				});
			}
		});**/
		MenuItem item2 = new MenuItem("Rename");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Rename");
				TextInputDialog dialog = new TextInputDialog("New name");
				dialog.setTitle("Rename file");
				dialog.setHeaderText(null);
				dialog.setContentText("New name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					System.out.println(selectedNode.getValue().toString());
					CampaignItem campItem = (CampaignItem) selectedNode.getValue();
					campItem.setName(filename);
					citemdao.updateCampaignItem(em, campItem);
					selectedNode.setValue(null);
					selectedNode.setValue(campItem);
					
					for (Tab tab : tabPane2.getTabs()) {
						if (tab.getProperties().get("cid") != null)
							if (tab.getProperties().get("cid").equals(campItem.getId())) {
								tab.setText(campItem.toString());
								break;
							}
					}
				});
			}
		});

		contextMenu.getItems().addAll(item2);

		return contextMenu;
	}

	private ContextMenu getTemplateItemContextMenu() {
		final ContextMenu contextMenu = new ContextMenu();

		MenuItem item1 = new MenuItem("New...");
		item1.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("New...");

				TextInputDialog dialog = new TextInputDialog("New Template");
				dialog.setTitle("Create new Template");
				dialog.setHeaderText(null);
				dialog.setContentText("New Template name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					System.out.println("New Template: " + filename);
					TreeItem<GenericItem> newNode = null;
					TreeItem<GenericItem> selectedNode = templatesTree.getSelectionModel().getSelectedItem();
					TemplateItem selectedItem = (TemplateItem) selectedNode.getValue();
					TemplateItem newItem = new TemplateItem();
					newItem.setName(filename);
					titemdao.createTemplateItem(em, newItem);
					newNode = new TreeItem<GenericItem>(newItem);
					selectedNode.getParent().getChildren().add(newNode);
				});
			}
		});
		MenuItem item2 = new MenuItem("Rename");
		item2.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent e) {
				System.out.println("Rename");
				TextInputDialog dialog = new TextInputDialog("New name");
				dialog.setTitle("Rename file");
				dialog.setHeaderText(null);
				dialog.setContentText("New name:");
				dialog.setGraphic(null);
				Optional<String> result = dialog.showAndWait();
				result.ifPresent(filename -> {
					TreeItem<GenericItem> selectedNode = templatesTree.getSelectionModel().getSelectedItem();
					System.out.println(selectedNode.getValue().toString());
					TemplateItem templItem = (TemplateItem) selectedNode.getValue();
					templItem.setName(filename);
					titemdao.updateTemplateItem(em, templItem);
					selectedNode.setValue(null);
					selectedNode.setValue(templItem);
					
					for (Tab tab : tabPane2.getTabs()) {
						if (tab.getProperties().get("tid") != null)
							if (tab.getProperties().get("tid").equals(templItem.getId())) {
								tab.setText(templItem.toString());
								break;
							}
					}
				});
			}
		});

		contextMenu.getItems().addAll(item1, item2);

		return contextMenu;
	}

	private final class TextFieldTreeCellImpl extends TreeCell<GenericItem> {

		private TextField textField;

		public TextFieldTreeCellImpl() {

			addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent t) {
					if (t.getButton() == MouseButton.PRIMARY && t.getClickCount() == 2) {
						if (treeItemProperty().getValue().getChildren().isEmpty() && !(treeItemProperty().getValue().getValue() instanceof CampaignCategory)) {

							final HTMLEditor htmlEditor = new HTMLEditor();
							htmlEditor.setHtmlText(getItem().getText());
							
							htmlEditor.addEventHandler(InputEvent.ANY, new EventHandler<InputEvent>() {
																
						        @Override
						        public void handle(InputEvent event) {
						        	
						        	if (event instanceof KeyEvent) {
						        		KeyEvent kEvent = (KeyEvent) event;
						        		if (kEvent.getCode() == KeyCode.W && kEvent.isControlDown()) { 
						        			System.out.println("ctrl + w");
						        			tabPane2.getTabs().remove(tabPane2.getSelectionModel().getSelectedIndex());
						        	    }
						        	}
						        	
						        	int autosaveDelay = 500; //Milliseconds
						        	
									autosaveTimer.cancel();
									autosaveTimer = new Timer();
									autosaveTimer.schedule(new TimerTask() {
										@Override
										public void run() {
											autosaveTimer.cancel();
											System.out.println("User stopped typing after "+String.valueOf(autosaveDelay)+" ms");
											
											Tab selectedTab = tabPane2.getSelectionModel().getSelectedItem();
											GenericItem item = null;
											
											if (selectedTab.getProperties().get("cid") != null) {
												long cid = (long) selectedTab.getProperties().get("cid");
												item = citemdao.readById(em, cid);
											}
											else {
												long tid = (long) selectedTab.getProperties().get("tid");
												item = titemdao.readById(em, tid);
											}
											
											getItem().setText(htmlEditor.getHtmlText());
											
											
											
											boolean updated = (item instanceof CampaignItem) ? citemdao.updateCampaignItem(em, (CampaignItem) item) : 
												titemdao.updateTemplateItem(em, (TemplateItem) getItem());
										}
									}, autosaveDelay);
						        	
						        }
						    });
							
							GenericItem item = getItem();
							SingleSelectionModel<Tab> selectionModel = tabPane2.getSelectionModel();
							
							for (Tab tab : tabPane2.getTabs()) {
								if (item instanceof CampaignItem)
								if (tab.getProperties().get("cid") != null && tab.getProperties().get("cid").equals(item.getId())) {
									System.out.println("Tab exists!!!");
									selectionModel.select(tab);
									t.consume();
									return;
								}
								if (item instanceof TemplateItem)
								if (tab.getProperties().get("tid") != null && tab.getProperties().get("tid").equals(item.getId())) {
									System.out.println("Tab!!!");
									selectionModel.select(tab);
									t.consume();
									return;
								}
							}
							
							Tab newTab = new Tab(item.toString());
							if(item instanceof CampaignItem) {
								newTab.getProperties().put("cid", item.getId());
								newTab.setStyle("-fx-border-color:red; -fx-background-color: white;");
							}
							if (item instanceof TemplateItem) {
								newTab.getProperties().put("tid", item.getId());
								newTab.setStyle("-fx-border-color:blue; -fx-background-color: white;");
							}
							newTab.setContent(htmlEditor);
							tabPane2.getTabs().add(newTab);
							
							selectionModel.select(newTab);
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
			setText((String) getItem().toString());
			setGraphic(getTreeItem().getGraphic());
		}

		@Override
		public void updateItem(GenericItem item, boolean empty) {
			super.updateItem(item, empty);

			if (empty) {
				setText(null);
				setGraphic(null);

			} else {
				setText(getString());
				setGraphic(getTreeItem().getGraphic());
				setContextMenu(getAppropriateContextMenu(getTreeItem()));
			}
		}

		private void createTextField() {
			textField = new TextField(getString());
			textField.setOnKeyPressed(new EventHandler<KeyEvent>() {

				@Override
				public void handle(KeyEvent t) {
					if (t.getCode() == KeyCode.ENTER) {
						// commitEdit(textField.getText());
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
