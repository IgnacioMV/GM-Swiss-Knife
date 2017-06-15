package application;

import java.util.List;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import javax.persistence.EntityManager;

import dao.CampaignDAOImpl;
import dao.CampaignItemDAOImpl;
import dao.EMFService;
import dao.TemplateItemDAOImpl;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
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
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Campaign;
import model.CampaignItem;
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
	private CampaignItemDAOImpl citemdao = CampaignItemDAOImpl.getInstance();
	private TemplateItemDAOImpl titemdao = TemplateItemDAOImpl.getInstance();

	@Override
	public void start(Stage primaryStage) {
		getMetaModels();

		try {
			primaryStage.setTitle("GM Swiss Knife");

			campaignsTab.setContent(campaignsTree);
			templatesTab.setContent(templatesTree);
			tabPane1.getTabs().addAll(campaignsTab, templatesTab);
			tabPane1.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);

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
	}

	private void populateCampaigns() {

		System.out.println("populating campaigns");

		List<Campaign> campaigns = campaigndao.readAll(em);

		if (campaigns.isEmpty()) {
			campaigndao.createCampaign(em, new Campaign("First Campaign"));
			campaigndao.createCampaign(em, new Campaign("Second Campaign"));
			campaigndao.createCampaign(em, new Campaign("Fourth Campaign"));
		}

		TreeItem<GenericItem> dummyRoot = new TreeItem<GenericItem>();

		for (Campaign campaign : campaigns) {
			TreeItem<GenericItem> campaignNode = new TreeItem<GenericItem>(campaign);
			if (campaignNode.valueProperty().get() instanceof Campaign) {
				System.out.println("is campaign and Nacho YOU FUCKING ROCK");
			}

			TreeItem<GenericItem> pcsNode = new TreeItem<GenericItem>(new DummyItem("PCs", ItemType.PC));
			TreeItem<GenericItem> npcsNode = new TreeItem<GenericItem>(new DummyItem("NPCs", ItemType.NPC));
			TreeItem<GenericItem> locationsNode = new TreeItem<GenericItem>(
					new DummyItem("Locations", ItemType.LOCATION));
			TreeItem<GenericItem> equipmentsNode = new TreeItem<GenericItem>(
					new DummyItem("Equipment", ItemType.EQUIPMENT));

			List<CampaignItem> pcs = citemdao.readByItemTypeAndCampaign(em, ItemType.PC, campaign);
			List<CampaignItem> npcs = citemdao.readByItemTypeAndCampaign(em, ItemType.NPC, campaign);
			List<CampaignItem> locations = citemdao.readByItemTypeAndCampaign(em, ItemType.LOCATION, campaign);
			List<CampaignItem> equipments = citemdao.readByItemTypeAndCampaign(em, ItemType.EQUIPMENT, campaign);

			for (CampaignItem pc : pcs) {
				pcsNode.getChildren().add(new TreeItem<GenericItem>(pc));
			}
			for (CampaignItem npc : npcs) {
				npcsNode.getChildren().add(new TreeItem<GenericItem>(npc));
			}
			for (CampaignItem location : locations) {
				locationsNode.getChildren().add(new TreeItem<GenericItem>(location));
			}
			for (CampaignItem equipment : equipments) {
				equipmentsNode.getChildren().add(new TreeItem<GenericItem>(equipment));
			}

			campaignNode.getChildren().addAll(pcsNode, npcsNode, locationsNode, equipmentsNode);

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

	private ContextMenu getCampaignContextMenu() {
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
					System.out.println("campaign");

					Campaign newItem = new Campaign(filename);
					campaigndao.createCampaign(em, newItem);
					newNode = new TreeItem<GenericItem>(newItem);
					TreeItem<GenericItem> newPCs = new TreeItem<GenericItem>(new DummyItem("PCs", ItemType.PC));
					TreeItem<GenericItem> newNPCs = new TreeItem<GenericItem>(new DummyItem("NPCs", ItemType.NPC));
					TreeItem<GenericItem> newLocations = new TreeItem<GenericItem>(
							new DummyItem("Locations", ItemType.LOCATION));
					TreeItem<GenericItem> newEequipments = new TreeItem<GenericItem>(
							new DummyItem("Equipments", ItemType.EQUIPMENT));
					newNode.getChildren().addAll(newPCs, newNPCs, newLocations);
					campaignsTree.getRoot().getChildren().add(newNode);
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
						newItem.setCampaignId(parent.getId());
						newItem.setItemType(dm.getItemType());
						citemdao.createCampaignItem(em, newItem);
						newNode = new TreeItem<GenericItem>(newItem);
					} else {
						TemplateItem newItem = new TemplateItem();
						newItem.setName(filename);
						newItem.setItemType(dm.getItemType());
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
					newItem.setCampaignId(parentCampaign.getId());
					newItem.setItemType(selectedItem.getItemType());
					citemdao.createCampaignItem(em, newItem);
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
					TreeItem<GenericItem> selectedNode = campaignsTree.getSelectionModel().getSelectedItem();
					System.out.println(selectedNode.getValue().toString());
					CampaignItem campItem = (CampaignItem) selectedNode.getValue();
					campItem.setName(filename);
					citemdao.updateCampaignItem(em, campItem);
					selectedNode.setValue(null);
					selectedNode.setValue(campItem);
				});
			}
		});

		contextMenu.getItems().addAll(item1, item2);

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
					newItem.setItemType(selectedItem.getItemType());
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
						if (treeItemProperty().getValue().getChildren().isEmpty()) {
							final TextArea textArea = new TextArea("");
							textArea.setWrapText(true);
							textArea.setText(getItem().getText());
							textArea.textProperty().addListener((observable, oldValue, newValue) -> {
								int autosaveDelay = 1000; //miliseconds
								
								System.out.println(getItem() instanceof CampaignItem);
								System.out.println(getItem().getText());
								
								autosaveTimer.cancel();
								autosaveTimer = new Timer();
								autosaveTimer.schedule(new TimerTask() {
									@Override
									public void run() {
										autosaveTimer.cancel();
										System.out.println("User stopped typing after "+String.valueOf(autosaveDelay)+" ms");
										getItem().setText(textArea.getText());
										boolean updated = (getItem() instanceof CampaignItem) ? citemdao.updateCampaignItem(em, (CampaignItem) getItem()) : 
											titemdao.updateTemplateItem(em, (TemplateItem) getItem());
									}
								}, autosaveDelay);
								
								//System.out.println("textfield changed from " + oldValue + " to " + newValue);
							});
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
