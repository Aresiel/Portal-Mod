package se.aresiel.aresportals;

import com.qouteall.immersive_portals.portal.Portal;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistryV3;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentFactoryRegistry;
import dev.onyxstudios.cca.api.v3.entity.EntityComponentInitializer;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;

public final class MyComponents implements EntityComponentInitializer {

    public static final ComponentKey<IntComponent> CORRIDORPORTALS =
            ComponentRegistryV3.INSTANCE.getOrCreate(new Identifier("aresportals:corridorportals"), IntComponent.class);

    @Override
    public void registerEntityComponentFactories(EntityComponentFactoryRegistry entityComponentFactoryRegistry) {
        entityComponentFactoryRegistry.registerFor(Portal.class, CORRIDORPORTALS, portal -> new CorridorPortalComponent());
    }

    public static int getPortalMaxAge(Entity portal){
        return CORRIDORPORTALS.get(portal).getValue();
    }

    public static void setPortalMaxAge(Entity portal, int maxAge){
        CORRIDORPORTALS.get(portal).setValue(maxAge);
    }
}
