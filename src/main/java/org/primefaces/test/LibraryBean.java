package org.primefaces.test;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;

import org.primefaces.extensions.component.sheet.Sheet;
import org.primefaces.extensions.event.SheetEvent;
import org.primefaces.extensions.model.sheet.SheetUpdate;
import org.primefaces.test.MockDatabase.DatabaseItems;

@Named
@ViewScoped
public class LibraryBean implements Serializable {
    private static final String MOVIES = "Movies";
    private static final String BOOKS = "Books";
    private String selectedLibrary = MOVIES;
    private List<Item> libraryItems;
    private String enteredTitle = "";

    @PostConstruct
    public void init() {
        libraryItems = new ArrayList<>();

        initSheet();
    }

    public void addItem() {
        if (enteredTitle.length() == 0) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Missing Field.", "Title is required"));
            return;
        }
        if (enteredTitle.length() < 2) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Invalid Title.",
                            "Title must be greater than 2 characters."));
        }
    }

    public void onLibrarySelect() {
        initSheet();
    }

    public void onCellChange(final SheetEvent event) {
        final Sheet sheet = event.getSheet();
        final List<SheetUpdate> updates = sheet.getUpdates();
        final HashSet<Item> processed = new HashSet<>();
        for (final SheetUpdate update : updates) {
            final Item item = (Item) update.getRowData();
            if (!processed.contains(item)) {
                DatabaseItems.update(item);
                processed.add(item);
            }
        }
        sheet.commitUpdates();
    }

    public void onRowAdd() {
        String startIdxStr = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap()
                .get("startIndex");
        int startIdx = Integer.parseInt(startIdxStr);
        String type = selectedLibrary.equals(MOVIES) ? DatabaseItems.MOVIE : DatabaseItems.BOOK;
        Item item = new Item("", type);
        // Add the item in the desired index
        libraryItems.add(startIdx, item);
        // Remove all the database entries for the selected type and insert the ones.
        // This keeps the desired order.
        DatabaseItems.clear(type);
        DatabaseItems.addAll(libraryItems);
    }

    public void onRowRemove() {
    }

    public void update() {
        initSheet();
    }

    public void reset() {
        DatabaseItems.init();
        initSheet();
    }

    /**
     * @return String return the selectedLibrary
     */
    public String getSelectedLibrary() {
        return selectedLibrary;
    }

    /**
     * @param selectedLibrary the selectedLibrary to set
     */
    public void setSelectedLibrary(String selectedLibrary) {
        this.selectedLibrary = selectedLibrary;
    }

    /**
     * @return {@code List<String>} return the libraryItems
     */
    public List<Item> getLibraryItems() {
        return libraryItems;
    }

    /**
     * @param libraryItems the libraryItems to set
     */
    public void setLibraryItems(List<Item> libraryItems) {
        this.libraryItems = libraryItems;
    }

    public String getEnteredTitle() {
        return enteredTitle;
    }

    public void setEnteredTitle(String enteredTitle) {
        this.enteredTitle = enteredTitle;
    }

    private void initSheet() {
        libraryItems = fetchLibrary();
    }

    private List<Item> fetchLibrary() {
        switch (selectedLibrary) {
            case MOVIES:
                return DatabaseItems.getMovies();
            case BOOKS:
                return DatabaseItems.getBooks();
            default:
                return Arrays.asList();
        }
    }
}
