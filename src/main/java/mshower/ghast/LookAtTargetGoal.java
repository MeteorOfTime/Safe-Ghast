package mshower.ghast;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.GhastEntity;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.EnumSet;

public class LookAtTargetGoal extends Goal {
    private final GhastEntity ghast;

    public LookAtTargetGoal(GhastEntity ghast) {
        this.ghast = ghast;
        this.setControls(EnumSet.of(Control.LOOK));
    }

    @Override
    public boolean canStart() {
        return true;
    }

    @Override
    public boolean shouldRunEveryTick() {
        return true;
    }

    @Override
    public void tick() {
        LivingEntity target = ghast.getTarget();
        if (target == null) {
            Vec3d vel = ghast.getVelocity();
            ghast.setYaw(-((float) MathHelper.atan2(vel.x, vel.z)) * (180F / (float)Math.PI));
            ghast.bodyYaw = ghast.getYaw();
        } else {
            double e = target.getX() - ghast.getX();
            double f = target.getZ() - ghast.getZ();
            ghast.setYaw(-((float) MathHelper.atan2(e, f)) * (180F / (float)Math.PI));
            ghast.bodyYaw = ghast.getYaw();
        }
    }
}
