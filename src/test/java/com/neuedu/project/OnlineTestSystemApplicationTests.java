package com.neuedu.project;

import com.neuedu.project.dao.CourseMapper;
import com.neuedu.project.dao.QuestionMapper;
import com.neuedu.project.domain.*;
import com.neuedu.project.domain.factories.QuestionFactory;
import com.neuedu.project.domain.utils.QuestionUtils;
import com.neuedu.project.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OnlineTestSystemApplicationTests {

    private final String testTeacherId = "iii";

    private final int testCourseId = 1;

    private final String testCourseName = "语文";

    @Autowired
    private CourseService courseService;

    @Autowired
    private QuestionService questionService;

    @Autowired
    private TestService testService;

    @Autowired
    private ArrangeService arrangeService;

    @Autowired
    private AnswerService answerService;

    @Test
    public void contextLoads() {
        assert true;
    }

    @Test
    public void testGetCourses() {
        List<Course> courses = courseService.getCourseByName(testCourseName);
        boolean containsName = false;
//        assert courses.size() == 2;
        for (Course course : courses) {
            if (testCourseName.equals(course.getCourseName())) {
                containsName = true;
//                break;
            }
        }
        assert containsName;
    }

    @Test
    public void testAddCourse() {
//        courseService.addCourse(testTeacherId, "Chinese");
    }

    @Test
    public void testAddTeacherToCourse() {
//        courseService.addTeacherToCourse(testTeacherId, testCourseId);
    }

    @Test
    public void testParse() {
        Question forQuery = new Question();
        forQuery.setCourseId(1);
        forQuery.setQuestionType(1);

        List<Question> results = questionService.getChoiceQuestionByCourseId(1);

        ChoiceQuestion cq = new ChoiceQuestion(results.get(0));
//        System.out.println(cq);
        List<ChoiceQuestion> choiceQuestions = QuestionUtils.getInstance().parseAsChoiceQuestions(results);

        System.out.println(choiceQuestions);
    }

   /* @Test
    public void testOJLD(){
        System.out.println(testService.Calcualte_OJLD(10,5));
        System.out.println(testService.Calcualte_OJLD(5,10));
        System.out.println(testService.Calcualte_OJLD(5,7));
        System.out.println(testService.Calcualte_OJLD(7,5));
        System.out.println(testService.Calcualte_OJLD(5,5));
    }*/

    @Test
    public void testTest() {
        testService.autoCreateTest(1, 5, 2);
    }

    @Test
    public void testArrange() {
        arrangeService.arrangeTest(7, "2019-07-30 10:22:22", 100);
    }


    @Test
    public void testGetStudentSelectedCourse() {
        System.out.println(courseService.getStudentCourse("abc"));
    }

    @Test
    public void testExam() {
        com.neuedu.project.domain.Test test = testService.getTestById(1);
        String cqIds = test.getChoiceQuestionIds();
        String sqIds = test.getSubjectiveQuestionIds();


        if (cqIds != null && !"".equals(cqIds.trim())) {
            // 解析选择题
            List<Question> forChoiceQuestions = new ArrayList<>();
            for (String idString : cqIds.split("\\s+")) {
                int id = Integer.parseInt(idString);
                Question question = questionService.getQuestionById(id);
                forChoiceQuestions.add(question);
            }
            List<ChoiceQuestion> choiceQuestions = QuestionUtils.getInstance().parseAsChoiceQuestions(forChoiceQuestions);
            // 解析选择题完成
            System.out.println(choiceQuestions);
        }

        if (sqIds != null && !"".equals(sqIds.trim())) {
            // 解析主观题
            List<Question> subjectiveQuestions = new ArrayList<>();
            for (String idString : sqIds.split("\\s+")) {
                int id = Integer.parseInt(idString);
                subjectiveQuestions.add(questionService.getQuestionById(id));
            }
            System.out.println(subjectiveQuestions);
        }





    }
    @Test
    public void testgetArrangedTestsByStudentId(){
        System.out.println(testService.getArrangedTestsByStudentId("abc"));
    }
    @Test
    public void testTimeLast(){
        System.out.println(testService.getTimeLast(7));
    }
    @Test
    public void TestgetStartedTest(){
        List<Arrangement> arrangements = testService.getArrangedTestsByStudentId("aaaa");
        //存储已开始的考试安排
        List<Arrangement> arrs = new ArrayList<>();
        for (Arrangement arrangement : arrangements) {
            if (arrangement.getIdentity() == 1)
                arrs.add(arrangement);
        }
        //System.out.println("------------------------------------------------------");
        System.out.println(arrs);
        //System.out.println("------------------------------------------------------");
    }

    @Test
    public void TestaddAnswerSheet(){

        String ca = "A#B#C# #A";
        String sa = "USB#clean";
        answerService.addAnswerSheet("aaaa",7,ca,sa);
    }

}
