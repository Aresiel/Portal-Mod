package se.aresiel.aresportals;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class PortalWandItem extends Item {
    private final int range;

    public PortalWandItem(Settings settings, int range) {
        super(settings);
        this.range = range;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        Boolean success = false;

        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hitResult = client.crosshairTarget;

        if(hitResult.getType() == HitResult.Type.BLOCK){
            BlockHitResult blockHitResult = (BlockHitResult) hitResult;
            BlockPos blockPos = blockHitResult.getBlockPos();
            BlockState blockState = client.world.getBlockState(blockPos);

            BlockPos blockPosBottom = blockPos.add(0,1,0);
            BlockState blockStateBottom = client.world.getBlockState(blockPosBottom);
            BlockPos blockPosTop = blockPos.add(0,2,0);
            BlockState blockStateTop = client.world.getBlockState(blockPosTop);

            if(blockHitResult.getSide() == Direction.UP && blockStateBottom.isAir() && blockStateTop.isAir()){
                ServerWorld serverWorld = McHelper.getServerWorld(playerEntity.world.getRegistryKey());
                Vec3d origin = new Vec3d(blockPosTop.getX(), blockPosTop.getY(), blockPosTop.getZ()).add(0.5, 0, 0.5);
                Vec3d destination = playerEntity.getPos().add(0, 1, 0);
                RegistryKey<World> dimension = playerEntity.getEntityWorld().getRegistryKey();

                Vec3d sideDir = SpaceTools.getDirectionVec3d(blockHitResult.getSide());
                Vec3d playerFacing = SpaceTools.getPerpendicularDirectionVec3d(playerEntity.getHorizontalFacing());

                Portal portal1 = Portal.entityType.create(serverWorld);
                portal1.setOriginPos(origin);
                portal1.setDestinationDimension(dimension);
                portal1.setDestination(destination);
                portal1.setOrientationAndSize(
                        playerFacing.multiply(-1),
                        new Vec3d(0, 1, 0),
                        1,
                        2
                );
                if(!world.isClient()){
                    portal1.world.spawnEntity(portal1);
                }

                success = true;
            } else {
                success = false;
            }

        }

        if (success) {
            playerEntity.getItemCooldownManager().set(this, 10);
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, 1f, 1.5f);
            return TypedActionResult.success(playerEntity.getStackInHand(hand));
        } else {
            playerEntity.getItemCooldownManager().set(this, 5);
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, 1f, 0.5f);
            return TypedActionResult.fail(playerEntity.getStackInHand(hand));
        }
    }
}
