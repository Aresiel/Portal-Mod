package se.aresiel.aresportals.mixin;

import com.qouteall.immersive_portals.portal.Portal;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Portal.class)
public abstract class PortalKillMixin extends Entity {
    public PortalKillMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void init(CallbackInfo info){

        Entity portalEntity = (Entity) this;

        if(portalEntity.getCustomName() != null && portalEntity.getCustomName().asString().equals("AresielTemporaryPortal200") && portalEntity.age >= 200){
            portalEntity.kill();
        }
    }
}
