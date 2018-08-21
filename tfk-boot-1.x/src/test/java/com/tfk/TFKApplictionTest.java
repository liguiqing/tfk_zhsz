package com.tfk;

/**
 * @author Liguiqing
 * @since V3.0
 */

import com.tfk.assessment.port.adapter.http.controller.AssessController;
import com.tfk.assessment.port.adapter.http.controller.CollaboratorController;
import com.tfk.assessment.port.adapter.http.controller.IndexController;
import com.tfk.assessment.port.adapter.http.controller.MedalController;
import com.tfk.boot.port.adapter.http.controller.MainController;
import com.tfk.sm.port.adapter.http.controller.ClazzController;
import com.tfk.sm.port.adapter.http.controller.SchoolController;
import com.tfk.sm.port.adapter.http.controller.StudentController;
import com.tfk.sm.port.adapter.http.controller.TeacherController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = TFKAppliction.class)
public class TFKApplictionTest {

    @Autowired
    private TeacherController teacherController;

    @Autowired
    private SchoolController schoolController;

    @Autowired
    private ClazzController clazzController;

    @Autowired
    private StudentController studentController;

    @Autowired
    private AssessController assessController;

    @Autowired
    private CollaboratorController collaboratorController;

    @Autowired
    private IndexController indexController;

    @Autowired
    private MedalController medalController;

    @Autowired
    private MainController mainController;


    @Test
    public void contextLoads() throws Exception {

    }

}