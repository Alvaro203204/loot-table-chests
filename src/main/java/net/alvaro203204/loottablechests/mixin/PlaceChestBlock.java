package net.alvaro203204.loottablechests.mixin;

import net.alvaro203204.loottablechests.LootTableChests;
import net.alvaro203204.loottablechests.util.ICustomChestBlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.ChestBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlock.class)
public class PlaceChestBlock {
    @Inject(method = "onPlaced", at = @At("HEAD"))
    public void onPlaced(World world, BlockPos pos, BlockState state, LivingEntity placer, ItemStack stack, CallbackInfo ci) {
        if (placer.isPlayer()) {
            PlayerEntity player = (PlayerEntity) placer;

            if (!player.isCreative() && !player.isSpectator()) {
                BlockEntity entity = world.getBlockEntity(pos);

                if (entity instanceof ChestBlockEntity chest && !world.isClient()) {
                    ICustomChestBlockEntity customChest = (ICustomChestBlockEntity) chest;
                    customChest.getPersistentData().putBoolean("used", true);
                }
            }
        }
    }
}
