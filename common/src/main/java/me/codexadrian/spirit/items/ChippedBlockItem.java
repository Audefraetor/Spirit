package me.codexadrian.spirit.items;

import me.codexadrian.spirit.SpiritConfig;
import me.codexadrian.spirit.platform.Services;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ChippedBlockItem extends BlockItem {
    public ChippedBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> list, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, list, tooltipFlag);
        if(!Services.PLATFORM.isModLoaded("chipped") && SpiritConfig.showChippedError()) {
            list.add(new TranslatableComponent("spirit.tooltip.chipped_loaded").withStyle(ChatFormatting.RED));
        }
    }
}
