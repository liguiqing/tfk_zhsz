package com.zhezhu.boot.infrastructure.init;

import com.zhezhu.assessment.application.index.IndexApplicationService;
import com.zhezhu.assessment.application.index.NewIndexCommand;
import com.zhezhu.assessment.domain.model.index.IndexCategory;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.commons.util.DateUtilWrapper;
import com.zhezhu.share.domain.id.SubjectId;
import com.zhezhu.share.domain.id.school.ClazzId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.domain.school.SchoolScope;
import com.zhezhu.sm.application.clazz.ClazzApplicationService;
import com.zhezhu.sm.application.clazz.NewClazzCommand;
import com.zhezhu.sm.application.data.Contacts;
import com.zhezhu.sm.application.data.CourseData;
import com.zhezhu.sm.application.data.StudyData;
import com.zhezhu.sm.application.student.ArrangeStudentCommand;
import com.zhezhu.sm.application.student.NewStudentCommand;
import com.zhezhu.sm.application.student.StudentApplicationService;
import com.zhezhu.sm.application.teacher.ArrangeTeacherCommand;
import com.zhezhu.sm.application.teacher.NewTeacherCommand;
import com.zhezhu.sm.application.teacher.TeacherApplicationService;
import com.zhezhu.sm.domain.model.clazz.UnitedClazz;
import com.zhezhu.sm.domain.model.school.School;
import com.zhezhu.sm.domain.model.school.SchoolRepository;
import com.zhezhu.sm.domain.model.teacher.Teacher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Liguiqing
 * @since V3.0
 */

@Slf4j
@Service
@Transactional
public class DbInitService {

    @Value("${ysyp.app.status:runtime}")
    private String status;

    @Autowired
    private JdbcTemplate jdbc;

    @Autowired(required=false)
    private List<SchoolData> schoolData;

    @Autowired(required=false)
    private IndexApplicationService indexApplicationService;

    @Transactional(rollbackFor = Exception.class)
    public void doInit()throws Exception{
        if(!"testing".equals(status) || CollectionsUtilWrapper.isNullOrEmpty(schoolData)){
            return;
        }
        log.debug("Db initializing ......");
        clear();
        for(SchoolData schoolData:schoolData){
            schoolData.create();
        }

        createCommonIndexes();
    }

    private void createCommonIndexes() {
        if(this.indexApplicationService == null)
            return;

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("积极举手发言")
                .alias("积极举手发言")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("作业完成优秀")
                .alias("作业完成优秀")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("课堂安静听讲")
                .alias("课堂安静听讲")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("成绩优良")
                .alias("成绩优良")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("进步明显")
                .alias("进步明显")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("认真做操")
                .alias("认真做操")
                .categoryName(IndexCategory.Sports.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("帮助同学")
                .alias("帮助同学")
                .categoryName(IndexCategory.Morals.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("课前准备好")
                .alias("课前准备好")
                .categoryName(IndexCategory.Morals.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("热爱活动")
                .alias("热爱活动")
                .categoryName(IndexCategory.Morals.name())
                .plus(true)
                .score(10.0)
                .weight(0.0)
                .build());
        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("课堂讲小话")
                .alias("课堂讲小话")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(false)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("未提交作业")
                .alias("未提交作业")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(false)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("成绩退步")
                .alias("成绩退步")
                .categoryName(IndexCategory.Intelligence.name())
                .plus(false)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("追逐打闹")
                .alias("追逐打闹")
                .categoryName(IndexCategory.Morals.name())
                .plus(false)
                .score(10.0)
                .weight(0.0)
                .build());

        indexApplicationService.newStIndex(NewIndexCommand.builder()
                .name("行为不文明")
                .alias("行为不文明")
                .categoryName(IndexCategory.Morals.name())
                .plus(false)
                .score(10.0)
                .weight(0.0)
                .build());

    }

    private void clear()throws Exception{
        String dbInitFile = ResourceUtils.getURL("classpath:").getPath()+"dbInit.sql";
        List<String> lines = Files.lines(Paths.get(dbInitFile), Charset.defaultCharset())
                .flatMap(line -> Arrays.stream(line.split("\n")))
                .collect(Collectors.toList());

        ArrayList<String> sqls = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        for(String line:lines){
            if(line.length() > 0 ){
                sb.append(line);
            }

            if(line.endsWith(";")){
                sqls.add(sb.toString());
                sb = new StringBuilder();
            }
        }
        String[] tbs = new String[sqls.size()];
        sqls.toArray(tbs);
        jdbc.batchUpdate(tbs);
    }

}