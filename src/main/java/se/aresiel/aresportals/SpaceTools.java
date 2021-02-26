package se.aresiel.aresportals;

import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

public class SpaceTools {
    public static Vec3d getDirectionVec3d(Direction direction) {
        switch (direction) {
            case UP:
                return new Vec3d(0, -1, 0);
            case DOWN:
                return new Vec3d(0, 1, 0);
            case EAST:
                return new Vec3d(-1, 0, 0);
            case WEST:
                return new Vec3d(1, 0, 0);
            case NORTH:
                return new Vec3d(0, 0, -1);
            case SOUTH:
                return new Vec3d(0, 0, 1);
            default:
                throw new NullPointerException();
        }
    }
}
