package se.aresiel.aresportals;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WandItem extends Item {
    public WandItem(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity playerEntity, Hand hand) {
        playerEntity.getItemCooldownManager().set(this, 10);

        MinecraftClient client = MinecraftClient.getInstance();
        HitResult hit = client.crosshairTarget;

        if(hit.getType() == HitResult.Type.BLOCK){
            BlockHitResult blockHit = (BlockHitResult) hit;
            BlockPos blockPos = blockHit.getBlockPos();
            BlockState blockState = client.world.getBlockState(blockPos);
            Block block = blockState.getBlock();



            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, 1f, 1.5f);
            return TypedActionResult.success(playerEntity.getStackInHand(hand));
        } else {
            playerEntity.playSound(SoundEvents.BLOCK_NOTE_BLOCK_GUITAR, 1f, 0.5f);
            return TypedActionResult.fail(playerEntity.getStackInHand(hand));
        }
    }
}
