/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.avbravo.microtesting;

import com.avbravo.jmoordb.pojos.UserInfo;
import com.avbravo.jmoordbunit.anotation.Report;
import com.avbravo.jmoordbunit.anotation.Test;
import com.avbravo.jmoordbunit.interfaces.ITest;
import com.avbravo.jmoordbunit.test.UnitTest;

import com.avbravo.microtestingejb.entity.Bodega;
import com.avbravo.microtestingejb.repository.BodegaRepository;
import java.util.ArrayList;
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
@Test
@Report(path = "/home/avbravo/Descargas/")
public class BodegaTest implements ITest{

    @Inject
    UnitTest unitTest;
    @Inject
    BodegaRepository bodegaRepository;
    
    @PostConstruct
    @Override
    public void init() {
        unitTest.start(BodegaTest.class);
        
        save();
        findAll();

    }

    @Test
    private void save() {
        try {
            
            //Mock
            Bodega bodega = new Bodega();
            bodega.setIdbodega("bodega-test");
            bodega.setDireccion("Panama");
            bodega.setTelefono("(507)");
            //User info es una clase que usa el framework para guardar referencias
            //de usuarios
            List<UserInfo> list = new ArrayList<>();
            bodega.setUserInfo(list);
            bodega.setActivo("si");
            
            unitTest.assertEquals(nameOfMethod(), true,bodegaRepository.save(bodega));
        } catch (Exception e) {
            System.out.println(nameOfMethod() + e.getLocalizedMessage());
        }

    }

    @Test
    private void findAll() {
 
    unitTest.assertNotEquals(nameOfMethod(), 0, bodegaRepository.findAll().size());
 

    }

    @PreDestroy
    @Override
    public void destroy() {
        unitTest.end(BodegaTest.class);
    }
}
