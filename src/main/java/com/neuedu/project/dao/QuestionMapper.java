package com.neuedu.project.dao;

import com.neuedu.project.domain.Question;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface QuestionMapper {

    void addChoiceQuestion(Question choiceQuestion);

    void addSubjectiveQuestion(Question subjectiveQuestion);

    /**
     * get all questions of the required course.
     * @param courseId  course id
     * @return  a list contains all question of this course
     */
//    List<Question> getQuestionsByCourseId(int courseId);

    List<Question> getQuestionsByCourseId(Question forQuery);

    Question getQuestionById(int id);

    void updateQuestion(Question question);

    void deletequestionById(int id);

//    List<Question> getSubjectiveQuestionsByCourseId(int coueseId);




}
