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
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers;
import com.vaadin.flow.theme.lumo.LumoUtility.Gap;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import jakarta.annotation.security.PermitAll;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.lineawesome.LineAwesomeIconUrl;

@PageTitle("Registro De Estudiantes UTH - Programación Avanzada I")
@Route("")
@Menu(order = 0, icon = LineAwesomeIconUrl.UNIVERSITY_SOLID)
@PermitAll
@Uses(Icon.class)
public class RegistroDeEstudiantesUTHProgramaciónAvanzadaIView extends Composite<VerticalLayout> {

    private final List<SamplePerson> persons = new ArrayList<>();
    private final Grid<SamplePerson> basicGrid = new Grid<>(SamplePerson.class);

    @Autowired
    private SamplePersonService samplePersonService;

    public RegistroDeEstudiantesUTHProgramaciónAvanzadaIView() {
        HorizontalLayout layoutRow = new HorizontalLayout();
        VerticalLayout layoutColumn2 = new VerticalLayout();
        VerticalLayout layoutColumn3 = new VerticalLayout();
        TextField textField = new TextField("Nombre Completo");
        TextField textField2 = new TextField("No. de Cuenta");
        EmailField emailField = new EmailField("Email");
        TextField textField3 = new TextField("Teléfono");
        DatePicker datePicker = new DatePicker("Fecha de Ingreso");
        ComboBox<SampleItem> comboBox = new ComboBox<>("Carrera");
        ComboBox<SampleItem> comboBox2 = new ComboBox<>("Campus");
        Checkbox checkbox = new Checkbox("Activo");
        Button buttonPrimary = new Button("Agregar");
        Button buttonDelete = new Button("Eliminar");
        Anchor link = new Anchor("https://www.uth.hn/", "Página Principal UTH");

        // Botón para abrir la ventana de búsqueda
        Button openSearchDialogButton = new Button("Buscar");
        openSearchDialogButton.addClickListener(event -> {
            Dialog searchDialog = createSearchDialog();
            searchDialog.open();
        });

        // Configurar componentes
        getContent().setWidth("170%");
        getContent().getStyle().set("flex-grow", "1");
        layoutRow.addClassName(Gap.MEDIUM);
        layoutRow.setWidth("100%");
        layoutRow.getStyle().set("flex-grow", "1");
        layoutColumn2.setWidth("100%");
        layoutColumn2.getStyle().set("flex-grow", "1");
        basicGrid.setWidth("100%");
        basicGrid.getStyle().set("flex-grow", "0");
        setGridSampleData(basicGrid);
        layoutColumn3.getStyle().set("flex-grow", "1");

        comboBox.setWidth("min-content");
        setComboBoxSampleData(comboBox);
        comboBox2.setWidth("min-content");
        setComboBoxSampleDataForSecond(comboBox2);
        buttonPrimary.setWidth("min-content");
        buttonPrimary.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        link.setWidth("150px");

        // Configurar las cabeceras de las columnas en español
        basicGrid.removeAllColumns();
        basicGrid.addColumn(SamplePerson::getFirstName).setHeader("Nombre Completo").setWidth("250px").setKey("firstName");
        basicGrid.addColumn(SamplePerson::getLastName).setHeader("Número de Cuenta").setWidth("180px").setKey("lastName");
        basicGrid.addColumn(SamplePerson::getEmail).setHeader("Correo Electrónico").setWidth("250px").setKey("email");
        basicGrid.addColumn(SamplePerson::getPhone).setHeader("Teléfono").setWidth("100px").setKey("phone");
        basicGrid.addColumn(person -> person.getDateOfBirth() != null ? person.getDateOfBirth().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) : "").setHeader("Fecha de Ingreso").setWidth("130px").setKey("dateOfBirth");
        basicGrid.addColumn(SamplePerson::getOccupation).setHeader("Carrera").setWidth("250px").setKey("occupation");
        basicGrid.addColumn(SamplePerson::getRole).setHeader("Campus").setWidth("150px").setKey("role");
        basicGrid.addColumn(person -> person.isImportant() ? "Activo" : "Inactivo").setHeader("Activo").setWidth("100px").setKey("important");

        // Agregar componentes al diseño
        getContent().add(layoutRow);
        layoutRow.add(layoutColumn2);
        layoutColumn2.add(openSearchDialogButton);
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
        getContent().add(link);

        // Configurar el evento de clic del botón de agregar
        buttonPrimary.addClickListener(event -> agregarDatos(textField, textField2, emailField, textField3, datePicker, comboBox, comboBox2, checkbox));

        // Configurar el evento de clic del botón de eliminar
        buttonDelete.addClickListener(event -> eliminarDatos());
layoutColumn3.add(buttonPrimary);
layoutColumn3.add(buttonDelete);
layoutColumn3.add(buttonDelete);
    }

    // Método para crear el diálogo de búsqueda
    private Dialog createSearchDialog() {
        Dialog searchDialog = new Dialog();
        searchDialog.setWidth("400px");

        VerticalLayout layout = new VerticalLayout();
        TextField searchField = new TextField("Buscar por Nombre");
        Button searchButton = new Button("Buscar Registro");

        searchButton.addClickListener(event -> {
            String searchTerm = searchField.getValue().toLowerCase();
            basicGrid.setItems(persons.stream()
                    .filter(person -> person.getFirstName().toLowerCase().contains(searchTerm))
                    .collect(Collectors.toList()));
            searchDialog.close();
        });

        layout.add(searchField, searchButton);
        searchDialog.add(layout);

        return searchDialog;
    }

    // Método para manejar el evento de clic del botón y agregar datos
    private void agregarDatos(TextField textField, TextField textField2, EmailField emailField, TextField textField3, DatePicker datePicker, ComboBox<SampleItem> comboBox, ComboBox<SampleItem> comboBox2, Checkbox checkbox) {
        // Obtener los valores de los componentes
        String firstName = textField.getValue(); // Guardar el nombre completo en firstName
        String accountNumber = textField2.getValue(); // No. de Cuenta se guarda en lastName
        String email = emailField.getValue();
        String phone = textField3.getValue();
        LocalDate dateOfBirth = datePicker.getValue();
        String occupation = comboBox.getValue() != null ? comboBox.getValue().label() : null;
        String role = comboBox2.getValue() != null ? comboBox2.getValue().label() : null;
        boolean important = checkbox.getValue();

        // Validaciones
        if (firstName.isEmpty() || accountNumber.isEmpty() || email.isEmpty() || phone.isEmpty() || dateOfBirth == null || occupation == null || role == null) {
            Notification.show("Todos los campos deben estar completos.");
            return;
        }
        if (!isValidEmail(email) || !isValidPhone(phone) || dateOfBirth.isAfter(LocalDate.now())) {
            Notification.show("El correo, teléfono o la fecha de ingreso no son válidos.");
            return;
        }

        // Crear un nuevo SamplePerson y agregarlo a la lista
        SamplePerson newPerson = new SamplePerson();
        newPerson.setFirstName(firstName);
        newPerson.setLastName(accountNumber); // Guardar el No. de Cuenta en lastName
        newPerson.setEmail(email);
        newPerson.setPhone(phone);
        newPerson.setDateOfBirth(dateOfBirth);
        newPerson.setOccupation(occupation);
        newPerson.setRole(role);
        newPerson.setImportant(important);

        persons.add(newPerson);

        // Guardar la nueva persona en la base de datos
        samplePersonService.save(newPerson);

        // Actualizar el Grid con la nueva lista de personas
        basicGrid.setItems(persons);

        // Limpiar los campos de entrada
        textField.clear();
        textField2.clear();
        emailField.clear();
        textField3.clear();
        datePicker.clear();
        comboBox.clear();
        comboBox2.clear();
        checkbox.clear();

        // Lógica adicional para manejar los datos agregados (guardar en una base de datos, etc.)
        System.out.println("Datos agregados: " + newPerson);
    }

    // Método para manejar el evento de clic del botón y eliminar datos
    private void eliminarDatos() {
        SamplePerson selectedPerson = basicGrid.asSingleSelect().getValue();
        if (selectedPerson != null) {
            ConfirmDialog dialog = new ConfirmDialog();
            dialog.setHeader("Confirmar Eliminación");
            dialog.setText("¿Estás seguro de que deseas eliminar este registro?");
            dialog.setCancelable(true);

            dialog.setConfirmText("Eliminar");
            dialog.addConfirmListener(event -> {
                // Eliminar la persona seleccionada de la lista y de la base de datos
                persons.remove(selectedPerson);
                samplePersonService.deleteById(selectedPerson.getId());

                // Actualizar el Grid con la nueva lista de personas
                basicGrid.setItems(persons);

                // Lógica adicional para manejar los datos eliminados
                System.out.println("Datos eliminados: " + selectedPerson);
            });

            dialog.setCancelText("Cancelar");
            dialog.addCancelListener(event -> {
                // Cancelar la eliminación
                System.out.println("Eliminación cancelada");
            });

            dialog.open();
        } else {
            Notification.show("Por favor, selecciona una persona para eliminar.");
        }
    }

    private void setGridSampleData(Grid<SamplePerson> grid) {
        grid.setItems(query -> samplePersonService.list(VaadinSpringDataHelpers.toSpringPageRequest(query)).stream());
    }

    record SampleItem(String value, String label, Boolean disabled) {}

    private void setComboBoxSampleData(ComboBox<SampleItem> comboBox) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("Ingeniería en Computación", "Ingeniería en Computación", null));
        sampleItems.add(new SampleItem("Ingeniería en Sistemas", "Ingeniería en Sistemas", Boolean.TRUE));;
        sampleItems.add(new SampleItem("Ingeniería en Electrónica", "Ingeniería en Electrónica", null));
        sampleItems.add(new SampleItem("Ingeniería en Mecatrónica", "Ingeniería en Mecatrónica", null));
        sampleItems.add(new SampleItem("Ingeniería en Producción Industrial", "Ingeniería en Producción Industrial", null));
        sampleItems.add(new SampleItem("Licenciatura en Turismo", "Licenciatura en Turismo", null));
        sampleItems.add(new SampleItem("Licenciatura en Derecho", "Licenciatura en Derecho", null));
        sampleItems.add(new SampleItem("Licenciatura en Relaciones Industriales", "Licenciatura en Relaciones Industriales", null));
        sampleItems.add(new SampleItem("Licenciatura en Comercio y Negocios Internacionales", "Licenciatura en Comercio y Negocios Internacionales", null));
        sampleItems.add(new SampleItem("Licenciatura en Contaduría Financiera", "Licenciatura en Contaduría Financiera", null));
        sampleItems.add(new SampleItem("Licenciatura en Marketing", "Licenciatura en Marketing", null));
        sampleItems.add(new SampleItem("Licenciatura en Gerencia de Negocios", "Licenciatura en Gerencia de Negocios", null));
        sampleItems.add(new SampleItem("Licenciatura en Ingeniería Financiera", "Licenciatura en Ingeniería Financiera", null));
        sampleItems.add(new SampleItem("Licenciatura en Informática Administrativa", "Licenciatura en Informática Administrativa", null));
        sampleItems.add(new SampleItem("Licenciatura en Gerencia de Negocios Agro-Cafetaleros", "Licenciatura en Gerencia de Negocios Agro-Cafetaleros", null));
        sampleItems.add(new SampleItem("Licenciatura en Emprendimiento Gerencial", "Licenciatura en Emprendimiento Gerencial", null));
        sampleItems.add(new SampleItem("Licenciatura en Psicología", "Licenciatura en Psicología", null));
        sampleItems.add(new SampleItem("Licenciatura en Recursos Humanos", "Licenciatura en Recursos Humanos", null));
        sampleItems.add(new SampleItem("Licenciatura en Comunicación Corporativa y Relaciones Públicas", "Licenciatura en Comunicación Corporativa y Relaciones Públicas", null));

        comboBox.setItems(new ArrayList<>(sampleItems));
        comboBox.setItemLabelGenerator(SampleItem::label);
    }

    private void setComboBoxSampleDataForSecond(ComboBox<SampleItem> comboBox2) {
        List<SampleItem> sampleItems = new ArrayList<>();
        sampleItems.add(new SampleItem("San Pedro Sula", "San Pedro Sula", Boolean.TRUE));
        sampleItems.add(new SampleItem("La Ceiba", "La Ceiba", null));
        sampleItems.add(new SampleItem("Puerto Cortés", "Puerto Cortés", null));
        sampleItems.add(new SampleItem("El Progreso", "El Progreso", null));
        sampleItems.add(new SampleItem("Santa Bárbara", "Santa Bárbara", null));
        sampleItems.add(new SampleItem("Tegucigalpa", "Tegucigalpa", null));
        sampleItems.add(new SampleItem("Roatán", "Roatán", null));
        sampleItems.add(new SampleItem("Siguatepeque", "Siguatepeque", null));
        sampleItems.add(new SampleItem("Tocoa", "Tocoa", null));
        sampleItems.add(new SampleItem("Choluteca", "Choluteca", null));
        sampleItems.add(new SampleItem("Choloma", "Choloma", null));
        sampleItems.add(new SampleItem("Juticalpa", "Juticalpa", null));
        sampleItems.add(new SampleItem("Cofradía", "Cofradía", null));
        sampleItems.add(new SampleItem("Villanueva", "Villanueva", null));

        comboBox2.setItems(new ArrayList<>(sampleItems));
        comboBox2.setItemLabelGenerator(SampleItem::label);
    }

    // Método para validar el correo electrónico
    private boolean isValidEmail(String email) {
        String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    // Método para validar el número de teléfono
    private boolean isValidPhone(String phone) {
        String phoneRegex = "^[+]?[0-9]{8,16}$";
        Pattern pattern = Pattern.compile(phoneRegex);
        return pattern.matcher(phone).matches();
    }
}
