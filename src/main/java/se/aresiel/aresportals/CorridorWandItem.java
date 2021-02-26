package se.aresiel.aresportals;

import com.qouteall.immersive_portals.McHelper;
import com.qouteall.immersive_portals.portal.Portal;
import net.minecraft.block.Block;
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
import net.minecraft.util.math.Vec3i;
import net.minecraft.world.World;

public class CorridorWandItem extends Item {

    public int range;

    public CorridorWandItem(Settings settings, int range) {
        super(settings);
        this.range = range;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {


        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;
        Boolean success = false;

        if(hit.getType() == HitResult.Type.BLOCK){
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = client.world.getBlockState(blockPos);
            Block block = blockState.getBlock();

            BlockPos belowBlockPos = blockPos.add(0,-1,0);
            BlockState belowBlockState = client.world.getBlockState(belowBlockPos);

            if(blockHit.getSide() != Direction.UP && blockHit.getSide() != Direction.DOWN && !belowBlockState.isAir()){
                if(!world.isClient()){
                    ServerWorld serverWorld = McHelper.getServerWorld(playerEntity.world.getRegistryKey());

                    Portal portal = Portal.entityType.create(serverWorld);

                    Vec3d sideDir = SpaceTools.getDirectionVec3d(blockHit.getSide());
                    Vec3d perpSideDir = SpaceTools.getPerpendicularDirectionVec3d(blockHit.getSide());

                    Vec3d origin = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ()).add(0.5,0,0.5).add(sideDir.multiply(0.51));

                    /*for(int i = 1; i <= range; i++){ //Testing for empty space
                        BlockPos topBlockPos = blockPos.add(new Vec3i(sideDir.multiply(i)));
                    }*/

                    portal.setOriginPos(origin);
                    portal.setDestinationDimension(playerEntity.getEntityWorld().getRegistryKey());
                    portal.setDestination(playerEntity.getPos().add(0,1,0));
                    portal.setOrientationAndSize(
                            perpSideDir,
                            new Vec3d(0,1,0),
                            1,2
                    );
                    portal.world.spawnEntity(portal);

                    success = true;
                } else {
                    success = true;
                }


            }
        }

        if(success){
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
