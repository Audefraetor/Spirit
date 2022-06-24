package me.codexadrian.spirit.platform.forge;

import me.codexadrian.spirit.Spirit;
import me.codexadrian.spirit.platform.services.IRegistryHelper;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

public class ForgeRegistryHelper implements IRegistryHelper {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, Spirit.MODID);
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, Spirit.MODID);
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(ForgeRegistries.ENCHANTMENTS, Spirit.MODID);
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(ForgeRegistries.BLOCK_ENTITIES, Spirit.MODID);
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, Spirit.MODID);
    public static final DeferredRegister<RecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Spirit.MODID);

    @Override
    public <T extends Item> Supplier<T> registerItem(String id, Supplier<T> item) {
        return ITEMS.register(id, item);
    }

    @Override
    public <T extends Block> Supplier<T> registerBlock(String id, Supplier<T> item) {
        return BLOCKS.register(id, item);
    }

    @Override
    public <E extends BlockEntity, T extends BlockEntityType<E>> Supplier<T> registerBlockEntity(String id, Supplier<T> item) {
        return BLOCK_ENTITIES.register(id, item);
    }

    @Override
    public <E extends BlockEntity> BlockEntityType<E> createBlockEntityType(BlockEntityFactory<E> factory, Block... blocks) {
        return BlockEntityType.Builder.of(factory::create, blocks).build(null);
    }

    @Override
    public <T extends Enchantment> Supplier<T> registerEnchantment(String id, Supplier<T> enchantment) {
        return ENCHANTMENTS.register(id, enchantment);
    }

    @Override
    public <T extends Entity> Supplier<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory group, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, group).sized(width, height).build(name));
    }

    @Override
    public <R extends Recipe<?>, T extends RecipeSerializer<R>> Supplier<T> registerRecipeSerializer(String name, Supplier<T> recipe) {
        return RECIPE_SERIALIZERS.register(name, recipe);
    }

    @Override
    public CreativeModeTab registerCreativeTab(ResourceLocation tab, Supplier<ItemStack> supplier) {
        return new CreativeModeTab(tab.getNamespace() + "." + tab.getPath()) {
            @Override
            public @NotNull ItemStack makeIcon() {
                return supplier.get();
            }
        };
    }
}
