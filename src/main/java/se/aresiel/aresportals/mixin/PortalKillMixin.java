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
import se.aresiel.aresportals.MyComponents;

@Mixin(Portal.class)
public abstract class PortalKillMixin extends Entity{

    public PortalKillMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("TAIL"), method = "tick")
    private void init(CallbackInfo info){
        int maxAge = MyComponents.getPortalMaxAge(this);

        if(this.age > maxAge && maxAge != -1){
            this.kill();
        }
    }
}
