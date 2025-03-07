package com.example.application.views.registrodeestudiantesuthprogramaciónavanzadai;

import com.example.application.data.SamplePerson;
import com.example.application.services.SamplePersonService;
import com.vaadin.flow.component.Composite;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.Uses;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import jakarta.annotation.security.PermitAll;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Registro De Estudiantes UTH - Programación Avanzada I")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.UNIVERSITY_SOLID)
@PermitAll
@Uses(Icon.class)
public class RegistroDeEstudiantesUTHProgramaciónAvanzadaIView extends Composite<VerticalLayout> {

    public RegistroDeEstudiantesUTHProgramaciónAvanzadaIView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        Avatar avatar = new Avatar();
        Grid basicGrid = new Grid(SamplePerson.class);
        VerticalLayout layoutColumn3 = new VerticalLayout();
        TextField textField = new TextField();
        TextField textField2 = new TextField();
        EmailField emailField = new EmailField();
        TextField textField3 = new TextField();
        DatePicker datePicker = new DatePicker();
        ComboBox comboBox = new ComboBox();
        ComboBox comboBox2 = new ComboBox();
        Checkbox checkbox = new Checkbox();
        Button buttonPrimary = new Button();
        Anchor link = new Anchor();
        getContent().setWidth("100%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        avatar.setName("Firstname Lastname");
        basicGrid.setWidth("100%");
        basicGrid.getStyle().set("flex-grow", "0");
        setGridSampleData(basicGrid);
        layoutColumn3.getStyle().set("flex-grow", "1");
        textField.setLabel("Nombre Completo");
        textField.setWidth("min-content");
        textField2.setLabel("No. de Cuenta");
        textField2.setWidth("min-content");
        emailField.setLabel("Email");
        emailField.setWidth("min-content");
        textField3.setLabel("Teléfono");
        textField3.setWidth("min-content");
        datePicker.setLabel("Fecha de Ingreso");
        datePicker.setWidth("min-content");
        comboBox.setLabel("Carrera");
        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        comboBox2.setLabel("Campus");
        comboBox2.setWidth("min-content");
        setComboBoxSampleData(comboBox2);
        checkbox.setLabel("Activo");
        checkbox.setWidth("min-content");
        buttonPrimary.setText("Agregar");
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        link.setText("Página Principal UTH");
        link.setHref("#");
        link.setWidth("150px");
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(avatar);
        layoutColumn2.add(basicGrid);
        layoutRow.add(layoutColumn3);
        layoutColumn3.add(textField);
        layoutColumn3.add(textField2);
        layoutColumn3.add(emailField);
        layoutColumn3.add(textField3);
        layoutColumn3.add(datePicker);
        layoutColumn3.add(comboBox);
        layoutColumn3.add(comboBox2);
        layoutColumn3.add(checkbox);
        layoutColumn3.add(buttonPrimary);
        getContent().add(link);
    }

    private void setGridSampleData(Grid grid) {
        grid.setItems(query -> samplePersonService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
    }

    @Autowired()
    private SamplePersonService samplePersonService;

    record SampleItem(String value, String label, Boolean disabled) {
    }

    private void setComboBoxSampleData(ComboBox comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("first", "First", null));
        sampleItems.add(new SampleItem("second", "Second", null));
        sampleItems.add(new SampleItem("third", "Third", Boolean.TRUE));
        sampleItems.add(new SampleItem("fourth", "Fourth", null));
        comboBox.setItems(sampleItems);
        comboBox.setItemLabelGenerator(item -> ((SampleItem) item).label());
    }
}
