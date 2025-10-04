package mshower.ghast.mixin;

import mshower.ghast.FlyRandomlyGoal;
import mshower.ghast.LookAtTargetGoal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.GhastEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GhastEntity.class)

public class GhastEntityMixin {

    @Inject(method = "initGoals", at = @At("HEAD"), cancellable = true)
    private void customInitGoals(CallbackInfo ci) {
        ci.cancel();

        GhastEntity ghast = (GhastEntity)(Object)this;
        GoalSelector goalSelector = ((MobEntityAccessor) ghast).getGoalSelector();
        GoalSelector targetSelector = ((MobEntityAccessor) ghast).getTargetSelector();

        goalSelector.clear(goal -> true);
        targetSelector.clear(goal -> true);


        goalSelector.add(5, new FlyRandomlyGoal(ghast));
        goalSelector.add(7, new LookAtTargetGoal(ghast));

    }
}
