package com.zhezhu.assessment.application.assess;

import com.google.common.collect.Lists;
import com.zhezhu.assessment.domain.model.assesse.*;
import com.zhezhu.assessment.domain.model.collaborator.*;
import com.zhezhu.assessment.domain.model.index.Index;
import com.zhezhu.assessment.domain.model.index.IndexRepository;
import com.zhezhu.assessment.domain.model.medal.AwardRepository;
import com.zhezhu.assessment.infrastructure.message.AssessMessage;
import com.zhezhu.commons.message.MessageEvent;
import com.zhezhu.commons.message.MessageListener;
import com.zhezhu.commons.message.Messagingable;
import com.zhezhu.commons.util.ArraysUtilWrapper;
import com.zhezhu.commons.util.CollectionsUtilWrapper;
import com.zhezhu.share.domain.id.PersonId;
import com.zhezhu.share.domain.id.assessment.AssessId;
import com.zhezhu.share.domain.id.assessment.AssesseeId;
import com.zhezhu.share.domain.id.assessment.AssessorId;
import com.zhezhu.share.domain.id.index.IndexId;
import com.zhezhu.share.domain.id.school.SchoolId;
import com.zhezhu.share.infrastructure.school.SchoolService;
import com.zhezhu.share.infrastructure.school.StudentData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 评价应用服务
 *
 * @author Liguiqing
 * @since V3.0
 */

@Service
@Slf4j
public class AssessApplicationService {

    @Autowired
    private AssessService assesseService;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private AssessRepository assessRepository;

    @Autowired
    private AssessTeamRepository assessTeamRepository;

    @Autowired
    private AssessorRepository assessorRepository;

    @Autowired
    private AssesseeRepository assesseeRepository;

    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private SchoolService schoolService;

    @Autowired
    private RankService rankService;

    @Autowired
    private AssessRankRepository rankRepository;

    @Autowired(required = false)
    private MessageListener messageListener;

    /**
     * 按学校生成评价组
     *
     * @param schoolId Value of {@link SchoolId}
     */
    @Transactional(rollbackFor = Exception.class)
    public void genAssessTeamsOf(String schoolId){
        log.debug("Gen assess teams for {}",schoolId);

        AssessTeamService teamService = new AssessTeamService();
        List<AssessTeam> schoolTeams = teamService.teamOfSchool(new SchoolId(schoolId), schoolService);

        if(CollectionsUtilWrapper.isNullOrEmpty(schoolTeams))
            return;
        ArrayList<AssessTeam> existsTeams = Lists.newArrayList();
        ArrayList<AssessTeam> existsTeams2 = Lists.newArrayList();
        for(AssessTeam team:schoolTeams){
            AssessTeam team_ = assessTeamRepository.findByTeamId(team.getTeamId());
            if(team_ != null){
                existsTeams.add(team_);
                existsTeams2.add(team);
                continue;
            }
            int i = 0;
            for(AssessTeam pt:existsTeams2){
                if(pt.getAssessTeamId().equals(team.getParentAssessTeamId())){
                    AssessTeam pt_ = existsTeams.get(i);
                    team.updateParent(pt_);
                    break;
                }
                i++;
            }
            assessTeamRepository.save(team);
        }
    }

    /**
     * 进行一次评价
     *
     * @param command {@link NewAssessCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void assess(NewAssessCommand command){
        log.debug("New assess for {}",command);

        Index index = indexRepository.loadOf(new IndexId(command.getIndexId()));
        Assessor assessor = assessorRepository.loadOf(new AssessorId(command.getAssessorId()));
        Assessee assessee = assesseeRepository.loadOf(new AssesseeId(command.getAssesseeId()));

        //以下方法只实现老师评价学生非走班情况,走班还要根据Assessor的教师信息进行判断,最好做成一个接口来处理AssessTeam,才能做到业务抽象,以后要改
        PersonId assesseePersonId = assessee.getCollaborator().getPersonId();
        StudentData student = schoolService.getStudentBy(assesseePersonId);
        String clazzId = student.getManagedClazzId();
        AssessTeam team = assessTeamRepository.findByTeamId(clazzId);

        List<Assess> assesses = assesseService.newAssesses(index, assessor, assessee, command.getScore());
        assesses.forEach(assess -> {
            assess.associateTo(team);
            assessRepository.save(assess);
        });
        sendMessage(new AssessMessage(index,assessee,command.getScore()));
    }

    /**
     * 进行一组评价
     *
     * @param commands array of {@link NewAssessCommand}
     */
    @Transactional(rollbackFor = Exception.class)
    public void assesses(NewAssessCommand[] commands){
        if(ArraysUtilWrapper.isNotNullAndNotEmpty(commands)){
            for(NewAssessCommand command:commands){
                if(command != null){
                    this.assess(command);
                }
            }
        }
    }


    /**
     * 老师评价学生
     *
     * @param command {@link NewTeacherAssessStudentCommand}
     * @return array of {@link AssessId}
     */
    @Transactional(rollbackFor = Exception.class)
    public String[] teacherAssessStudent(NewTeacherAssessStudentCommand command){
        log.debug("Teacher assess Student {}",command);
        List<IndexAssess> assesses = command.getAssesses();
        if(CollectionsUtilWrapper.isNullOrEmpty(assesses))
            return new String[]{};

        PersonId teacherPersonId = new PersonId(command.getTeacherPersonId());
        PersonId studentPersonId = new PersonId(command.getStudentPersonId());
        SchoolId schoolId = new SchoolId(command.getSchoolId());
        Assessor assessor = teacherAsAssessor(teacherPersonId, schoolId);
        Assessee assessee = studentAsAssessee(studentPersonId, schoolId);

        StudentData student = schoolService.getStudentBy(studentPersonId);
        String clazzId = student.getManagedClazzId();
        AssessTeam team = assessTeamRepository.findByTeamId(clazzId);

        String[] assessIds = new String[assesses.size()];
        int i= 0;
        for(IndexAssess assess:assesses){
            Index index = null;
            if(assess.getIndexId() != null){
                index = indexRepository.loadOf(new IndexId(assess.getIndexId()));
            }
            Assess assess_ = assesseService.newAssess(index, assessor, assessee, assess.getScore(), assess.getWord());
            if(index != null){
                sendMessage(new AssessMessage(index,assessee,assess.getScore()));
            }else{
                sendMessage(new AssessMessage(assessee,assess.getWord()));
            }
            assess_.associateTo(team);
            assessRepository.save(assess_);
            assessIds[i++] = assess_.getAssessId().id();
        }
        return assessIds;
    }

    /**
     * 按评价组进行一次全排名:即计算{@link RankCategory#values()}及{@link RankScope#values()}排名
     *
     * @param teamId value of {@link SchoolId} or {@link com.zhezhu.share.domain.id.school.ClazzId}
     */
    @Transactional(rollbackFor = Exception.class)
    public void rank(String teamId){
        log.debug("Rank all for {}",teamId);
        for(RankCategory category:RankCategory.values()){
            for(RankScope scope:RankScope.values()){
                rank(teamId, category, scope);
            }
        }
    }

    /**
     * 按评价组Id进行一次分类分范围排名
     *
     * @param teamId value of {@link SchoolId} or {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param category name() of {@link RankCategory}
     * @param scope name() of {@link RankScope}
     */
    @Transactional(rollbackFor = Exception.class)
    public void rank(String teamId,String category,String scope){
        log.debug("Rank for {} {} {}",teamId,category,scope);

        RankCategory category1 = RankCategory.valueOf(category);
        RankScope scope1 = RankScope.valueOf(scope);
        rank(teamId,category1,scope1);
    }

    /**
     * 按评价组Id进行一次分类分范围排名
     *
     * @param teamId value of {@link SchoolId} or {@link com.zhezhu.share.domain.id.school.ClazzId}
     * @param category {@link RankCategory}
     * @param scope {@link RankScope}
     */
    @Transactional(rollbackFor = Exception.class)
    public void rank(String teamId,RankCategory category,RankScope scope){
        List<AssessRank> ranks = rankService.rank(teamId, category, scope);
        for(AssessRank rank:ranks){
            rankRepository.deleteByPersonIdAndAssessTeamIdAndRankCategoryAndRankScopeAndRankNodeAndYearStartsAndYearEnds(
                    rank.getPersonId(),rank.getAssessTeamId(),rank.getRankCategory(),rank.getRankScope(),rank.getRankNode(),
                    rank.getYearStarts(),rank.getYearEnds()
            );
            rankRepository.save(rank);
        }
    }

    private Assessor teacherAsAssessor(PersonId teacherPersonId,SchoolId schoolId){
        Assessor assessor = assessorRepository.findByPersonIdAndSchoolId(teacherPersonId, schoolId);
        if(assessor == null){
            assessor = Assessor.builder()
                    .assessorId(assessorRepository.nextIdentity())
                    .personId(teacherPersonId)
                    .role(CollaboratorRole.Teacher.name())
                    .schoolId(schoolId)
                    .build();
            assessorRepository.save(assessor);
        }
        return assessor;
    }

    private Assessee studentAsAssessee(PersonId stuentPersonId,SchoolId schoolId){
        Assessee assessee = assesseeRepository.findByPersonIdAndSchoolId(stuentPersonId, schoolId);
        if(assessee == null){
            assessee = Assessee.builder()
                    .assesseeId(assesseeRepository.nextIdentity())
                    .personId(stuentPersonId)
                    .role(CollaboratorRole.Student.name())
                    .schoolId(schoolId)
                    .build();
        }
        return assessee;
    }


    private void sendMessage(Messagingable messagingable){
        if(this.messageListener != null){
            this.messageListener.addEvent(new MessageEvent(messagingable));
        }
    }
}