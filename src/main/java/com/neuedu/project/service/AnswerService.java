package com.neuedu.project.service;

import com.neuedu.project.domain.AnswerSheet;
import com.neuedu.project.domain.Score;
import com.neuedu.project.domain.ScoreData;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AnswerService {

    /**
     * 添加答题卡信息
     *
     * @param studentId 学生Id
     * @param testId 试卷Id
     * @param ca 选择题答案字符串
     * @param sa 主观题答案字符串
     */
    void addAnswerSheet(String studentId, int testId, String ca,String sa);

    /**
     * 评选择题分数
     *
     * @param teacherId 老师Id -> 学生id -> 做答信息
     * @param testId 要评选择题分的考试Id -> 正确答案
     */
    boolean scoreChoiceQuestion(String teacherId, int testId);

    /**
     * 学生获得考试成绩
     *
     * @param studentId 学生Id
     * @param testId 考试Id
     */
    Score getChoiceScore(String studentId, int testId);


    /**
     * 老死获得主观题标准答案
     *
     * @param testId 考试Id
     */
    //void getSubjectiveAnswer(int testId);

    /**
     * 获取排名信息
     *
     * @param studentId 学生Id
     * @param testId 考试Id
     * @return 排名信息
     */
    Score getRankByChoiceScore(String studentId, int testId);


    /**
     * 老师获得成绩管理信息
     *
     * @param testId 考试Id
     * @return
     */
    ScoreData getGradeManageByTeacher(int testId);


    /**
     * 判断重复提交试卷
     *
     * @param studentId
     * @param testId
     * @return 重复为true，单次为false
     */
    boolean duclipSubmitAnswerSheet(String studentId, int testId);


}
