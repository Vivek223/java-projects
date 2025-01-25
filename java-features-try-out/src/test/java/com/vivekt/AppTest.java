package com.vivekt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class AppTest {

    @Test
    void When_ValidInputPassed_Expect_FirstWordCapitalized(){
        //Arrange
        String input = "one flew over cucu's nest";
        String expected = "One Flew Over Cucu's Nest";
        App app = new App();

        //Act
        String actual = app.capitalizeWords(input);

        //Assert
        Assertions.assertEquals(expected, actual);
    }
}
