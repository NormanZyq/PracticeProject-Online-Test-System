package com.neuedu.project.interceptor;

import com.neuedu.project.service.AnswerService;
import com.neuedu.project.service.TestService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * 负责控制学生是否有参加某一场考试的权限。
 * <p>
 * 控制包括：如果学生不在参加考试列表，则不允许考试
 * 如果考试时间已结束，不允许参加考试
 */
@Component
public class ExamAccessInterceptor implements HandlerInterceptor {

    /**
     * logger for this interceptor.
     */
    private final Logger log = Logger.getLogger(ExamAccessInterceptor.class);

    /**
     * 注入service（不确定是否可行）。
     */
    @Autowired
    private TestService testService;

    @Autowired
    private AnswerService answerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=utf-8");
        //PrintWriter out = response.getWriter();
        OutputStream os = response.getOutputStream();

        // 获得用户的ID
        String loggedId = (String) request.getSession().getAttribute("loggedId");
        int testId = Integer.parseInt(request.getParameter("testId"));

        Integer attendId = testService.getAttendTestRecId(loggedId, testId);

        if (attendId == null) {
            log.info("学生" + loggedId
                    + "未参加考试{id = " + testId + "}或该考试不存在，不允许访问");
            response.sendRedirect("/mypage");
            //out.close();
            return false;
        } else {
            log.info("学生" + loggedId
                    + "参加考试{testId = " + testId + "}，正在判断考试状态...");

            // 判断考试是否开始或结束
            long timeLast = testService.getTimeLast(testId);
            if (timeLast == -1) {
                // 考试未开始
                log.info("考试未开始");
                os.write(("<script>alert('考试未开始，不可进入！');"
                        + "window.location.href='/mypage';</script>").getBytes());
                os.flush();
            } else if (timeLast == 0) {
                // 考试已结束
                log.info("考试已结束");
                os.write(("<script>alert('考试已结束，不可进入！');"
                        + "window.location.href='/mypage';</script>").getBytes());
                os.flush();
            } else {
                log.info("考试进行中，允许访问");

                if (answerService.duclipSubmitAnswerSheet(loggedId, testId)) {
                    log.info("该学生已提交答卷，不再允许进入");
                    os.write(("<script>alert('已提交答卷，不可再次进入！');"
                            + "window.location.href='/mypage';</script>").getBytes());
                    os.flush();
                    return false;
                }


                return true;
            }
            os.close();
            return false;
        }
    }
}


















