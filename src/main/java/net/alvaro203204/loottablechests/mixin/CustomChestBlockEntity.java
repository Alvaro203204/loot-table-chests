package net.alvaro203204.loottablechests.mixin;

import net.alvaro203204.loottablechests.LootTableChests;
import net.alvaro203204.loottablechests.util.ICustomChestBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.nbt.NbtCompound;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ChestBlockEntity.class)
public abstract class CustomChestBlockEntity implements ICustomChestBlockEntity {
    private NbtCompound persistentData;

    @Inject(method = "writeNbt", at = @At("HEAD"))
    protected void injectWriteMethod(NbtCompound nbt, CallbackInfo ci) {
        if (persistentData != null) {
            nbt.put(LootTableChests.MOD_ID, persistentData);
        }
    }

    @Inject(method = "readNbt", at = @At("HEAD"))
    protected void injectReadMethod(NbtCompound nbt, CallbackInfo ci) {
        if (nbt.contains(LootTableChests.MOD_ID, 10)) {
            persistentData = nbt.getCompound(LootTableChests.MOD_ID);
        }
    }
    @Override
    public NbtCompound getPersistentData() {
        if (persistentData == null) {
            persistentData = new NbtCompound();
        }
        return persistentData;
    }
}
