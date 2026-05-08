package com.goncharova.project.service;

import com.goncharova.project.entity.QuestionEntity;
import org.springframework.stereotype.Service;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class TestService {

    private final Random random = new Random();

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(
                "jdbc:postgresql://localhost:5433/ege_exam",
                "postgres",
                "pass123"
        );
    }

    public List<QuestionEntity> getAllQuestions() {
        List<QuestionEntity> questions = new ArrayList<>();
        String sql = "SELECT * FROM questions";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                QuestionEntity q = new QuestionEntity();
                q.setId(rs.getLong("id"));
                q.setQuestionsText(rs.getString("questions_text"));
                q.setQuestionType(rs.getString("question_type"));
                q.setOption1(rs.getString("option1"));
                q.setOption2(rs.getString("option2"));
                q.setOption3(rs.getString("option3"));
                q.setOption4(rs.getString("option4"));
                q.setCorrectAnswer(rs.getString("correct_answer"));
                q.setExplanation(rs.getString("explanation"));
                questions.add(q);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }

    public QuestionEntity getRandomQuestion() {
        List<QuestionEntity> all = getAllQuestions();
        if (all.isEmpty()) return null;
        return all.get(random.nextInt(all.size()));
    }

    public boolean checkAnswer(Long questionId, String userAnswer) {
        String sql = "SELECT correct_answer FROM questions WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, questionId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getString("correct_answer").equalsIgnoreCase(userAnswer.trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public QuestionEntity getQuestionById(Long id) {
        String sql = "SELECT * FROM questions WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setLong(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                QuestionEntity q = new QuestionEntity();
                q.setId(rs.getLong("id"));
                q.setQuestionsText(rs.getString("questions_text"));
                q.setQuestionType(rs.getString("question_type"));
                q.setOption1(rs.getString("option1"));
                q.setOption2(rs.getString("option2"));
                q.setOption3(rs.getString("option3"));
                q.setOption4(rs.getString("option4"));
                q.setCorrectAnswer(rs.getString("correct_answer"));
                q.setExplanation(rs.getString("explanation"));
                return q;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}