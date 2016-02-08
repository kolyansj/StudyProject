/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.solution.model.provider;

import com.example.solution.FileSettings;
import com.example.solution.model.Faculty;
import com.example.solution.model.Group;
import com.example.solution.model.Student;
import com.example.solution.model.University;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 *
 * @author Nikolay
 */
public class XMLProvider implements DataProvider {

    //private static final Logger LOG = Logger.getLogger(XMLProvider.class.getName()); 
    
    @Override
    public University loadData(File file) {
        try (InputStream is = new FileInputStream(file)) {
            JAXBContext jaxb = JAXBContext.newInstance(Group.class, Student.class, University.class);
            Unmarshaller unmarsh = jaxb.createUnmarshaller();
            University university = (University) unmarsh.unmarshal(is);
            
            for (Faculty faculty : university.getFaculties()) {
                for (Group group : faculty.getGroups()) {
                    group.setFaculty(faculty);
                    for (Student student : group.getStudents()) {
                        student.setGroup(group);
                    }
                }
            }
            
            return university;
        } catch (FileNotFoundException ex) {
            //LOG.log(Level.ERROR, "Exception: ", ex);
            return new University();
        } catch (IOException | JAXBException ex) {
            //LOG.log(Level.ERROR, "Exception: ", ex);
            System.out.println(ex);
            return new University();
        }
    }
    
    @Override
    public University loadData() {
        FileSettings settings = FileSettings.getInstance();
        File file = settings.getDataFile();
        return loadData(file);
    }

    @Override
    public void saveData(University sch) {
        FileSettings settings = FileSettings.getInstance();
        File universityFile = settings.getDataFile();
        try (OutputStream os = new FileOutputStream(universityFile)) {
            JAXBContext jaxb = JAXBContext.newInstance(Group.class, Student.class, University.class);
            Marshaller marsh = jaxb.createMarshaller();
            marsh.marshal(new University(sch), os);
            //LOG.log(Level.DEBUG, "saved tasks to XML: " + sch);
        } catch (FileNotFoundException ex) {
            //LOG.log(Level.ERROR, "Exception: ", ex);
        } catch (IOException | JAXBException ex) {
            //LOG.log(Level.ERROR, "Exception: ", ex);
        }
    }
    
}
