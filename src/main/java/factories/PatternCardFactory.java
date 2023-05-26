package main.java.factories;

import main.java.model.PatternCard;

public class PatternCardFactory {

    
    public static PatternCard generatePatternCard(){
        return new PatternCard();
    }
}
