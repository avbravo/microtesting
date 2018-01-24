/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.microtesting;

import com.avbravo.ejbjmoordb.pojos.UserInfo;
import com.avbravo.jmoordbunit.anotation.Test;
import com.avbravo.jmoordbunit.datatable.ColView;
import com.avbravo.jmoordbunit.datatable.RowView;
import com.avbravo.jmoordbunit.htmlcomponents.Checkbox;
import com.avbravo.jmoordbunit.htmlcomponents.InputText;
import com.avbravo.jmoordbunit.htmlcomponents.Item;
import com.avbravo.jmoordbunit.htmlcomponents.Radio;
import com.avbravo.jmoordbunit.htmlcomponents.SelectOneMenu;
import com.avbravo.jmoordbunit.test.UnitTest;
import com.avbravo.jmoordbunit.view.UnitView;
import com.avbravo.microtestingejb.entity.Color;
import com.avbravo.microtestingejb.repository.ColorRepository;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.DependsOn;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

/**
 *
 * @author avbravo
 */
@Startup
@Singleton
@DependsOn("TestEnvironment")
public class ColorTest {

    @Inject
    UnitTest unitTest;
    @Inject
    UnitView unitView;
    @Inject
    ColorRepository colorRepository;

    @PostConstruct
    void init() {
        unitTest.start(ColorTest.class);
        unitView.start(ColorTest.class);
        save();
        panelSelectOneMenu();
        panelDataTable();
        radio();
        checkbox();
        allComponents();

    }

    @PreDestroy
    public void destroy() {
        unitTest.end(ColorTest.class);
    }

    @Test
    public String save() {
        try {

//Mock
            Color color = new Color();
            color.setActivo("si");
            color.setAutoincrementable(15);
            color.setIdcolor("rojo");
            List<UserInfo> list = new ArrayList<>();
            color.setUserInfo(list);
            Boolean expResult = true;
            Boolean save = unitTest.assertEquals("save", true, colorRepository.save(color));
            if (!save) {
                /*
            Dibuja la interfaz
                 */
                unitView.form();
                unitView.formTitle("Registros de colores");
                unitView.panel();

                unitView.panelAddInputText(Arrays.asList(new InputText("idcolor", color.getIdcolor()),
                        new InputText("activo", color.getActivo())
                ));

                unitView.panelClose();
                unitView.buttonGreen("Save");
                unitView.formClose();

                unitView.errorMessage("No se pudo guardar");

            }

        } catch (Exception e) {
            System.out.println("save() " + e.getLocalizedMessage());
        }

        return "";
    }

    @Test
    public String panelSelectOneMenu() {
        try {

            List<Color> colorList = colorRepository.findAll();
            Boolean found = unitTest.assertFalse("panelSelectOneMenu()", colorList.isEmpty());
            if (found) {
                /*
            Dibuja la interfaz
                 */

                unitView.form();
                unitView.formTitle("panelSelectOneMenu()");
                unitView.panel();

                //-----------------------------
                List<Item> itemList = new ArrayList<>();
                colorList.stream().map((c) -> new Item(c.getIdcolor(), c.getIdcolor(), c.getIdcolor())).forEachOrdered((item) -> {
                    itemList.add(item);
                });
                unitView.panelAddSelectOneMenu(
                        Arrays.asList(new SelectOneMenu("color", itemList)));

                unitView.panelClose();

                unitView.formClose();
            } else {
                unitView.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
            System.out.println("panelSelectOneMenu() " + e.getLocalizedMessage());
        }

        return "";
    }

    @Test
    public String panelDataTable() {
        try {

            /*
            Dibuja la interfaz
             */
            unitView.form();
            unitView.formTitle("panelDataTable()");
            unitView.panel();

            unitView.panelAddTableHeader("colores", Arrays.asList(new RowView("idcolor"), new RowView("activo")));
            colorRepository.findAll().forEach((c) -> {
                unitView.panelAddTableCol(Arrays.asList(new ColView(c.getIdcolor()), new ColView(c.getActivo())));
            });
            unitView.panelAddTableClose();

            unitView.panelClose();

            unitView.formClose();

        } catch (Exception e) {
            System.out.println("panelDataTable() " + e.getLocalizedMessage());
        }

        return "";
    }

    @Test
    public String radio() {
        try {
            //titulo de la tabla
            List<Color> colorList = colorRepository.findAll();
            Boolean found = unitTest.assertFalse("radio()", colorList.isEmpty());
            if (found) {

                unitView.form();
                unitView.formTitle("Radio");
                unitView.panel();

                unitView.panelAddRadio(Arrays.asList(new Radio("sexo",
                        Arrays.asList(new Item("sexo", "Masculino", "Masculino"),
                                new Item("sexo", "Femenino", "Femenino")))));

                unitView.panelClose();
                unitView.formClose();

            } else {
                unitView.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
            System.out.println("radio() " + e.getLocalizedMessage());
        }
        return "";
    }

    @Test
    public String checkbox() {
        try {
            //titulo de la tabla
            List<Color> colorList = colorRepository.findAll();
            Boolean found = unitTest.assertFalse("checkbox()", colorList.isEmpty());
            if (found) {

                unitView.form();
                unitView.formTitle("Checkbox");
                unitView.panel();

                unitView.panelAddCheckbox("sexo", Arrays.asList(new Checkbox("masculino", "Masculino", "Masculino"),
                        new Checkbox("femenino", "Femenino", "Femenino")));

                unitView.panelClose();
                unitView.formClose();

            } else {
                unitView.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
            System.out.println("checkbox() " + e.getLocalizedMessage());
        }
        return "";
    }

    @Test
    public String allComponents() {
        try {
            List<Color> colorList = colorRepository.findAll();

            Boolean found = unitTest.assertFalse("allComponents()", colorList.isEmpty());
            if (found) {
                /*
            Dibuja la interfaz
                 */
                unitView.form();
                unitView.formTitle("allComponents()");
                unitView.panel();

                //-- Formulario
                Color color = new Color();
                color = colorList.get(0);
               
                unitView.panelAddInputText(Arrays.asList(new InputText("idcolor", color.getIdcolor()),
                        new InputText("activo", color.getActivo())));

                //-- SelectOneMenu
                List<Item> itemList = new ArrayList<>();
                colorList.stream().map((c) -> new Item(c.getIdcolor(), c.getIdcolor(), c.getIdcolor())).forEachOrdered((item) -> {
                    itemList.add(item);
                });
                unitView.panelAddSelectOneMenu(
                        Arrays.asList(new SelectOneMenu("color", itemList)));

                //-- Tabla
                unitView.panelAddTableHeader("colores", Arrays.asList(new RowView("idcolor"), new RowView("activo")));
                colorRepository.findAll().forEach((c) -> {
                    unitView.panelAddTableCol(Arrays.asList(new ColView(c.getIdcolor()), new ColView(c.getActivo())));
                });
                unitView.panelAddTableClose();
                    unitView.panelClose();
                
                    //-- Otra fila
                     unitView.panel();
                    //-- Radio
                    
                unitView.panelAddRadio(Arrays.asList(new Radio("sexo",
                        Arrays.asList(new Item("sexo", "Masculino", "Masculino"),
                                new Item("sexo", "Femenino", "Femenino")))));

                    //-- CheckBox
                    
                      unitView.panelAddCheckbox("sexo", Arrays.asList(new Checkbox("masculino", "Masculino", "Masculino"),
                        new Checkbox("femenino", "Femenino", "Femenino")));

                  unitView.panelClose();

            
           
                unitView.formClose();
            } else {
                unitView.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
            System.out.println("save() " + e.getLocalizedMessage());
        }

        return "";
    }

}
