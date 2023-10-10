package net.alvaro203204.loottablechests.event;

import net.alvaro203204.loottablechests.config.ConfigManager;
import net.alvaro203204.loottablechests.util.ICustomChestBlockEntity;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;

import java.util.List;

public class ChestOpenHandler implements UseBlockCallback {
    @Override
    public ActionResult interact(PlayerEntity player, World world, Hand hand, BlockHitResult hitResult) {
        BlockPos pos = hitResult.getBlockPos();
        BlockEntity entity = player.world.getBlockEntity(pos);

        if (entity instanceof ChestBlockEntity chest && !world.isClient()) {
            Biome biome = world.getBiome(pos).value();

            Identifier biomeName = world.getRegistryManager().get(RegistryKeys.BIOME).getId(biome);

            ICustomChestBlockEntity customChest = (ICustomChestBlockEntity) chest;
            boolean used = customChest.getPersistentData().getBoolean("used");

            if (!used) {
                List<Identifier> lootTables = ConfigManager.LOOT_TABLES.getOrDefault(biomeName, ConfigManager.DEFAULT_LOOT_TABLES);

                int index = world.random.nextInt(lootTables.size());

                Identifier selectedLootTables = lootTables.get(index);

                chest.setLootTable(selectedLootTables, world.random.nextLong());

                customChest.getPersistentData().putBoolean("used", true);

                player.sendMessage(Text.literal("Generating random loot"));
            }
        }

        return ActionResult.PASS;
    }
}
