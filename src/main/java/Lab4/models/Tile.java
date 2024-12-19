package Lab4.models;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.io.Serializable;

public class Tile implements Serializable {
    private final IntegerProperty number;
    private final BooleanProperty immutable;

    public Tile() {
        this.number = new SimpleIntegerProperty(0);
        this.immutable = new SimpleBooleanProperty(false);
    }

    public void setNumber(int num) {
        this.number.set(num);
    }

    public int getNumber() {
        return number.get();
    }

    public IntegerProperty numberProperty() {
        return number;
    }

    public boolean isImmutable() {
        return immutable.get();
    }

    public void setImmutable(boolean immutable) {
        this.immutable.set(immutable);
    }

    public BooleanProperty immutableProperty() {
        return immutable;
    }
}
