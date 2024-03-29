package com.neuedu.project.controller;

import com.neuedu.project.domain.Score;
import com.neuedu.project.domain.ScoreData;
import com.neuedu.project.service.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 答案相关的控制器.
 *
 * @author ljq
 */
@Controller
@RequestMapping("/answer")
public final class AnswerController {

    @Autowired
    private AnswerService answerService;

    /**
     * 添加答题卡。
     *
     * @param session session
     * @param testId  考试Id
     * @param ca      选择题作答字符串
     * @param sa      主观题作答字符串
     * @return 提示语
     */
    @PostMapping(value = "/add")
    @ResponseBody
    public String addAnswerSheet(HttpSession session, int testId, String ca, String sa) {
        String studentId = (String) session.getAttribute("loggedId");
        answerService.addAnswerSheet(studentId, testId, ca, sa);
        return "交卷成功！";
    }

    /**
     * 给考这门试的学生评选择题分。
     *
     * @param httpSession session
     * @param testId      考试Id
     * @return 成功或失败
     */
    @PostMapping(value = "/score")
    @ResponseBody
    public boolean scoreChoiceQuestion(HttpSession httpSession, int testId) {
        String teacherId = (String) httpSession.getAttribute("loggedId");
        return answerService.scoreChoiceQuestion(teacherId, testId);
    }

    /**
     * 学生查选择题分。
     * 1. 排除学生选课但未参加考试，数据库没有答题记录的情况,返回null
     * 2. 排除非这一科科学生查询这课成绩情况,返回null
     *
     * @param httpSession session
     * @param testId      考试Id
     * @return
     */
    @GetMapping(value = "/query/{testId}")
    @ResponseBody
    public Score getChoiceScore(HttpSession httpSession, @PathVariable int testId) {
        String studentId = (String) httpSession.getAttribute("loggedId");
        return answerService.getChoiceScore(studentId, testId);
    }

    /**
     * 学生查询自己考试分数及教学班排名.
     *
     * @param httpSession session
     * @param testId      考试Id
     * @return Score对象
     */
    @GetMapping(value = "/rank/{testId}")
    @ResponseBody
    public Score getRankByChoiceScore(HttpSession httpSession, @PathVariable int testId) {
        String studentId = (String) httpSession.getAttribute("loggedId");
        return answerService.getRankByChoiceScore(studentId, testId);
    }

    @GetMapping(value = "/ranks/{testId}")
    @ResponseBody
    public ScoreData getGradeManageByTeacher(@PathVariable int testId) {
        return answerService.getGradeManageByTeacher(testId);
    }
}
