package se.aresiel.aresportals;

import net.minecraft.nbt.CompoundTag;

public class CorridorPortalComponent implements IntComponent {

    int maxAge = -1;

    @Override
    public int getValue() {
        return maxAge;
    }

    @Override
    public void setValue(int value) {
        this.maxAge = value;
    }

    @Override
    public void readFromNbt(CompoundTag compoundTag) {
        this.maxAge = compoundTag.getInt("maxAge");
    }

    @Override
    public void writeToNbt(CompoundTag compoundTag) {
        compoundTag.putInt("maxAge", this.maxAge);
    }
}
