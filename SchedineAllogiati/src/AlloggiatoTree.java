import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.swt.widgets.TreeItem;

/**
 * 
 */

/**
 * @author Alberto Bonizzi
 * 
 */
public class AlloggiatoTree extends Tree {
	
	protected TreeColumn type, arrival, stay, surname, name, sex, birthday,
			birthState, birthDistrict, citizienship, docType, docNumber, documentReleasePlace;
	
	protected static final String menuItems[] = {
	
		"Modifica" 
	
	};
	
	protected static final String singleIconFile = "res/files/single.png";
	protected static final String groupIconFile = "res/files/group.png";
	protected static final String familyIconFile = "";
	
	public AlloggiatoTree (Composite parent, int params) {
		super (parent, params);
		
		Menu treeMenu;
		MenuItem temp;
		
		// Lets make visible the column heathers with the column names
		setHeaderVisible (true);
		setLinesVisible (true);
		
		type = new TreeColumn (this, SWT.CENTER);
		type.setText ("Tipo Alloggiato");
		type.setWidth (100);
		
		arrival = new TreeColumn (this, SWT.CENTER);
		arrival.setText ("Arrivo");
		arrival.setWidth (100);
		
		stay = new TreeColumn (this, SWT.CENTER);
		stay.setText ("Permanenza");
		stay.setWidth (100);
		
		surname = new TreeColumn (this, SWT.CENTER);
		surname.setText ("Cognome");
		surname.setWidth (100);
		
		name = new TreeColumn (this, SWT.CENTER);
		name.setText ("Nome");
		name.setWidth (100);
		
		sex = new TreeColumn (this, SWT.CENTER);
		sex.setText ("Sesso");
		sex.setWidth (100);
		
		birthday = new TreeColumn (this, SWT.CENTER);
		birthday.setText ("Data d nascita");
		birthday.setWidth (100);
		
		birthDistrict = new TreeColumn (this, SWT.CENTER);
		birthDistrict.setText ("Comune di nascita");
		birthDistrict.setWidth (100);
		
		birthState = new TreeColumn (this, SWT.CENTER);
		birthState.setText ("Stato di nascita");
		birthState.setWidth (100);
		
		citizienship = new TreeColumn (this, SWT.CENTER);
		citizienship.setText ("Cittadinanza");
		citizienship.setWidth (100);
		
		docType = new TreeColumn (this, SWT.CENTER);
		docType.setText ("Tipo Documento");
		docType.setWidth (100);
		
		docNumber = new TreeColumn (this, SWT.CENTER);
		docNumber.setText ("Numero documento");
		docNumber.setWidth (100);
		
		documentReleasePlace = new TreeColumn (this, SWT.CENTER);
		documentReleasePlace.setText ("Luogo rilascio documento");
		documentReleasePlace.setWidth (100);
		
		treeMenu = new Menu (parent.getShell (), SWT.POP_UP);
		
		for (String item : menuItems) {
			
			temp = new MenuItem (treeMenu, SWT.NONE);
			temp.setText (item);
			
			temp.addSelectionListener (new MenuListener (this));
		}
		
		setMenu (treeMenu);
		
		addListener (SWT.Selection, new Listener () {
			
			@Override
			public void handleEvent (Event event) {
				
				if (event.detail == SWT.CHECK) {
					
					TreeItem item;
					boolean checked;
					
					item = (TreeItem) event.item;
					checked = item.getChecked ();
					
					checkItems (item, checked);
					checkPath (item.getParentItem (), checked, false);
				}
			}
		
		});
	}
	
	public void insertRecords (List<Record> people) {
		
		TreeItem item, subItem;
		Image singleImage, groupImage, familyImage, tempImage;
		Color errorColor;
		
		tempImage = new Image (Display.getDefault (),
				ResourceLoader.loader (singleIconFile));
		singleImage = new Image (Display.getDefault (), tempImage
				.getImageData ().scaledTo (16, 16));
		tempImage.dispose ();
		
		tempImage = new Image (Display.getDefault (),
				ResourceLoader.loader (groupIconFile));
		groupImage = new Image (Display.getDefault (), tempImage
				.getImageData ().scaledTo (16, 16));
		tempImage.dispose ();
		
		item = null;
		errorColor = new Color (Display.getDefault (), 255, 163, 102);
		
		for (Record record : people) {
			
			String[] fields;
			
			fields = record.toStringArray ();
			
			if (record.getTipoAlloggiato ().equals (Alloggiato.CAPO_GRUPPO) ||
					record.getTipoAlloggiato ().equals (Alloggiato.CAPO_FAMIGLIA)) {
				
				item = new TreeItem (this, SWT.NONE);
				item.setText (fields);
				
				if (record.getTipoAlloggiato ().equals (Alloggiato.CAPO_GRUPPO)) {
					
					item.setImage (groupImage);
				} else {
					//TODO: aggiungere immagine famiglia
					
				}
				
				if (checkNull (item, fields))
					item.setBackground (errorColor);
				
				item.setChecked (true);
			
			} else if (record.getTipoAlloggiato ().equals (Alloggiato.MEMBRO_GRUPPO) ||
					record.getTipoAlloggiato ().equals (Alloggiato.MEMBRO_FAMIGLIA)){
				
				subItem = new TreeItem (item, SWT.NONE);
				subItem.setText (record.toStringArray ());
				
				subItem.setImage (singleImage);
				
				if (checkNull (item, fields))
					subItem.setBackground (errorColor);
				
				subItem.setChecked (true);
				
			} else { //OSPITE SINGOLO
				
				item = new TreeItem (this, SWT.NONE);
				item.setText (record.toStringArray ());
				
				item.setImage (singleImage);
				
				if (checkNull (item, fields))
					item.setBackground (errorColor);
				
				item.setChecked (true);
			}
		}
	}
	
	private boolean checkNull (TreeItem item, String[] fields) {
		
		for (String temp : fields)
			if (temp == null) return true;
		
		return false;
	}
	
	public List<Record> getSelectedRecords () {
		
		List<Record> res;
		
		res = new ArrayList<Record> ();
		
		for (TreeItem temp : getItems ()) {
			
			if (temp.getChecked ())
				res.add (treeItemToRecord (temp));
			
			getSelectedRecordsRecursive (temp, res);
		}
		
		return res;
	}
	
	private void getSelectedRecordsRecursive (TreeItem item, List<Record> res) {
		
		for (TreeItem temp : item.getItems ()) {
			
			if (temp.getChecked ())
				res.add (treeItemToRecord (temp));
			
			getSelectedRecordsRecursive (temp, res);
		}
	};
	
	private void checkPath (TreeItem item, boolean checked, boolean grayed) {
		
		if (item == null)
			return;
		
		if (grayed) {
			
			checked = true;
		
		} else {
			
			int index;
			TreeItem[] items;
			
			index = 0;
			items = item.getItems ();
			
			while (index < items.length) {
				
				TreeItem child;
				
				child = items[index];
				
				if (child.getGrayed () || checked != child.getChecked ()) {
					
					checked = grayed = true;
					break;
				}
				
				index++;
			}
		}
		
		item.setChecked (checked);
		item.setGrayed (grayed);
		
		checkPath (item.getParentItem (), checked, grayed);
	}

	private void checkItems (TreeItem item, boolean checked) {
		
		TreeItem[] items;
		
		item.setGrayed (false);
		item.setChecked (checked);
		
		items = item.getItems ();
		
		for (int i = 0; i < items.length; i++) {
			
			checkItems (items[i], checked);
		}
	}
	
	@Override
	protected void checkSubclass () {
		
	}
	
	private static Record treeItemToRecord (TreeItem item) {
		
		String type, arrival, surname, name, sex, birthday,
			birthState, birthDistrict, citizienship, docType, docNumber, documentReleasePlace;
		int stay;
		Record res;
		
		type = item.getText (0);
		arrival = item.getText (1);
		stay = Integer.parseInt (item.getText (2));
		surname = item.getText (3);
		name = item.getText (4);
		sex = item.getText (5);
		birthday = item.getText (6);
		birthDistrict = item.getText (7);
		birthState = item.getText (8);
		citizienship = item.getText (9);
		docType = item.getText (10);
		docNumber = item.getText (11);
		documentReleasePlace = item.getText (12);

		res = new Record (type, arrival, stay, surname, name, sex, birthday,
				birthDistrict, birthState, citizienship, docType, docNumber,
				documentReleasePlace);
		
		return res;
	}
	
	private class MenuListener implements SelectionListener {
		
		private Tree tree;
		
		public MenuListener (Tree tree) {
			
			this.tree = tree;
		}
		
		@Override
		public void widgetDefaultSelected (SelectionEvent arg0) {
			
		}
		
		@Override
		public void widgetSelected (SelectionEvent arg0) {
			
			System.out.print ("[menu] pressed \""
					+ ((MenuItem) arg0.widget).getText () + "\", ");
			System.out.println ("selected element \""
					+ treeItemToRecord (tree.getSelection ()[0]) + "\"");
		}
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main (String[] args) throws IOException {
		
		Display display;
		Shell shell;
		final AlloggiatoTree tree;
		final FileManager fManager;
		
		display = new Display ();
		shell = new Shell (display);
		fManager = new Csv ();
		
		shell.setText ("AllogiatoTree");
		shell.setSize (1300, 200);
		
		GridLayout gl_shell = new GridLayout (1, false);
		gl_shell.marginWidth = 0;
		gl_shell.horizontalSpacing = 0;
		gl_shell.marginHeight = 0;
		gl_shell.verticalSpacing = 0;
		shell.setLayout (gl_shell);
		
		tree = new AlloggiatoTree (shell, SWT.CHECK);
		tree.insertRecords (fManager.loadFile ("/home/alberto.bonizzi/Desktop/alloggiati.csv"));
		GridData gd_tree = new GridData (SWT.FILL, SWT.FILL, true, true, 1, 1);
		gd_tree.heightHint = 146;
		tree.setLayoutData (gd_tree);
		
		shell.open ();
		
		while (!shell.isDisposed ()) {
			
			if (!display.readAndDispatch ()) {
				display.sleep ();
			}
		}
	}
}
