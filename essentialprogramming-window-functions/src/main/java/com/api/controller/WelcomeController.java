package com.api.controller;

import com.util.json.JsonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


@Tag(description = "Test API", name = "Welcome (Test purpose only) ")
@RestController
@RequiredArgsConstructor
public class WelcomeController {

    private static final Logger LOG = LoggerFactory.getLogger(WelcomeController.class);



    //........................................................................................................................
    //Test purpose only
    @SneakyThrows
    @GetMapping(value = "test/{name}", produces = {"application/json"})
    @Operation(summary = "Test purpose only")
    public ResponseEntity<Serializable> test(@RequestHeader("Accept-Language") String lang, @PathVariable("name") String name) {
        return ResponseEntity.ok(new JsonResponse()
                .with("message", "Welcome " + name)
                .done()
        );
    }


    @GetMapping(value = "/questions")
    @ResponseBody
    public List<QuestionJSON> getQuestionsByQuiz() {

        QuestionJSON question = QuestionJSON.builder()
                .question("Why are you here ?")
                .answers(Arrays.asList("Don't know", "Looking around", "I want to build an awesome app"))
                .correctAnswer("c")
                .build();

        QuestionJSON question2 = QuestionJSON.builder()
                .question("What's next ?")
                .answers(Arrays.asList("Leave this page", "Get a coffee", "Build an awesome app"))
                .correctAnswer("c")
                .build();


        return Arrays.asList(question, question2);

    }

    @Builder
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionJSON {

        private int id;
        private String question;
        private String correctAnswer;
        private String quiz;
        private List<String> answers;
    }
}
