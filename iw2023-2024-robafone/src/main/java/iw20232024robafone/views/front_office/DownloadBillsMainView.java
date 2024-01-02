package iw20232024robafone.views.front_office;

import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("bills")
public class DownloadBillsMainView  extends VerticalLayout {
    public DownloadBillsMainView {

        H1 title = new H1("Download Past Bills");
        
        //Error cause we need the Bill entity
        Grid<Bill> grid = new Grid<>(Bill.class, false);
        grid.setSelectionMode(Grid.SelectionMode.MULTI);
        grid.addColumn(Bill::getFirstName).setHeader("First name");
        grid.addColumn(Bill::getLastName).setHeader("Last name");
        grid.addColumn(Bill::getEmail).setHeader("Email");

        List<Bill> people = DataService.getPeople();
        grid.setItems(people);

        grid.addSelectionListener(selection -> {
            // System.out.printf("Number of selected people: %s%n",
            // selection.getAllSelectedItems().size());
        });
        add(title, grid);
    }
}
