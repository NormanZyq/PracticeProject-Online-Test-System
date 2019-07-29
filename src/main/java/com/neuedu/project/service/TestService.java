package com.neuedu.project.service;

import com.neuedu.project.domain.Arrangement;
import com.neuedu.project.domain.Test;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TestService {

    /**
     * 自动创建考试
     *
     * @param courseId 涉及课程id
     * @param cqCount 选择题数目
     * @param sqCount 主观题数目
     */
    void autoCreateTest(int courseId, int cqCount, int sqCount);

    /**
     * 学生界面：获得自己的考试信息
     *
     */
    List<Arrangement> getArrangedTestsByStudentId(String studentId);

//    int Calcualte_OJLD(int i, int j);

}
