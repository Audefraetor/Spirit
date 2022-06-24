package me.codexadrian.spirit.forge;

import me.codexadrian.spirit.Spirit;
import me.codexadrian.spirit.compat.forge.TOPCompat;
import me.codexadrian.spirit.entity.CrudeSoulEntity;
import me.codexadrian.spirit.platform.forge.ForgeRegistryHelper;
import me.codexadrian.spirit.registry.SpiritMisc;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(Spirit.MODID)
public class ForgeSpirit {

    public ForgeSpirit() {
        Spirit.onInitialize();
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SpiritConfigImpl.CONFIG, "spirit.toml");
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ForgeRegistryHelper.BLOCKS.register(eventBus);
        ForgeRegistryHelper.ITEMS.register(eventBus);
        ForgeRegistryHelper.BLOCK_ENTITIES.register(eventBus);
        ForgeRegistryHelper.ENCHANTMENTS.register(eventBus);
        ForgeRegistryHelper.ENTITIES.register(eventBus);
        ForgeRegistryHelper.RECIPE_SERIALIZERS.register(eventBus);
        eventBus.addListener(this::entityAttributeStuff);
        eventBus.addListener(this::imcEvent);
    }

    private void imcEvent(InterModEnqueueEvent event) {
        if(ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", TOPCompat::new);
        }
    }

    private void entityAttributeStuff(EntityAttributeCreationEvent event) {
        event.put(SpiritMisc.SOUL_ENTITY.get(), CrudeSoulEntity.createMobAttributes().build());
    }
}