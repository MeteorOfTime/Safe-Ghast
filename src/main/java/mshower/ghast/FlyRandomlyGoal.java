package mshower.ghast;

import net.minecraft.entity.ai.control.MoveControl;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.random.Random;

import java.util.EnumSet;

public class FlyRandomlyGoal extends Goal {
    private final GhastEntity ghast;

    public FlyRandomlyGoal(GhastEntity ghast) {
        this.ghast = ghast;
        this.setControls(EnumSet.of(Control.MOVE));
    }

    @Override
    public boolean canStart() {
        MoveControl move = ghast.getMoveControl();
        if (!move.isMoving()) return true;

        double dx = move.getTargetX() - ghast.getX();
        double dy = move.getTargetY() - ghast.getY();
        double dz = move.getTargetZ() - ghast.getZ();
        double distSq = dx * dx + dy * dy + dz * dz;
        return distSq < 1.0 || distSq > 3600.0;
    }

    @Override
    public void start() {
        Random random = ghast.getRandom();
        double dx = ghast.getX() + (random.nextFloat() * 2.0 - 1.0) * 16.0;
        double dy = ghast.getY() + (random.nextFloat() * 2.0 - 1.0) * 16.0;
        double dz = ghast.getZ() + (random.nextFloat() * 2.0 - 1.0) * 16.0;

        ghast.getMoveControl().moveTo(dx, dy, dz, 1.0);
    }

    private boolean willCollide(Vec3d direction, int steps) {
        Box box = ghast.getBoundingBox();
        for (int i = 1; i < steps; i++) {
            box = box.offset(direction);
            if (!ghast.getWorld().isSpaceEmpty(ghast, box)) return false;
        }
        return true;
    }
}