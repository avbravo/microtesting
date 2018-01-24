/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.microtesting;

import com.avbravo.ejbjmoordb.pojos.UserInfo;
import com.avbravo.jmoordbunit.anotation.Test;
import com.avbravo.jmoordbunit.htmlcomponents.InputText;
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
                        new InputText("activo", color.getActivo())));

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

}
