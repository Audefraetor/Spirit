package me.codexadrian.spirit.mixin;

import me.codexadrian.spirit.EngulfableItem;
import me.codexadrian.spirit.SpiritRegistry;
import me.codexadrian.spirit.platform.Services;
import me.codexadrian.spirit.recipe.SoulEngulfingRecipe;
import me.codexadrian.spirit.utils.RecipeUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseFireBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BaseFireBlock.class)
public abstract class BaseFireBlockMixin {

    @Inject(method = "entityInside", at = @At("HEAD"), cancellable = true)
    private void onBurn(BlockState blockState, Level level, BlockPos blockPos, Entity entity, CallbackInfo ci) {
        if (blockState.is(Blocks.SOUL_FIRE) && entity instanceof ItemEntity itemE && !level.isClientSide) {
            for(var recipe : SoulEngulfingRecipe.getRecipeForStack(itemE.getItem(), level.getRecipeManager())) {
                if(recipe.validateRecipe(blockPos, itemE, level)) {
                    ci.cancel();
                    break;
                }
            }

            if(Services.PLATFORM.isModLoaded("patchouli")) {
                if (itemE.getItem().getItem().equals(Items.BOOK)) {
                    //PatchouliCompat.bookRecipe(blockPos, itemE, ci);
                }
            }

            if(itemE.getItem().is(SpiritRegistry.SOUL_BLADE.get()) || itemE.getItem().is(SpiritRegistry.SOUL_BOW.get())) {
                itemE.setInvulnerable(true);
                ItemStack tool = itemE.getItem();
                if(tool.isDamaged() && level.random.nextBoolean()) {
                    tool.setDamageValue(tool.getDamageValue() - 1);
                    ServerLevel sLevel = (ServerLevel) itemE.level;
                    sLevel.sendParticles(ParticleTypes.SOUL, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 5, 0.5, 0.75, 0.5, 0);
                }
            }
        }
    }
}