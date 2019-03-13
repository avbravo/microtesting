/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.microtesting;

import com.avbravo.jmoordb.pojos.UserInfo;
import com.avbravo.jmoordbunit.anotation.Test;
import com.avbravo.jmoordbunit.datatable.ColView;
import com.avbravo.jmoordbunit.datatable.RowView;
import com.avbravo.jmoordbunit.htmlcomponents.Checkbox;
import com.avbravo.jmoordbunit.htmlcomponents.InputText;
import com.avbravo.jmoordbunit.htmlcomponents.Item;
import com.avbravo.jmoordbunit.htmlcomponents.Radio;
import com.avbravo.jmoordbunit.htmlcomponents.SelectOneMenu;
import com.avbravo.jmoordbunit.interfaces.ITest;
import com.avbravo.jmoordbunit.test.UnitTest;
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
@DependsOn("BodegaTest")
@Test
public class ColorTest implements ITest {

    @Inject
    UnitTest unitTest;

    @Inject
    ColorRepository colorRepository;

    @PostConstruct
    @Override
    public void init() {
        unitTest.start(ColorTest.class);
        save();
        panelSelectOneMenu();
        panelDataTable();
        radio();
        checkbox();
        allComponents();
        unitTest.terminate();
    }

    /**
     *
     */
    @PreDestroy
    @Override
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
                unitTest.form();
                unitTest.formTitle("Registros de colores");
                unitTest.panel();

                unitTest.panelAddInputText(Arrays.asList(new InputText("idcolor", color.getIdcolor()),
                        new InputText("activo", color.getActivo())
                ));

                unitTest.panelClose();
                unitTest.buttonGreen("Save");
                unitTest.formClose();

                unitTest.errorMessage("No se pudo guardar");

            }

        } catch (Exception e) {
            System.out.println(nameOfMethod() + e.getLocalizedMessage());
        }

        return "";
    }

    @Test
    public String panelSelectOneMenu() {
        try {

            List<Color> colorList = colorRepository.findAll();
            Boolean found = unitTest.assertFalse(nameOfMethod(), colorList.isEmpty());
            if (found) {
                /*
            Dibuja la interfaz
                 */

                unitTest.form();
                unitTest.formTitle("panelSelectOneMenu()");
                unitTest.panel();

                //-----------------------------
                List<Item> itemList = new ArrayList<>();
                colorList.stream().map((c) -> new Item(c.getIdcolor(), c.getIdcolor(), c.getIdcolor())).forEachOrdered((item) -> {
                    itemList.add(item);
                });
                unitTest.panelAddSelectOneMenu(
                        Arrays.asList(new SelectOneMenu("color", itemList)));

                unitTest.panelClose();

                unitTest.formClose();
            } else {
                unitTest.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
            System.out.println(nameOfMethod()+" " + e.getLocalizedMessage());
        }

        return "";
    }

    @Test
    public String panelDataTable() {
        try {

            /*
            Dibuja la interfaz
             */
            unitTest.form();
            unitTest.formTitle("panelDataTable()");
            unitTest.panel();

            unitTest.panelAddTableHeader("colores", Arrays.asList(new RowView("idcolor"), new RowView("activo")));
            colorRepository.findAll().forEach((c) -> {
                unitTest.panelAddTableCol(Arrays.asList(new ColView(c.getIdcolor()), new ColView(c.getActivo())));
            });
            unitTest.panelAddTableClose();

            unitTest.panelClose();

            unitTest.formClose();

        } catch (Exception e) {
            System.out.println(nameOfMethod()+" " + e.getLocalizedMessage());
        }

        return "";
    }

    @Test
    public String radio() {
        try {
            //titulo de la tabla
            List<Color> colorList = colorRepository.findAll();
            Boolean found = unitTest.assertFalse(nameOfMethod(), colorList.isEmpty());
            if (found) {

                unitTest.form();
                unitTest.formTitle("Radio");
                unitTest.panel();

                unitTest.panelAddRadio(Arrays.asList(new Radio("sexo",
                        Arrays.asList(new Item("sexo", "Masculino", "Masculino"),
                                new Item("sexo", "Femenino", "Femenino")))));

                unitTest.panelClose();
                unitTest.formClose();

            } else {
                unitTest.errorMessage("No tiene registros");
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
            Boolean found = unitTest.assertFalse(nameOfMethod(), colorList.isEmpty());
            if (found) {

                unitTest.form();
                unitTest.formTitle("Checkbox");
                unitTest.panel();

                unitTest.panelAddCheckbox("sexo", Arrays.asList(new Checkbox("masculino", "Masculino", "Masculino"),
                        new Checkbox("femenino", "Femenino", "Femenino")));

                unitTest.panelClose();
                unitTest.formClose();

            } else {
                unitTest.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
                 System.out.println(nameOfMethod()+" " + e.getLocalizedMessage());
        }
        return "";
    }

    @Test
    public String allComponents() {
        try {
            List<Color> colorList = colorRepository.findAll();

            Boolean found = unitTest.assertFalse(nameOfMethod(), colorList.isEmpty());
            if (found) {
                /*
            Dibuja la interfaz
                 */
                unitTest.form();
                unitTest.formTitle("allComponents()");
                unitTest.panel();

                //-- Formulario
                Color color = new Color();
                color = colorList.get(0);

                unitTest.panelAddInputText(Arrays.asList(new InputText("idcolor", color.getIdcolor()),
                        new InputText("activo", color.getActivo())));

                //-- SelectOneMenu
                List<Item> itemList = new ArrayList<>();
                colorList.stream().map((c) -> new Item(c.getIdcolor(), c.getIdcolor(), c.getIdcolor())).forEachOrdered((item) -> {
                    itemList.add(item);
                });
                unitTest.panelAddSelectOneMenu(
                        Arrays.asList(new SelectOneMenu("color", itemList)));

                //-- Tabla
                unitTest.panelAddTableHeader("colores", Arrays.asList(new RowView("idcolor"), new RowView("activo")));
                colorRepository.findAll().forEach((c) -> {
                    unitTest.panelAddTableCol(Arrays.asList(new ColView(c.getIdcolor()), new ColView(c.getActivo())));
                });
                unitTest.panelAddTableClose();
                unitTest.panelClose();

                //-- Otra fila
                unitTest.panel();
                //-- Radio

                unitTest.panelAddRadio(Arrays.asList(new Radio("sexo",
                        Arrays.asList(new Item("sexo", "Masculino", "Masculino"),
                                new Item("sexo", "Femenino", "Femenino")))));

                //-- CheckBox
                unitTest.panelAddCheckbox("sexo", Arrays.asList(new Checkbox("masculino", "Masculino", "Masculino"),
                        new Checkbox("femenino", "Femenino", "Femenino")));

                unitTest.panelClose();

                unitTest.formClose();
            } else {
                unitTest.errorMessage("No tiene registros");
            }

        } catch (Exception e) {
                  System.out.println(nameOfMethod()+" " + e.getLocalizedMessage());
        }

        return "";
    }

}
