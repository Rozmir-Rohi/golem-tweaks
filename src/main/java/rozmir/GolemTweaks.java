package rozmir;

import rozmir.events.ReplaceVanillaSnowGolem;
import rozmir.events.ReplaceVanillaIronGolem;
import net.minecraftforge.common.MinecraftForge;
import rozmir.entity_extensions.SnowGolemBall;
import rozmir.entity_extensions.SnowGolemExtension;
import rozmir.entity_extensions.IronGolemExtension;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.Mod;

@Mod(modid = "golemtweaks", name = "Golem Tweaks", version = "1.0")
public class GolemTweaks
{
    public static final String MODID = "golemtweaks";
    public static final String VERSION = "1.0";
    
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) {
        int ModEntityID = EntityRegistry.findGlobalUniqueEntityId();
        EntityRegistry.registerModEntity((Class)IronGolemExtension.class, "IronGolem", ModEntityID++, (Object)this, 50, 2, true);
        EntityRegistry.registerModEntity((Class)SnowGolemExtension.class, "SnowGolem", ModEntityID++, (Object)this, 50, 2, true);
        EntityRegistry.registerModEntity((Class)SnowGolemBall.class, "SnowBall", ModEntityID++, (Object)this, 50, 2, true);
        MinecraftForge.EVENT_BUS.register((Object)new ReplaceVanillaIronGolem());
        MinecraftForge.EVENT_BUS.register((Object)new ReplaceVanillaSnowGolem());
    }
}