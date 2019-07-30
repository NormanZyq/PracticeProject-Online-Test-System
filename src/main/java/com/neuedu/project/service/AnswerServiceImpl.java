package com.neuedu.project.service;

import com.neuedu.project.dao.*;
import com.neuedu.project.domain.AnswerSheet;
import com.neuedu.project.domain.Question;
import com.neuedu.project.domain.Score;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class AnswerServiceImpl implements AnswerService {

    private final AttendTestRecMapper attendTestRecMapper;

    private final AnswerSheetMapper answerSheetMapper;

    private final TestMapper testMapper;

    private final QuestionMapper questionMapper;

    private final ScoreMapper scoreMapper;

    @Autowired
    public AnswerServiceImpl(AttendTestRecMapper attendTestRecMapper,
                             AnswerSheetMapper answerSheetMapper,
                             TestMapper testMapper,
                             QuestionMapper questionMapper,
                             ScoreMapper scoreMapper){
        this.attendTestRecMapper = attendTestRecMapper;
        this.answerSheetMapper = answerSheetMapper;
        this.testMapper = testMapper;
        this.questionMapper = questionMapper;
        this.scoreMapper= scoreMapper;
    }

    @Override
    public void addAnswerSheet(String studentId, int testId, String ca,String sa){
        int attendTestRecId = attendTestRecMapper.getAttendTestRecId(studentId,testId);
        AnswerSheet answerSheet = new AnswerSheet();
        answerSheet.setAttendRecordId(attendTestRecId);
        answerSheet.setChoiceQuestionAnswer(ca);
        answerSheet.setSubjectiveQuestionAnswer(sa);
        answerSheetMapper.insertAnswers(answerSheet);
    }
    @Override
    public void scoreChoiceQuestion(String teacherId, int testId){
        //testid -> List<AnswerSheet>
        List<AnswerSheet> answerSheets = answerSheetMapper.queryAnswerSheetAboutTest(testId);
        //testid -> List<questionid> -> list<question>
        String questionIds = testMapper.queryTest(testId).getChoiceQuestionIds();
        List<String> questionsId = analyseQuestionId(questionIds," ");
        List<Question> questions = new ArrayList<>();
        for(String qid:questionsId){
            int temp = Integer.parseInt(qid);
            questions.add(questionMapper.getQuestionById(temp));
        }
        for(AnswerSheet as:answerSheets){
            List<String> choiceAnswers = analyseQuestionId(as.getChoiceQuestionAnswer(),"#");
            int sum = 0;
            for(int i = 0; i<Math.min(choiceAnswers.size(),questions.size());i++){
                if(choiceAnswers.get(i).equals(questions.get(i).getRightAnswerString())){
                    sum += questions.get(i).getScore();
                }
            }
            Score score = new Score();
            score.setTeacherId(teacherId);
            score.setAnswerSheetId(as.getId());
            score.setChoicesScore(sum);
            scoreMapper.insertScore(score);
        }
    }

    /**
     * From Internet
     * 字符串切割
     * " "   "#"  ","
     */
    private List<String> analyseQuestionId(String questionIds,String split){
        String[] temp = questionIds.split(split);
        List<String>  questions = new ArrayList<>();
        Collections.addAll(questions, temp);
        return questions;
    }
}
