package se.aresiel.aresportals;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.LiteralText;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class CorridorWandItem extends Item {

    private final int range;

    public CorridorWandItem(Settings settings, int range) {
        super(settings);
        this.range = range;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {


        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;
        Boolean success = false;

        if (hit.getType() == HitResult.Type.BLOCK) {
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();

            BlockPos belowBlockPos = blockPos.add(0, -1, 0);
            BlockState belowBlockState = client.world.getBlockState(belowBlockPos);

            if (blockHit.getSide() != Direction.UP && blockHit.getSide() != Direction.DOWN && !belowBlockState.isAir()) {
                ServerWorld serverWorld = McHelper.getServerWorld(playerEntity.world.getRegistryKey());

                Vec3d sideDir = SpaceTools.getDirectionVec3d(blockHit.getSide());
                Vec3d perpSideDir = SpaceTools.getPerpendicularDirectionVec3d(blockHit.getSide());

                Vec3d origin = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()).add(0.5, 0, 0.5).add(sideDir.multiply(0.501));
                Vec3d destination = null;


                for (int i = 1; i <= range; i++) { //Testing for empty space
                    Vec3d offset = sideDir.multiply(-i);
                    BlockPos topBlockPos = blockPos.add(offset.x, offset.y, offset.z);
                    BlockPos bottomBlockPos = blockPos.add(offset.x, offset.y - 1, offset.z);

                    BlockState topBlockState = client.world.getBlockState(topBlockPos);
                    BlockState bottomBlockState = client.world.getBlockState(bottomBlockPos);

                    if (topBlockState.isAir() && bottomBlockState.isAir()) {

                        destination = new Vec3d(topBlockPos.getX(), topBlockPos.getY(), topBlockPos.getZ()).add(0.5, 0, 0.5).add(sideDir.multiply(0.499));

                        break;
                    }
                }

                if (destination != null) {
                    if(!world.isClient()){
                        Portal startPortal = Portal.entityType.create(serverWorld);
                        Portal endPortal = Portal.entityType.create(serverWorld);

                        startPortal.setOriginPos(origin);
                        startPortal.setDestinationDimension(playerEntity.getEntityWorld().getRegistryKey());
                        startPortal.setDestination(destination);
                        startPortal.setOrientationAndSize(
                                perpSideDir,
                                new Vec3d(0, 1, 0),
                                1, 2
                        );

                        endPortal.setOriginPos(destination.add(sideDir.multiply(-0.001)));
                        endPortal.setDestinationDimension(playerEntity.getEntityWorld().getRegistryKey());
                        endPortal.setDestination(origin);
                        endPortal.setOrientationAndSize(
                                perpSideDir.multiply(-1),
                                new Vec3d(0, 1, 0),
                                1, 2
                        );

                        startPortal.world.spawnEntity(startPortal);
                        endPortal.world.spawnEntity(endPortal);

                        Entity startPortalEntity = McHelper.getServerWorld(startPortal.world.getRegistryKey()).getEntity(startPortal.getUuid());
                        Entity endPortalEntity = McHelper.getServerWorld(endPortal.world.getRegistryKey()).getEntity(endPortal.getUuid());

                        startPortalEntity.setCustomName(new LiteralText("AresielTemporaryPortal200"));
                        endPortalEntity.setCustomName(new LiteralText("AresielTemporaryPortal200"));
                    }
                    success = true;
                } else {
                    success = false;
                }
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
