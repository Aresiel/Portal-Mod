package se.aresiel.aresportals;


import dev.onyxstudios.cca.api.v3.component.ComponentV3;

public interface IntComponent extends ComponentV3 {
    int getValue();
    void setValue(int value);
}